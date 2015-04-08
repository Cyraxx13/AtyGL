package com.Aty.AtyGL.graphics;

import com.Aty.AtyGL.math.Vector4f;

public class Color {
	public float r, g, b, a;

	public Color() {
		this.r = 0;
		this.b = 0;
		this.g = 0;
		this.a = 0;
	}

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.b = b;
		this.g = g;
		this.a = a;
	}
	
	public Color(Color c){
		this(c.r, c.g, c.b, c.a);
	}
	
	public Color(Vector4f v){
		this(v.x, v.y, v.z, v.w);
	}
	
	public void set(Color c){
		this.r = c.r;
		this.b = c.b;
		this.g = c.g;
		this.a = c.a;
	}
	
	public float toFloatBits(){
		int intBits = (int) (255 * a) << 24 | (int) (255 * b) << 16 | (int) (255 * g) << 8 | (int) (255 * r);
		return Float.intBitsToFloat(intBits & 0xfeffffff);
	}
}
