package scala3d

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import java.nio._
import org.lwjgl.BufferUtils
import org.lwjgl.system.MemoryUtil

class RenderingWindow extends Window("Scala 3D | Jack Henrikson", 1240, 720):
  val vertices = Array[Float](-0.5f, -0.5f, 0.0f,
                               0.5f, -0.5f, 0.0f,
                               0.0f,  0.5f, 0.0f)
  var vao: Int = 0
  var vbo: Int = 0
  var shaderProgram: Int = 0

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

    // Shader
    val vertexShaderSource: CharSequence = "#version 330 core\n" +
                       "layout (location = 0) in vec3 aPos;\n" +
                       "void main()\n" +
                       "{\n" +
                       "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
                       "}"

    val fragmentShaderSource: CharSequence = "#version 330 core\n" +
                       "out vec4 FragColor;\n" +
                       "void main()\n" +
                       "{\n" +
                       "   FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
                       "}"

    val vertexShaderID = glCreateShader(GL_VERTEX_SHADER)
    glShaderSource(vertexShaderID, vertexShaderSource)
    glCompileShader(vertexShaderID)

    val fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER)
    glShaderSource(fragmentShaderID, fragmentShaderSource)
    glCompileShader(fragmentShaderID)

    shaderProgram = glCreateProgram()
    glAttachShader(shaderProgram, vertexShaderID)
    glAttachShader(shaderProgram, fragmentShaderID)
    glLinkProgram(shaderProgram)
    glUseProgram(shaderProgram)
    glDeleteShader(vertexShaderID)
    glDeleteShader(fragmentShaderID)

  override def onUpdate(deltaTime: Double): Unit =
    println("Update")
    
  override def onRender(deltaTime: Double): Unit =
    glUseProgram(shaderProgram)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)
