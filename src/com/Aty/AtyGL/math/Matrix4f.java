package com.Aty.AtyGL.math;

import java.nio.FloatBuffer;

import com.Aty.AtyGL.utils.BufferUtils;

public class Matrix4f {
	
	private static final Matrix4f tmpMat = new Matrix4f();
	private static final Vector3f tmpVec = new Vector3f();
	private static final Vector3f xVec = new Vector3f();
	private static final Vector3f yVec = new Vector3f();
	private static final Vector3f zVec = new Vector3f();
	
	public float[] elements = new float[4 * 4];

	public Matrix4f() {
		for (int i = 0; i < 4 * 4; i++) {
			elements[i] = 0.0f;
		}
	}

	public Matrix4f(float diagonal) {
		this();

		elements[0 + 0 * 4] = diagonal;
		elements[1 + 1 * 4] = diagonal;
		elements[2 + 2 * 4] = diagonal;
		elements[3 + 3 * 4] = diagonal;
	}

	public Matrix4f lookAt(Vector3f position, Vector3f target, Vector3f up){
		tmpVec.set(target).sub(position);
		zVec.set(tmpVec).normalize();
		xVec.set(tmpVec).normalize();
		xVec.cross(up).normalize();
		yVec.set(xVec).cross(zVec).normalize();
		
		this.setToIdentity();
		this.elements[0 + 0 * 4] = xVec.x;
		this.elements[0 + 1 * 4] = xVec.y;
		this.elements[0 + 2 * 4] = xVec.z;
		this.elements[1 + 0 * 4] = yVec.x;
		this.elements[1 + 1 * 4] = yVec.y;
		this.elements[1 + 2 * 4] = yVec.z;
		this.elements[2 + 0 * 4] = zVec.x;
		this.elements[2 + 1 * 4] = -zVec.y;
		this.elements[2 + 2 * 4] = -zVec.z;
		
		tmpMat.setToTranslation(position.x, position.y, position.z);
		this.mul(tmpMat);
		
		return this;
	}
	
	public Matrix4f mul(Matrix4f matrix) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				float sum = 0.0f;
				for (int k = 0; k < 4; k++) {
					sum += elements[j + k * 4] * matrix.elements[k + i * 4];
				}
				elements[j + i * 4] = sum;
			}
		}

		return this;
	}
	
	public Matrix4f set(final Matrix4f other){
		for(int i = 0; i < 4 * 4; i++){
			elements[i] = other.elements[i];
		}
		
		return this;
	}
	
	public Matrix4f setToIdentity() {
		for (int i = 0; i < 4 * 4; i++) {
			elements[i] = 0.0f;
		}
		elements[0 + 0 * 4] = 1.0f;
		elements[1 + 1 * 4] = 1.0f;
		elements[2 + 2 * 4] = 1.0f;
		elements[3 + 3 * 4] = 1.0f;

		return this;
	}
	
	public Matrix4f setToTranslation(float x, float y, float z){
		this.setToIdentity();
		this.elements[0 + 3 * 4] = x;
		this.elements[1 + 3 * 4] = y;
		this.elements[2 + 3 * 4] = z;
		
		return this;
	}
	
	public Matrix4f setToTranslation(Vector3f position){
		return this.setToTranslation(position.x, position.y, position.z);
	}
	
	public Matrix4f translate(final float x, final float y, final float z) {
		this.elements[0 + 3 * 4] = x;
		this.elements[1 + 3 * 4] = y;
		this.elements[2 + 3 * 4] = z;

		return this;
	}
	
	public Matrix4f translate(final Vector3f translation) {
		translate(translation.x, translation.y, translation.z);
		return this;
	}
	
	public Matrix4f setToOrthographic(float left, float right, float bottom, float top, float near, float far) {
		this.setToIdentity();

		elements[0 + 0 * 4] = 2.0f / (right - left);
		elements[1 + 1 * 4] = 2.0f / (top - bottom);
		elements[2 + 2 * 4] = 2.0f / (near - far);

		elements[0 + 3 * 4] = (left + right) / (left - right);
		elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		elements[2 + 3 * 4] = (far + near) / (far - near);

		return this;
	}

	public static Matrix4f identity() {
		return new Matrix4f(1.0f);
	}

	// View matrices
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f ortho = identity();

		ortho.elements[0 + 0 * 4] = 2.0f / (right - left);
		ortho.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		ortho.elements[2 + 2 * 4] = 2.0f / (near - far);

		ortho.elements[0 + 3 * 4] = (left + right) / (left - right);
		ortho.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		ortho.elements[2 + 3 * 4] = (far + near) / (far - near); // TODO Lehet bugos (- kell elé?)

		return ortho;
	}

	public static Matrix4f perspective(float fovDeg, float aspectRatio, float near, float far) {
		Matrix4f perspective = identity();

		float q = 1.0f / (float) Math.tan(Math.toRadians(fovDeg));
		float a = q / aspectRatio;

		float b = (near + far) / (near - far);
		float c = (2.0f * near * far) / (near - far);

		perspective.elements[0 + 0 * 4] = a;
		perspective.elements[1 + 1 * 4] = q;
		perspective.elements[2 + 2 * 4] = b;
		perspective.elements[3 + 2 * 4] = -1.0f;
		perspective.elements[2 + 3 * 4] = c;

		return null;
	}

	// Transformation matrices
	public static Matrix4f sTranslate(Vector3f translation) {
		Matrix4f translated = identity();

		translated.elements[0 + 3 * 4] = translation.x;
		translated.elements[1 + 3 * 4] = translation.y;
		translated.elements[2 + 3 * 4] = translation.z;

		return translated;
	}

	public static Matrix4f rotation(float angleDeg, Vector3f axis) {
		Matrix4f rotated = identity();

		float rad = (float) Math.toRadians(angleDeg);
		float c = (float) Math.cos(rad);
		float s = (float) Math.sin(rad);
		float omc = 1.0f - c;

		rotated.elements[0 + 0 * 4] = axis.x * omc + c;
		rotated.elements[1 + 0 * 4] = axis.y * axis.x * omc + axis.z * s;
		rotated.elements[2 + 0 * 4] = axis.x * axis.z * omc - axis.y * s;

		rotated.elements[0 + 1 * 4] = axis.x * axis.y * omc - axis.z * s;
		rotated.elements[1 + 1 * 4] = axis.y * omc + c;
		rotated.elements[2 + 1 * 4] = axis.y * axis.z * omc + axis.x * s;

		rotated.elements[0 + 2 * 4] = axis.x * axis.z * omc + axis.y * s;
		rotated.elements[1 + 2 * 4] = axis.y * axis.z * omc - axis.x * s;
		rotated.elements[2 + 2 * 4] = axis.z * omc + c;

		return rotated;
	}

	public static Matrix4f scale(Vector3f scale) {
		Matrix4f scaled = identity();

		scaled.elements[0 + 0 * 4] = scale.x;
		scaled.elements[1 + 1 * 4] = scale.y;
		scaled.elements[2 + 2 * 4] = scale.z;

		return scaled;
	}

	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements);
	}

	public String toString(){
		String result = "";
		result += "[" + elements[0 + 0 * 4] + ", " + elements[0 + 1 * 4] + ", " + elements[0 + 2 * 4] + ", " + elements[0 + 3 * 4] + "]\n";
		result += "[" + elements[1 + 0 * 4] + ", " + elements[1 + 1 * 4] + ", " + elements[1 + 2 * 4] + ", " + elements[1 + 3 * 4] + "]\n";
		result += "[" + elements[2 + 0 * 4] + ", " + elements[2 + 1 * 4] + ", " + elements[2 + 2 * 4] + ", " + elements[2 + 3 * 4] + "]\n";
		result += "[" + elements[3 + 0 * 4] + ", " + elements[3 + 1 * 4] + ", " + elements[3 + 2 * 4] + ", " + elements[3 + 3 * 4] + "]\n";
		
		return result;
	}
}
