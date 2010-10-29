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
package org.eclipse.wst.xml.vex.core.internal.layout;

import java.net.URL;

import org.eclipse.core.runtime.Assert;
import org.eclipse.wst.xml.vex.core.internal.core.Color;
import org.eclipse.wst.xml.vex.core.internal.core.ColorResource;
import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.core.FontMetrics;
import org.eclipse.wst.xml.vex.core.internal.core.FontResource;
import org.eclipse.wst.xml.vex.core.internal.core.FontSpec;
import org.eclipse.wst.xml.vex.core.internal.core.Graphics;
import org.eclipse.wst.xml.vex.core.internal.core.Image;
import org.eclipse.wst.xml.vex.core.internal.core.Rectangle;

/**
 * A pseudo-Graphics class that returns a known set of font metrics.
 */
public class FakeGraphics implements Graphics {

	private int charWidth = 6;

	private URL lastDrawnImageUrl = null;

	public FakeGraphics() {
		DisplayDevice.setCurrent(new DisplayDevice() {
			public int getHorizontalPPI() {
				return 72;
			}

			public int getVerticalPPI() {
				return 72;
			}
		});
	}

	private FontMetrics fontMetrics = new FontMetrics() {
		public int getAscent() {
			return 10;
		}

		public int getDescent() {
			return 3;
		}

		public int getHeight() {
			return 13;
		}

		public int getLeading() {
			return 2;
		}
	};

	private static class FakeImage implements Image {
		public final URL url;
		private int height;
		private int width;

		public FakeImage(final URL url) {
			this(url, 0, 0);
		}

		public FakeImage(final URL url, final int width, final int height) {
			this.url = url;
			this.height = height;
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public int getWidth() {
			return width;
		}
	}

	public int charsWidth(char[] data, int offset, int length) {
		return length * charWidth;
	}

	public ColorResource createColor(Color rgb) {
		return new ColorResource() {
			public void dispose() {
			}
		};
	}

	public FontResource createFont(FontSpec fontSpec) {
		return new FontResource() {
			public void dispose() {
			}
		};
	}

	public void dispose() {
	}

	public void drawChars(char[] chars, int offset, int length, int x, int y) {
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
	}

	public void drawString(String s, int x, int y) {
	}

	public void drawOval(int x, int y, int width, int height) {
	}

	public void drawRect(int x, int y, int width, int height) {
	}

	public void drawImage(final Image image, final int x, final int y, final int width, final int height) {
		Assert.isTrue(image instanceof FakeImage);
		lastDrawnImageUrl = ((FakeImage) image).url;
	}

	public URL getLastDrawnImageUrl() {
		return lastDrawnImageUrl;
	}

	public void fillOval(int x, int y, int width, int height) {
	}

	public void fillRect(int x, int y, int width, int height) {
	}

	public Rectangle getClipBounds() {
		return null;
	}

	public ColorResource getBackgroundColor() {
		return null;
	}

	public ColorResource getColor() {
		return null;
	}

	public FontResource getFont() {
		return null;
	}

	public int getLineStyle() {
		return 0;
	}

	public int getLineWidth() {
		return 0;
	}

	public ColorResource getSystemColor(int id) {
		return null;
	}

	public FontMetrics getFontMetrics() {
		return this.fontMetrics;
	}

	public Image getImage(URL url) {
		return new FakeImage(url);
	}

	public boolean isAntiAliased() {
		return false;
	}

	public void setAntiAliased(boolean antiAliased) {
	}

	public ColorResource setBackgroundColor(ColorResource color) {
		return null;
	}

	public ColorResource setColor(ColorResource color) {
		return null;
	}

	public FontResource setFont(FontResource font) {
		return null;
	}

	public void setLineStyle(int style) {
	}

	public void setLineWidth(int width) {
	}

	public int stringWidth(String s) {
		return charWidth * s.length();
	}

	public int getCharWidth() {
		return this.charWidth;
	}

	public void setXORMode(boolean xorMode) {
	}
}
