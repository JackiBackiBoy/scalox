#version 330 core
layout (location = 0) in vec3 aPos;

uniform mat4 projMatrix;
uniform mat4 viewMatrix;

void main()
{
  gl_Position = projMatrix * vec4(aPos.x, aPos.y, aPos.z, 1.0);
}
