package com.Aty.AtyGL.graphics;

import com.Aty.AtyGL.math.Vector2f;
import com.Aty.AtyGL.math.Vector3f;

public abstract class Drawable2D {
	protected final Vector3f position;
	protected final Vector2f size;
	protected float color;
	
	public Drawable2D(){
		position = new Vector3f();
		size = new Vector2f();
	}
	
	public Drawable2D(Vector3f position, Vector2f size, Color color){
		this.position = position;
		this.size = size;
		this.color = color.toFloatBits();
		
	}
	
	public Vector3f getPosition(){
		return position;
	}
	
	public final Vector2f getSize(){
		return size;
	}
	
	public final float getFloatBitColor(){
		return color;
	}
}
