package com.Aty.AtyGL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import com.Aty.AtyGL.graphics.Color;
import com.Aty.AtyGL.graphics.Drawable2D;
import com.Aty.AtyGL.graphics.Shader;
import com.Aty.AtyGL.graphics.Sprite;
import com.Aty.AtyGL.graphics.Window;
import com.Aty.AtyGL.graphics.camera.OrthographicCamera;
import com.Aty.AtyGL.graphics.renderer.BatchRenderer;

public class Main implements Runnable {

	private final int WIDTH = 1024;
	private final int HEIGHT = WIDTH / 16 * 9;

	private Thread thread;
	private boolean running = false;

	private Window window;

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	// TODO (delete) For debug only
	private Drawable2D[] sprites;
	private BatchRenderer batch;
	
	private void init() {
		window = new Window(WIDTH, HEIGHT);

		window.setClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glActiveTexture(GL_TEXTURE0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		System.out.println("OpenGL " + glGetString(GL_VERSION));

		Shader.loadAll();

		camera = new OrthographicCamera(window.getWidth(), window.getHeight());

		sprites = new Drawable2D[60000];
		for(int i = 0; i < 60000; i++){
			sprites[i] = new Sprite((float)(Math.random() * window.getWidth()), (float)(Math.random() * window.getHeight()), 10, 10, new Color((float)Math.random(), 0, 1, 1), Shader.defaultShader);
		}
		batch = new BatchRenderer();
	}
	
	public void run() {
		init();
		final double nano = 1000000000.0 / 60.0;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		long currentTime;
		
		while(running) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / nano;
			lastTime = currentTime;
			if(delta >= 1.0) {
				update();
				updates++;
				delta--;
			}

			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.setTitle("Updates: " + updates + " || Fps: " + frames);
				updates = 0;
				frames = 0;
			}
			// Poll if window is closed
			if(window.isClosed())
				running = false;
		}
		
		// Terminate OpenGL and release resources
		dispose();
		window.terminate();
	}

	private OrthographicCamera camera;
	private float x, y;
	private void update() {
		window.update();

		if(window.isKeyPressed(GLFW_KEY_W))
			camera.position.y += 1f;
		if(window.isKeyPressed(GLFW_KEY_S))
			camera.position.y -= 1f;
		if(window.isKeyPressed(GLFW_KEY_A))
			camera.position.x -= 1f;
		if(window.isKeyPressed(GLFW_KEY_D))
			camera.position.x += 1f;

		x = (float) window.getMouseX();
		y = (float) (window.getHeight() - window.getMouseY());
		camera.update();
		Shader.defaultShader.bind();
		Shader.defaultShader.setUniformMat4f("proj_mat", camera.projection);
		Shader.defaultShader.setUniformMat4f("view_mat", camera.view);
		Shader.defaultShader.setUniform2f("lightPos", x, y);
		Shader.defaultShader.unbind();
	}

	private void render() {
		window.clear();
		
		// TODO render here
		final int len = sprites.length;
		Shader.defaultShader.bind();
		batch.begin();
		for(int i = 0; i < len; i++)
			batch.submit(sprites[i]);

		batch.end();
		batch.flush();
		// ------------------
		
		window.SwapBuffers();
	}

	private void dispose(){
		Shader.disposeAll();
		batch.dispose();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}
