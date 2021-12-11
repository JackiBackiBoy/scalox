package scalox

import java.nio._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import org.lwjgl.BufferUtils
import org.lwjgl.system.MemoryUtil

class RenderingWindow extends Window("Scala 3D | Jack Henrikson", 1240, 720):
  val vertices = Array[Float](-0.5f, -0.5f, 0.0f,
                               0.5f, -0.5f, 0.0f,
                               0.0f,  0.5f, 0.0f)
  var vao: Int = 0
  var vbo: Int = 0
  var shader: Shader = new Shader();

  override def onStart(): Unit =
    // Vertex array object (VAO)
    vao = glGenVertexArrays()
    glBindVertexArray(vao)

    var verticesBuffer: FloatBuffer = MemoryUtil.memAllocFloat(vertices.length)
    verticesBuffer.put(vertices).flip()

    // Vertex buffer object (VBO)
    vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW)

    // Vertex attributes
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0) // positions (x, y, z)
    glEnableVertexAttribArray(0)

    shader.loadShader(ShaderType.Vertex, "assets/shaders/lighting_shader.vert")
    shader.loadShader(ShaderType.Fragment, "assets/shaders/lighting_shader.frag")
    shader.createProgram();

  override def onUpdate(deltaTime: Double): Unit =
    println("Update")
    
  override def onRender(deltaTime: Double): Unit =
    glUseProgram(shader.id)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)
