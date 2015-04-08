package com.Aty.AtyGL.graphics.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.Aty.AtyGL.graphics.Drawable2D;
import com.Aty.AtyGL.graphics.buffers.IndexBuffer;
import com.Aty.AtyGL.utils.Constants;

public class BatchRenderer implements Renderer2D {
	/**Maximum number of sprites.*/
	private static final int RENDERER_MAX_SPRITES = 60000;
	/**Combined vertex attribute size in bytes.*/
	private static final int RENDERER_VERTEX_SIZE = Constants.VERTEX_SIZE_IN_BYTES;
	/**Total size of a sprite in bytes.*/
	private static final int RENDERER_SPRITE_SIZE = RENDERER_VERTEX_SIZE * 4;
	/**Total size of the buffer in bytes.*/
	private static final int RENDERER_BUFFER_SIZE = RENDERER_MAX_SPRITES * RENDERER_SPRITE_SIZE;
	/**Number of indices required.*/
	private static final int RENDERER_INDEX_SIZE = RENDERER_MAX_SPRITES * 6;

	private static final int SHADER_VERTEX_INDEX = 0;
	private static final int SHADER_COLOR_INDEX = 1;

//	private Vector3f pos = new Vector3f();

	private int vao;
	private int vbo;
	private IndexBuffer ibo;
	private int indexCount;
	private FloatBuffer buffer;

	public BatchRenderer() {
		init();
	}

	private void init() {
		vao = glGenVertexArrays();
		vbo = glGenBuffers();

		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, RENDERER_BUFFER_SIZE, null, GL_DYNAMIC_DRAW);

		// Enabling the attribute locations
		glEnableVertexAttribArray(SHADER_VERTEX_INDEX);
		glEnableVertexAttribArray(SHADER_COLOR_INDEX);

		// Creating the attributes
		glVertexAttribPointer(SHADER_VERTEX_INDEX, 3, GL_FLOAT, false, RENDERER_VERTEX_SIZE, 0);
		glVertexAttribPointer(SHADER_COLOR_INDEX, 4, GL_UNSIGNED_BYTE, true, RENDERER_VERTEX_SIZE, Constants.POSITION_IN_BYTES);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		// Generating the indices
		int offset = 0;
		int[] indices = new int[RENDERER_INDEX_SIZE];
		for(int i = 0; i < RENDERER_INDEX_SIZE; i += 6) {
			indices[i + 0] = offset + 0;
			indices[i + 1] = offset + 1;
			indices[i + 2] = offset + 2;

			indices[i + 3] = offset + 2;
			indices[i + 4] = offset + 3;
			indices[i + 5] = offset + 0;

			offset += 4;
		}

		ibo = new IndexBuffer(indices);

		glBindVertexArray(0);
		
	}
	public void begin() {
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
//		buffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer = glMapBufferRange(GL_ARRAY_BUFFER, 0, RENDERER_BUFFER_SIZE, GL_MAP_WRITE_BIT | GL_MAP_UNSYNCHRONIZED_BIT).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	@Override
	public void submit(Drawable2D drawable) {
//		pos = drawable.getPosition();
		// 0,0
		buffer.put(drawable.getPosition().x).put(drawable.getPosition().y).put(drawable.getPosition().z);
		buffer.put(drawable.getFloatBitColor());

		// 0,1
		buffer.put(drawable.getPosition().x).put(drawable.getPosition().y + drawable.getSize().y).put(drawable.getPosition().z);
		buffer.put(drawable.getFloatBitColor());

		// 1,1
		buffer.put(drawable.getPosition().x + drawable.getSize().x).put(drawable.getPosition().y + drawable.getSize().y).put(drawable.getPosition().z);
		buffer.put(drawable.getFloatBitColor());

		// 1,0
		buffer.put(drawable.getPosition().x + drawable.getSize().x).put(drawable.getPosition().y).put(drawable.getPosition().z);
		buffer.put(drawable.getFloatBitColor());

		indexCount += 6;
	}

	public void end() {
		glUnmapBuffer(GL_ARRAY_BUFFER);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void flush() {
		glBindVertexArray(vao);
		ibo.bind();

		glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);

		ibo.unbind();
		glBindVertexArray(0);

		indexCount = 0;
	}

	public void dispose() {
		buffer.clear();
		ibo.dispose();
		glDeleteBuffers(vao);
		glDeleteBuffers(vbo);
	}
}
