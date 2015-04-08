package com.Aty.AtyGL.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.Vector;

import com.Aty.AtyGL.graphics.buffers.VertexBuffer;

public class VertexArray {

	private Vector<VertexBuffer> vertexData = null;
	private int ID;

	/**Creates an empty Vertex Array. If this constructor is used we need to use {@link addVertexData()}!*/
	public VertexArray() {
		ID = glGenVertexArrays();
		vertexData = new Vector<VertexBuffer>(2);
	}

	public void addBuffer(VertexBuffer data, int shaderIndex) {
		bind();
		data.bind();

		glEnableVertexAttribArray(shaderIndex);
		glVertexAttribPointer(shaderIndex, data.getComponentCount(), GL_FLOAT, false, 0, 0);

		data.unbind();
		unbind();

		vertexData.addElement(data);
	}

	public void bind() {
		glBindVertexArray(ID);
	}

	public void unbind() {
		glBindVertexArray(0);
	}
	
	public void dispose(){
		glDeleteBuffers(ID);
	}
}
