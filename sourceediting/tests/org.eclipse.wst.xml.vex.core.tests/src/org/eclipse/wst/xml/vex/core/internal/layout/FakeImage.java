package org.eclipse.wst.xml.vex.core.internal.layout;

import java.net.URL;

import org.eclipse.wst.xml.vex.core.internal.core.Image;

public class FakeImage implements Image {
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
