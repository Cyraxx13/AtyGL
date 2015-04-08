package com.Aty.AtyGL.graphics.camera;

import com.Aty.AtyGL.math.Matrix4f;
import com.Aty.AtyGL.math.Vector3f;

public abstract class Camera {
	public final Matrix4f view = new Matrix4f();
	public final Matrix4f projection = new Matrix4f();
	public final Matrix4f combined = new Matrix4f();
	public final Matrix4f invProjecionView = new Matrix4f();

	public final Vector3f position = new Vector3f(0.0f, 0.0f, 1.0f);
	/** Direction vector. */
	public final Vector3f toEye = new Vector3f(0.0f, 0.0f, -1.0f);
	public final Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
	public final Vector3f target = new Vector3f(512f, 288f, 0.0f);
	
	public float near = 0.0f;
	public float far = 100.0f;

	public float viewportWidth = 0.0f;
	public float viewportHeight = 0.0f;

	public abstract void update();
}
