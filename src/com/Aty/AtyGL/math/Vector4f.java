package com.Aty.AtyGL.math;

public class Vector4f {
	public float x, y, z, w;

	public Vector4f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}

	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector4f v){
		this(v.x, v.y, v.z, v.w);
	}
	
	public Vector4f(Vector3f v, float w){
		this(v.x, v.y, v.z, w);
	}
}
