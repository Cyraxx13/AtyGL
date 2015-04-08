package com.Aty.AtyGL.graphics.camera;

import com.Aty.AtyGL.math.Vector3f;


public class OrthographicCamera extends Camera{
	private static final Vector3f tmp = new Vector3f();
	
	public float zoom = 1.0f;
	
	public OrthographicCamera(float viewportWidth, float viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
	}
	
	@Override
	public void update() {
		projection.setToOrthographic(0, zoom * viewportWidth, 0, zoom * viewportHeight, near, far);
		view.lookAt(position, tmp.set(position).add(toEye), up);
		combined.set(projection).mul(view);
		
	}

}
