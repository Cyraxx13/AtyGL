package com.Aty.AtyGL.graphics.renderer;

import com.Aty.AtyGL.graphics.Drawable2D;

public interface Renderer2D {
	abstract void submit(Drawable2D drawable);
	abstract void flush();
}
