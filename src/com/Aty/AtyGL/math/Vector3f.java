package com.Aty.AtyGL.math;

public class Vector3f {
	public float x, y, z;

	public Vector3f() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3f(float x, float y, float z) {
		set(x, y, z);
	}

	public Vector3f(Vector3f v) {
		this.set(v);
	}
	
	public Vector3f add(final Vector3f other){
		return this.set(x + other.x, y + other.y, z + other.z);
	}
	
	public Vector3f cross(final Vector3f other) {
		return this.set(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	public Vector3f divide(float divide) {
		this.x /= divide;
		this.y /= divide;
		this.z /= divide;
		return this;
	}
	
	public float dot(final Vector3f other){
		return x * other.x + y * other.y + z * other.z;
	}
	
	public boolean isEqual(Vector3f other){
		return x == other.x && y == other.y && z == other.z;
	}
	
	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}
	
	public float length(){
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float length2(){
		return x * x + y * y + z * z;
	}
	
	public Vector3f multiply(float multiplier) {
		this.x *= multiplier;
		this.y *= multiplier;
		this.z *= multiplier;
		return this;
	}
	
	public Vector3f normalize(){
		final float len2 = this.length2();
		if(len2 == 0.0f || len2 == 1.0f)
			return this;
		return this.divide((float)Math.sqrt(len2));
	}
	
	public Vector3f set(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3f set(final Vector3f v) {
		return this.set(v.x, v.y, v.z);
	}
	
	public Vector3f sub(final Vector3f other){
		return this.set(x - other.x, y - other.y, z - other.z);
	}

	public String toString(){
		return "[" + x + ", " + y + " ," + z + "]";
	}
}
	