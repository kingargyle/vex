/*******************************************************************************
 * Copyright (c) 2004, 2010 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Mohamadou Nassourou - Bug 298912 - rudimentary support for images 
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.swt;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.xml.vex.core.internal.core.Color;
import org.eclipse.wst.xml.vex.core.internal.core.ColorResource;
import org.eclipse.wst.xml.vex.core.internal.core.FontMetrics;
import org.eclipse.wst.xml.vex.core.internal.core.FontResource;
import org.eclipse.wst.xml.vex.core.internal.core.FontSpec;
import org.eclipse.wst.xml.vex.core.internal.core.Graphics;
import org.eclipse.wst.xml.vex.core.internal.core.Image;
import org.eclipse.wst.xml.vex.core.internal.core.Rectangle;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * Implementation of the Vex Graphics interface, mapping it to a
 * org.eclipse.swt.graphics.GC object.
 * 
 * <p>
 * The GC given to us by SWT is that of the Canvas, which is just a viewport
 * into the document. This class therefore implements an "origin", which
 * represents the top-left corner of the document relative to the top-left
 * corner of the canvas. The x- and y-coordinates of the origin are always
 * negative.
 * </p>
 * .
 */
public class SwtGraphics implements Graphics {

	private final GC gc;
	private int originX;
	private int originY;

	/**
	 * Class constructor.
	 * 
	 * @param gc
	 *            SWT GC to which we are drawing.
	 */
	public SwtGraphics(final GC gc) {
		this.gc = gc;
	}

	public void dispose() {
		gc.dispose();
	}

	public void drawChars(final char[] chars, final int offset, final int length, final int x, final int y) {
		drawString(new String(chars, offset, length), x, y);

	}

	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		gc.drawLine(x1 + originX, y1 + originY, x2 + originX, y2 + originY);
	}

	public void drawOval(final int x, final int y, final int width, final int height) {
		gc.drawOval(x + originX, y + originY, width, height);
	}

	public void drawRect(final int x, final int y, final int width, final int height) {
		gc.drawRectangle(x + originX, y + originY, width, height);
	}

	public void drawString(final String s, final int x, final int y) {
		gc.drawString(s, x + originX, y + originY, true);
	}

	public void drawImage(final Image image, final int x, final int y, final int width, final int height) {
		Assert.isTrue(image instanceof SwtImage);
		final org.eclipse.swt.graphics.Image swtImage = new org.eclipse.swt.graphics.Image(gc.getDevice(), ((SwtImage) image).imageData);
		try {
			gc.drawImage(swtImage, 0, 0, image.getWidth(), image.getHeight(), x + originX, y + originY, width, height);
		} finally {
			swtImage.dispose();
		}
	}

	/**
	 * Fills the given oval with the <em>foreground</em> color. This overrides
	 * the default SWT behaviour to be more like Swing.
	 */
	public void fillOval(final int x, final int y, final int width, final int height) {
		gc.fillOval(x + originX, y + originY, width, height);
	}

	/**
	 * Fills the given rectangle with the <em>foreground</em> color. This
	 * overrides the default SWT behaviour to be more like Swing.
	 */
	public void fillRect(final int x, final int y, final int width, final int height) {
		gc.fillRectangle(x + originX, y + originY, width, height);
	}

	public Rectangle getClipBounds() {
		final org.eclipse.swt.graphics.Rectangle r = gc.getClipping();
		return new Rectangle(r.x - originX, r.y - originY, r.width, r.height);
	}

	public ColorResource getColor() {
		return new SwtColor(gc.getForeground());
	}

	public FontResource getFont() {
		return new SwtFont(gc.getFont());
	}

	public FontMetrics getFontMetrics() {
		return new SwtFontMetrics(gc.getFontMetrics());
	}

	public int getLineStyle() {
		return lineStyle;
	}

	public int getLineWidth() {
		return gc.getLineWidth();
	}

	public Image getImage(final URL url) {
		final ImageData[] imageData = loadImageData(url);
		if (imageData != null && imageData.length > 0)
			return new SwtImage(imageData[0]);
		return new SwtImage(Display.getDefault().getSystemImage(SWT.ICON_ERROR).getImageData());
	}
	
	private static ImageData[] loadImageData(final URL url) {
		final ImageLoader imageLoader = new ImageLoader();
		try {
			final InputStream in = url.openStream();
			try {
				return imageLoader.load(in);
			} finally {
				in.close();
			}
		} catch (final IOException e) {
			VexPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, VexPlugin.ID, MessageFormat.format("Cannot load image from url: {0}", url), e));
			return null;
		}
	}

	public boolean isAntiAliased() {
		return false;
	}

	public void setAntiAliased(final boolean antiAliased) {
	}

	public ColorResource setColor(final ColorResource color) {
		final ColorResource oldColor = getColor();
		gc.setForeground(((SwtColor) color).getSwtColor());
		gc.setBackground(((SwtColor) color).getSwtColor());
		return oldColor;
	}

	public FontResource setFont(final FontResource font) {
		final FontResource oldFont = getFont();
		gc.setFont(((SwtFont) font).getSwtFont());
		return oldFont;
	}

	public void setLineStyle(final int lineStyle) {
		this.lineStyle = lineStyle;
		switch (lineStyle) {
		case LINE_DASH:
			gc.setLineStyle(SWT.LINE_DASH);
			break;
		case LINE_DOT:
			gc.setLineStyle(SWT.LINE_DOT);
			break;
		default:
			gc.setLineStyle(SWT.LINE_SOLID);
			break;
		}
	}

	public void setLineWidth(final int lineWidth) {
		gc.setLineWidth(lineWidth);
	}

	public int charsWidth(final char[] data, final int offset, final int length) {
		return stringWidth(new String(data, offset, length));
	}

	public ColorResource createColor(final Color rgb) {
		return new SwtColor(new org.eclipse.swt.graphics.Color(null, rgb.getRed(), rgb.getGreen(), rgb.getBlue()));
	}

	public FontResource createFont(final FontSpec fontSpec) {
		int style = SWT.NORMAL;
		if ((fontSpec.getStyle() & FontSpec.BOLD) > 0)
			style |= SWT.BOLD;
		if ((fontSpec.getStyle() & FontSpec.ITALIC) > 0)
			style |= SWT.ITALIC;
		final int size = Math.round(fontSpec.getSize() * 72 / 90); // TODO: fix. SWT
																	// uses pts, AWT
																	// uses device
																	// units
		final String[] names = fontSpec.getNames();
		final FontData[] fd = new FontData[names.length];
		for (int i = 0; i < names.length; i++)
			fd[i] = new FontData(names[i], size, style);
		return new SwtFont(new org.eclipse.swt.graphics.Font(null, fd));
	}

	public ColorResource getSystemColor(final int id) {

		if (id == ColorResource.SELECTION_BACKGROUND)
			return new SwtColor(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_SELECTION));
		else if (id == ColorResource.SELECTION_FOREGROUND)
			return new SwtColor(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
		else
			return new SwtColor(Display.getCurrent().getSystemColor(-1));
	}

	/**
	 * Sets the origin of this graphics object. See the class description for
	 * more details.
	 * 
	 * @param x
	 *            x-coordinate of the origin, relative to the viewport.
	 * @param y
	 *            y-coordinate of the origin, relative to the viewport.
	 */
	public void setOrigin(final int x, final int y) {
		originX = x;
		originY = y;
	}

	public int stringWidth(final String s) {
		return gc.stringExtent(s).x;
	}

	// ========================================================== PRIVATE

	private int lineStyle = LINE_SOLID;

}
