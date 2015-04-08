package com.Aty.AtyGL.graphics.buffers;

import static org.lwjgl.opengl.GL15.*;

import com.Aty.AtyGL.utils.BufferUtils;

public class VertexBuffer {

	private int bufferID;	
	private int componentCount;
	
	public VertexBuffer(float[] data, int componentCount){
		this.componentCount = componentCount;

		bufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void bind(){
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
	}
	
	public void unbind(){
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public int getComponentCount(){
		return componentCount;
	}
	
	public void dispose(){
		glDeleteBuffers(bufferID);
	}
}
