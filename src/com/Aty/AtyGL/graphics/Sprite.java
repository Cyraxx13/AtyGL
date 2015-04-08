package com.Aty.AtyGL.graphics;

import com.Aty.AtyGL.math.Vector2f;
import com.Aty.AtyGL.math.Vector3f;

public class Sprite extends Drawable2D {
	
	public Sprite(float x, float y, float width, float height, Color color, Shader shader) {
		super(new Vector3f(x, y, 0.0f), new Vector2f(width, height), color);
	}
}
