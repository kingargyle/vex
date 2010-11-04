package org.eclipse.wst.xml.vex.core.internal.layout;

import java.net.URL;

import org.eclipse.wst.xml.vex.core.internal.core.Image;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;

public class ImageBox extends AbstractBox implements InlineBox {

	private final Image image;

	public static ImageBox create(final VEXElement element, final LayoutContext context, final int maxWidth) {
		if (element == null)
			return null;
		final URL imageUrl = context.resolveUrl(element.getBaseURI(), context.getStyleSheet().getStyles(element).getBackgroundImage());
		if (imageUrl == null)
			return null;
		
		final Image image = context.getGraphics().getImage(imageUrl);
		final int width = Math.min(image.getWidth(), maxWidth);
		final int height = Math.round(((1f * width) / image.getWidth()) * image.getHeight());

		final ImageBox result = new ImageBox(image);
		result.setWidth(width);
		result.setHeight(height);
		return result;
	}
	
	public static ImageBox createWithHeight(final VEXElement element, final LayoutContext context, final int maxHeight) {
		if (element == null)
			return null;
		final URL imageUrl = context.resolveUrl(element.getBaseURI(), context.getStyleSheet().getStyles(element).getBackgroundImage());
		if (imageUrl == null)
			return null;
		
		final Image image = context.getGraphics().getImage(imageUrl);
		final int height = Math.min(image.getHeight(), maxHeight);
		final int width = Math.round(((1f * height) / image.getHeight()) * image.getWidth());
		
		final ImageBox result = new ImageBox(image);
		result.setWidth(width);
		result.setHeight(height);
		return result;
	}
	
	public ImageBox(final Image image) {
		this.image = image;
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}
	
	@Override
	public void paint(LayoutContext context, int x, int y) {
		if (this.skipPaint(context, x, y))
			return;
		context.getGraphics().drawImage(image, x, y, getWidth(), getHeight());
		super.paint(context, x, y);
	}
	
	public int getBaseline() {
		return getHeight();
	}

	public boolean isEOL() {
		return false;
	}

	public Pair split(LayoutContext context, int maxWidth, boolean force) {
		return new Pair(null, this);
	}
}
