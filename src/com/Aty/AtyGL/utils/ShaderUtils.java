package com.Aty.AtyGL.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {

	private ShaderUtils() {
	}

	public static int load(String vertPath, String fragPath) {
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		return create(vert, frag);
	}

	private static int create(String vert, String frag) {
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);

		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}
//		if(glGetShaderInfoLog(vertID) != "")
//			System.out.println(glGetShaderInfoLog(program));
		
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}
//		if(glGetShaderInfoLog(fragID) != "")
//			System.out.println(glGetShaderInfoLog(program));
		
		
		
		glAttachShader(program, vertID);
		glAttachShader(program, fragID);
		
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) != GL_TRUE){
			System.err.println("Failed to link shader program!");
			System.err.println(glGetProgramInfoLog(program));
		}
		
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) != GL_TRUE){
			System.err.println("Failed to validate shader program!");
			System.err.println(glGetProgramInfoLog(program));
		}
		
		glDetachShader(program, vertID);
		glDetachShader(program, fragID);
		glDeleteShader(vertID);
		glDeleteShader(fragID);
		
		return program;
	}
}
