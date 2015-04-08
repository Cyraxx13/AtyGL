package com.Aty.AtyGL.math;

public class Vector2f {
	public float x, y;
	
	public Vector2f(){
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2f(float x, float y){
		set(x, y);
	}
	
	public Vector2f(Vector2f vector){
		set(vector);
	}
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2f vector){
		this.x = vector.x;
		this.y = vector.y;
	}
}
