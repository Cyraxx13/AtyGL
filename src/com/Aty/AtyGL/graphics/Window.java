package com.Aty.AtyGL.graphics;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

public class Window {
	private final int MAX_KEYS = 1024;
	private final int MAX_BUTTONS = 32;

	private boolean[] keys;
	private boolean[] buttons;
	private double mx, my;

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseCallback;
	private GLFWCursorPosCallback cursorPosCallback;
	private GLFWWindowSizeCallback windowSizeCallback;

	private long window;
	private int width, height;

	public Window(int width, int height) {
		this.width = width;
		this.height = height;

		keys = new boolean[MAX_KEYS];
		buttons = new boolean[MAX_BUTTONS];

		init();
	}

	public void init() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW!");

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "AtyGL", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create GLFW window!");
	
		// TODO released repeated pressed once methods
		keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				keys[key] = action != GLFW_RELEASE;
			}
		};
		mouseCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				buttons[button] = action != GLFW_RELEASE;
			}
		};

		cursorPosCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				mx = xpos;
				my = ypos;
			}
		};

		windowSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				setSize(width, height);
			}
		};
		
		glfwSetKeyCallback(window, keyCallback);
		glfwSetMouseButtonCallback(window, mouseCallback);
		glfwSetCursorPosCallback(window, cursorPosCallback);
		glfwSetWindowSizeCallback(window, windowSizeCallback);

		ByteBuffer vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidMode) - width) / 2, (GLFWvidmode.height(vidMode) - height) / 2);

		glfwMakeContextCurrent(window);
		// V-sync 1 = on | 0 = off
		glfwSwapInterval(0);
		glfwShowWindow(window);
		GLContext.createFromCurrent();
	}

	public void close() {
		glfwSetWindowShouldClose(window, GL_TRUE);
	}
	
	/**Clears the color and depth buffer bit.*/
	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void clear(int bit){
		glClear(bit);
	}
	
	public boolean isClosed() {
		return glfwWindowShouldClose(window) == 1;
	}

	public boolean isKeyPressed(int key) {
		if (key < 0 || key >= MAX_KEYS)// TODO s'thing errory
			return false;
		return keys[key];
	}

	public boolean isButtonPressed(int button) {
		if (button < 0 || button >= MAX_BUTTONS)// TODO s'thing errory
			return false;
		return buttons[button];
	}

	public void processEvents() {
		glfwPollEvents();
	}

	private void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		glfwSetWindowSize(window, width, height);
		glViewport(0, 0, width, height);
	}

	public void setTitle(String title) {
		glfwSetWindowTitle(window, title);
	}

	public void setClearColor(float r, float g, float b, float a){
		glClearColor(r, g, b, a);
	}
	
	public void SwapBuffers() {
		glfwSwapBuffers(window);
	}

	public void update(){
		processEvents();
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.err.println(error);
		
		if (isKeyPressed(GLFW_KEY_ESCAPE))
			close();
	}
	
	public void vSync(boolean on){
		glfwSwapInterval(on ? 1 : 0);
	}
	
	public void terminate() {
		glfwDestroyWindow(window);
		keyCallback.release();
		mouseCallback.release();
		cursorPosCallback.release();
		windowSizeCallback.release();
		glfwTerminate();
		errorCallback.release();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getMouseX() {
		return mx;
	}

	public double getMouseY() {
		return my;
	}
	
}
