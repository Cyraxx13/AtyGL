package com.Aty.AtyGL.utils;

public interface Constants {
	/**Vertex position attribute size in bytes.*/
	public static final int POSITION_IN_BYTES = 3 * 4;
	/**Vertex color attribute size in bytes.*/
	public static final int COLOR_IN_BYTES = 1 * 4;
	/**Combined vertex attribute size in bytes.*/
	public static final int VERTEX_SIZE_IN_BYTES = POSITION_IN_BYTES + COLOR_IN_BYTES;
}
