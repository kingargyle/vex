package org.eclipse.wst.xml.vex.core.internal.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.net.URL;

import org.eclipse.wst.xml.vex.core.internal.core.Image;
import org.junit.Test;

public class ImageBoxTest {
	
	@Test
	public void createImageBox() throws Exception {
		final FakeImage image = new FakeImage(new URL("file://image.jpg"), 123, 456);
		final ImageBox imageBox = new ImageBox(image);
		assertEquals(image.getWidth(), imageBox.getWidth());
		assertEquals(image.getHeight(), imageBox.getHeight());
	}
	
	@Test
	public void paintImageBox() throws Exception {
		final FakeImage image = new FakeImage(new URL("file://image.jpg"), 123, 456);
		final FakeGraphics graphics = new FakeGraphics();
		final LayoutContext context = new LayoutContext();
		context.setGraphics(graphics);
		final ImageBox imageBox = new ImageBox(image);
		imageBox.paint(context, 0, 0);
		assertSame(image.url, graphics.getLastDrawnImageUrl());
	}
	
	@Test
	public void scaleImage() throws Exception {
		final FakeImage image = new FakeImage(new URL("file://image.jpg"), 123, 456);
		final int[] drawnImageSize = new int[2];
		final FakeGraphics graphics = new FakeGraphics() {
			public void drawImage(Image image, int x, int y, int width, int height) {
				super.drawImage(image, x, y, width, height);
				drawnImageSize[0] = width;
				drawnImageSize[1] = height;
			};
		};
		final LayoutContext context = new LayoutContext();
		context.setGraphics(graphics);
		final ImageBox imageBox = new ImageBox(image);
		imageBox.setWidth(12);
		imageBox.setHeight(34);
		imageBox.paint(context, 0, 0);
		assertEquals(12, drawnImageSize[0]);
		assertEquals(34, drawnImageSize[1]);
	}
}
