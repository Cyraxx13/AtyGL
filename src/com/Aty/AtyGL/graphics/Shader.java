package com.Aty.AtyGL.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.Aty.AtyGL.math.Matrix4f;
import com.Aty.AtyGL.utils.ShaderUtils;

public class Shader {
	public static final int A_VERTEX_POSITION = 0;
	public static final int A_TEXCOORD = 1;
	
	private static final Map<String, Integer> locationCahce = new HashMap<String, Integer>();
	public static Shader defaultShader;
	
	private final int ID;
	private boolean enabled = false;
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll(){
		defaultShader = new Shader("shaders/pass.vert", "shaders/pass.frag");
	}
	
	public static void disposeAll(){
		glDeleteShader(defaultShader.ID);
	}
	
	public int getUniform(String name) {
		if(locationCahce.containsKey(name))
			return locationCahce.get(name);
		
		int result = glGetUniformLocation(ID, name);
		if (result == -1)
			System.err.println("Could not find uniform variable " + name + "!");
		else
			locationCahce.put(name, result);
		return result;
	}

	public void setUniform1i(String name, int value) {
		if (!enabled) bind();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		if (!enabled) bind();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y) {
		if (!enabled) bind();
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, float x, float y, float z) {
		if (!enabled) bind();
		glUniform3f(getUniform(name), x, y, z);
	}
	
	public void setUniform4f(String name, float x, float y, float z, float q) {
		if (!enabled) bind();
		glUniform4f(getUniform(name), x, y, z, q);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix){
		if (!enabled) bind();
		glUniformMatrix4(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void bind() {
		glUseProgram(ID);
		enabled = true;
	}

	public void unbind() {
		glUseProgram(0);
		enabled = false;
	}
}
