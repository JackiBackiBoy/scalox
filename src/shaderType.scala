package scalox

import org.lwjgl.opengl.GL20._

enum ShaderType(val typeID: Int, val fileExtension: String) {
  case Vertex   extends ShaderType(GL_VERTEX_SHADER, "vert")
  case Fragment extends ShaderType(GL_FRAGMENT_SHADER, "frag")
}
