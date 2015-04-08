package com.Aty.AtyGL.graphics.buffers;

import static org.lwjgl.opengl.GL15.*;

import com.Aty.AtyGL.utils.BufferUtils;

public class IndexBuffer {
	private int bufferID;
	private int count;
	
	/**Sets the ID and count to -1*/
	public IndexBuffer(){
		this.bufferID = -1;
		this.count = -1;
	}
	
	public IndexBuffer(byte[] indices){
		this.count = indices.length;
		
		bufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public IndexBuffer(short[] indices){
		this.count = indices.length;
		
		bufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createShortBuffer(indices), GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public IndexBuffer(int[] indices){
		this.count = indices.length;
		
		bufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void bind(){
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferID);
	}
	
	public void unbind(){
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public int getID(){
		return bufferID;
	}
	
	public int getCount(){
		return count;
	}
	
	public void dispose(){
		glDeleteBuffers(bufferID);
	}
}
