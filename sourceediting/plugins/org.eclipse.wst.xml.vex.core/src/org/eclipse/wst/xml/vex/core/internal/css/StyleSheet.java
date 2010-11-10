/*******************************************************************************
 * Copyright (c) 2004, 2010 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Dave Holroyd - Implement font-weight:bolder
 *     Dave Holroyd - Implement text decoration
 *     John Austin - More complete CSS constants. Add the colour "orange".
 *     Travis Haagen - bug 260806 - enhanced support for 'content' CSS property
 *     Florian Thienel - bug 306639 - remove serializability from StyleSheet
 *                       and dependend classes
 *     Mohamadou Nassourou - Bug 298912 - rudimentary support for images 
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.css;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.wst.xml.vex.core.internal.core.FontSpec;
import org.eclipse.wst.xml.vex.core.internal.css.IProperty.Axis;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.w3c.css.sac.LexicalUnit;

/**
 * Represents a CSS style sheet.
 */
public class StyleSheet {
	private static final Comparator<PropertyDecl> PROPERTY_CASCADE_ORDERING =
		new Comparator<PropertyDecl>() {
		public int compare(PropertyDecl propertyDecl1,
				           PropertyDecl propertyDecl2) {
			if (propertyDecl1.isImportant() != propertyDecl2.isImportant()) {
				return    (propertyDecl1.isImportant() ? 1 : 0)
				        - (propertyDecl2.isImportant() ? 1 : 0);
			}

			return   propertyDecl1.getRule().getSpecificity()
			       - propertyDecl2.getRule().getSpecificity();
		}
	};

	/**
	 * Standard CSS properties.
	 */
	private static final IProperty[] CSS_PROPERTIES = new IProperty[] {
			new DisplayProperty(),
			new LineHeightProperty(),
			new ListStyleTypeProperty(),
			new TextAlignProperty(),
			new WhiteSpaceProperty(),

			new FontFamilyProperty(),
			new FontSizeProperty(),
			new FontStyleProperty(),
			new FontWeightProperty(),
			new TextDecorationProperty(),

			new ColorProperty(CSS.COLOR),
			new ColorProperty(CSS.BACKGROUND_COLOR),

			new LengthProperty(CSS.MARGIN_BOTTOM, IProperty.Axis.VERTICAL),
			new LengthProperty(CSS.MARGIN_LEFT, IProperty.Axis.HORIZONTAL),
			new LengthProperty(CSS.MARGIN_RIGHT, IProperty.Axis.HORIZONTAL),
			new LengthProperty(CSS.MARGIN_TOP, IProperty.Axis.VERTICAL),

			new LengthProperty(CSS.PADDING_BOTTOM, IProperty.Axis.VERTICAL),
			new LengthProperty(CSS.PADDING_LEFT, IProperty.Axis.HORIZONTAL),
			new LengthProperty(CSS.PADDING_RIGHT, IProperty.Axis.HORIZONTAL),
			new LengthProperty(CSS.PADDING_TOP, IProperty.Axis.VERTICAL),

			new ColorProperty(CSS.BORDER_BOTTOM_COLOR),
			new ColorProperty(CSS.BORDER_LEFT_COLOR),
			new ColorProperty(CSS.BORDER_RIGHT_COLOR),
			new ColorProperty(CSS.BORDER_TOP_COLOR),
			new BorderStyleProperty(CSS.BORDER_BOTTOM_STYLE),
			new BorderStyleProperty(CSS.BORDER_LEFT_STYLE),
			new BorderStyleProperty(CSS.BORDER_RIGHT_STYLE),
			new BorderStyleProperty(CSS.BORDER_TOP_STYLE),
			new BorderWidthProperty(CSS.BORDER_BOTTOM_WIDTH,
					CSS.BORDER_BOTTOM_STYLE, IProperty.Axis.VERTICAL),
			new BorderWidthProperty(CSS.BORDER_LEFT_WIDTH,
					CSS.BORDER_LEFT_STYLE, IProperty.Axis.HORIZONTAL),
			new BorderWidthProperty(CSS.BORDER_RIGHT_WIDTH,
					CSS.BORDER_RIGHT_STYLE, IProperty.Axis.HORIZONTAL),
			new BorderWidthProperty(CSS.BORDER_TOP_WIDTH, CSS.BORDER_TOP_STYLE,
					IProperty.Axis.VERTICAL), new BorderSpacingProperty(), 
			new LengthProperty(CSS.HEIGHT, IProperty.Axis.VERTICAL),
			new LengthProperty(CSS.WIDTH, IProperty.Axis.HORIZONTAL),
			new BackgroundImageProperty()
		};

	/**
	 * The rules that comprise the stylesheet.
	 */
	private final List<Rule> rules;

	/**
	 * Computing styles can be expensive, e.g. we have to calculate the styles
	 * of all parents of an element. We therefore cache styles in a map of
	 * element => WeakReference(styles). A weak hash map is used to avoid
	 * leaking memory as elements are deleted. By using weak references to the
	 * values, we also ensure the cache is memory-sensitive.
	 * 
	 * This must be transient to prevent it from being serialized, as
	 * WeakHashMaps are not serializable.
	 */
	private transient Map<VEXElement, WeakReference<Styles>> styleMap;

	/**
	 * Class constructor.
	 * 
	 * @param rules
	 *            Rules that constitute the style sheet.
	 */
	public StyleSheet(final Collection<Rule> rules) {
		this.rules = new ArrayList<Rule>(rules);
	}

	/**
	 * Flush any cached styles for the given element.
	 * 
	 * @param element
	 *            IVEXElement for which styles are to be flushed.
	 */
	public void flushStyles(VEXElement element) {
		this.getStyleMap().remove(element);
	}

	/**
	 * Returns a pseudo-element representing content to be displayed after the
	 * given element, or null if there is no such content.
	 * 
	 * @param element
	 *            Parent element of the pseudo-element.
	 */
	public VEXElement getAfterElement(VEXElement element) {
		PseudoElement pe = new PseudoElement(element, PseudoElement.AFTER);
		Styles styles = this.getStyles(pe);
		if (styles == null) {
			return null;
		} else {
			return pe;
		}
	}

	/**
	 * Returns a pseudo-element representing content to be displayed before the
	 * given element, or null if there is no such content.
	 * 
	 * @param element
	 *            Parent element of the pseudo-element.
	 */
	public VEXElement getBeforeElement(VEXElement element) {
		PseudoElement pe = new PseudoElement(element, PseudoElement.BEFORE);
		Styles styles = this.getStyles(pe);
		if (styles == null) {
			return null;
		} else {
			return pe;
		}
	}

	/**
	 * Returns the styles for the given element. The styles are cached to ensure
	 * reasonable performance.
	 * 
	 * @param element
	 *            IVEXElement for which to calculate the styles.
	 */
	public Styles getStyles(VEXElement element) {

		Styles styles;
		WeakReference<Styles> ref = getStyleMap().get(element);

		if (ref != null) {
			// can't combine these tests, since calling ref.get() twice
			// (once to query for null, once to get the value) would
			// cause a race condition should the GC happen btn the two.
			styles = ref.get();
			if (styles != null) {
				return styles;
			}
		} else if (getStyleMap().containsKey(element)) {
			// this must be a pseudo-element with no content
			return null;
		}

		styles = calculateStyles(element);

		if (styles == null) {
			// Yes, they can be null if element is a PseudoElement with no
			// content property
			getStyleMap().put(element, null);
		} else {
			getStyleMap().put(element, new WeakReference<Styles>(styles));
		}

		return styles;
	}

	private Styles calculateStyles(VEXElement element) {

		Styles styles = new Styles();
		Styles parentStyles = null;
		if (element.getParent() != null) {
			parentStyles = this.getStyles(element.getParent());
		}

		Map<String, LexicalUnit> decls = getApplicableDeclarations(element);

		LexicalUnit lu;

		// If we're finding a pseudo-element, look at the 'content' property
		// first, since most of the time it'll be empty and we'll return null.
		if (element instanceof PseudoElement) {
			lu = decls.get(CSS.CONTENT);
			if (lu == null)
				return null;

			List<String> content = new ArrayList<String>();
			while (lu != null) {
				switch (lu.getLexicalUnitType()) {
				case LexicalUnit.SAC_STRING_VALUE :
					// content: "A String"
					content.add(lu.getStringValue());
					break;
				case LexicalUnit.SAC_ATTR :
					// content: attr(attributeName)
					final String attributeValue = element.getParent().getAttribute(lu.getStringValue());
					if (attributeValue != null)
						content.add(attributeValue);
					break;
				}
				lu = lu.getNextLexicalUnit();
			}
			styles.setContent(content);
		}

		for (final IProperty property : CSS_PROPERTIES) {
			lu = decls.get(property.getName());
			Object value = property.calculate(lu, parentStyles, styles, element);
			styles.put(property.getName(), value);
		}

		// Now, map font-family, font-style, font-weight, and font-size onto
		// an AWT font.

		int styleFlags = FontSpec.PLAIN;
		String fontStyle = styles.getFontStyle();
		if (fontStyle.equals(CSS.ITALIC) || fontStyle.equals(CSS.OBLIQUE)) {
			styleFlags |= FontSpec.ITALIC;
		}
		if (styles.getFontWeight() > 550) {
			// 550 is halfway btn normal (400) and bold (700)
			styleFlags |= FontSpec.BOLD;
		}
		String textDecoration = styles.getTextDecoration();
		if (textDecoration.equals(CSS.UNDERLINE)) {
			styleFlags |= FontSpec.UNDERLINE;
		} else if (textDecoration.equals(CSS.OVERLINE)) {
			styleFlags |= FontSpec.OVERLINE;
		} else if (textDecoration.equals(CSS.LINE_THROUGH)) {
			styleFlags |= FontSpec.LINE_THROUGH;
		}

		styles.setFont(new FontSpec(styles.getFontFamilies(), styleFlags, Math
				.round(styles.getFontSize())));

		return styles;
	}

	/**
	 * Returns the rules comprising this stylesheet.
	 */
	public List<Rule> getRules() {
		return Collections.unmodifiableList(this.rules);
	}
	
	/**
	 * Returns all the declarations that apply to the given element.
	 */
	private Map<String, LexicalUnit> getApplicableDeclarations(final VEXElement element) {
		final List<PropertyDecl> rawDeclarationsForElement = findAllDeclarationsFor(element);

		// Sort in cascade order. We can then just stuff them into a
		// map and get the right values since higher-priority values
		// come later and overwrite lower-priority ones.
		Collections.sort(rawDeclarationsForElement, PROPERTY_CASCADE_ORDERING);

		final Map<String, PropertyDecl> distilledDeclarations = new HashMap<String, PropertyDecl>();
		final Map<String, LexicalUnit> values = new HashMap<String, LexicalUnit>();
		for (final PropertyDecl declaration : rawDeclarationsForElement) {
			final PropertyDecl previousDeclaration = distilledDeclarations.get(declaration.getProperty());
			if (previousDeclaration == null || !previousDeclaration.isImportant()
					|| declaration.isImportant()) {
				distilledDeclarations.put(declaration.getProperty(), declaration);
				values.put(declaration.getProperty(), declaration.getValue());
			}
		}

		return values;
	}
	
	private List<PropertyDecl> findAllDeclarationsFor(final VEXElement element) {
		final List<PropertyDecl> rawDeclarations = new ArrayList<PropertyDecl>();
		for (final Rule rule : rules) {
			if (rule.matches(element)) {
				PropertyDecl[] ruleDecls = rule.getPropertyDecls();
				for (final PropertyDecl ruleDecl : ruleDecls) {
					rawDeclarations.add(ruleDecl);
				}
			}
		}
		return rawDeclarations;
	}

	private Map<VEXElement, WeakReference<Styles>> getStyleMap() {
		if (styleMap == null) {
			styleMap = new WeakHashMap<VEXElement, WeakReference<Styles>>();
		}
		return styleMap;
	}
}
