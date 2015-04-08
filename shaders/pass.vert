#version 330 core

layout (location = 0) in vec4 a_position;
layout (location = 1) in vec4 a_color;

uniform mat4 proj_mat;
uniform mat4 view_mat = mat4(1.0);
uniform mat4 model_mat = mat4(1.0);

out vec4 v_position;
out vec4 v_color;

void main(){
	v_position = model_mat * a_position; 
	v_color = a_color;
	gl_Position = proj_mat * view_mat * model_mat * a_position;
}