package com.Aty.AtyGL.graphics;

import com.Aty.AtyGL.graphics.buffers.IndexBuffer;
import com.Aty.AtyGL.graphics.buffers.VertexBuffer;
import com.Aty.AtyGL.math.Vector2f;
import com.Aty.AtyGL.math.Vector3f;

public class StaticSprite extends Drawable2D{

	private VertexArray vao;
	private IndexBuffer ibo;
	private Shader shader;
	
	public StaticSprite(float x, float y, float width, float height, Color color, Shader shader){
		
		super(new Vector3f(x, y, 0.0f), new Vector2f(width, height), color);
		this.shader = shader;
		// TODO debug only
		float[] data = new float[]{
				000f,  000f,    0f,
				000f,  height,  0f,
				width, height,  0f,
				width, 000f,    0f
		};
		
		float[] colors = new float[]{
				color.r, color.g, color.b, color.a,
				color.r, color.g, color.b, color.a,
				color.r, color.g, color.b, color.a,
				color.r, color.g, color.b, color.a,
		};
		
		vao = new VertexArray();
		vao.addBuffer(new VertexBuffer(data, 3), 0);
		vao.addBuffer(new VertexBuffer(colors, 4), 1);
		
		byte[] indices = new byte[]{
				0, 1, 2,
				2, 3, 0
		};
		
		ibo = new IndexBuffer(indices);
	}

	public VertexArray getVAO(){
		return vao;
	}
	
	public IndexBuffer getIBO(){
		return ibo;
	}
	
	public Shader getShader(){
		return shader;
	}
}
