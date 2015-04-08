#version 330 core

layout (location = 0) out vec4 color;

uniform vec2 lightPos;

in vec4 v_position;
in vec4 v_color;

void main(){
	float intensity = 64.0 / length(v_position.xy - lightPos);
	color = v_color * intensity;
	color.a = 1.0;
}