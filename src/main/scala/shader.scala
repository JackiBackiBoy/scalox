package scala3d

import org.lwjgl.opengl.GL20._

class Shader:
  private var shaderIDs: scala.collection.mutable.Map[ShaderType, Int] = scala.collection.mutable.Map()
  var id: Int = 0

  def loadShader(shaderType: ShaderType, filePath: String): Unit =
    // Check if file extension is valid for the given shader file, else ignore attaching shader
      var shaderString = scala.io.Source.fromFile(filePath).mkString
      println(shaderString)

      shaderIDs.addOne(shaderType -> glCreateShader(shaderType.typeID))
      glShaderSource(shaderIDs(shaderType), shaderString)
      glCompileShader(shaderIDs(shaderType))
      

  def createProgram(): Unit =
    id = glCreateProgram()

    shaderIDs.foreach((k, v) => glAttachShader(id, v)) // attach all shaders
    glLinkProgram(id); // unite all attached shaders into one program
    shaderIDs.foreach((k, v) => glDeleteShader(v)) // deallocate the standalone shaders
