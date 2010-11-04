package org.eclipse.wst.xml.vex.core.internal.layout;

import org.eclipse.wst.xml.vex.core.internal.core.Image;

public class ImageBox extends AbstractBox implements InlineBox {

	private final Image image;

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
		return 0;
	}

	public boolean isEOL() {
		return false;
	}

	public Pair split(LayoutContext context, int maxWidth, boolean force) {
		return new Pair(null, this);
	}
}
