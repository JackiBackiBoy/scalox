package scalox

import org.lwjgl._
import org.lwjgl.glfw._
import org.lwjgl.opengl._
import org.lwjgl.system._

import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.system.MemoryUtil._

abstract class Window(title: String, width: Int, height: Int):
  protected var window: Long = 0

  def onStart(): Unit = ()
  def onUpdate(deltaTime: Double): Unit = println("parent update")
  def onRender(deltaTime: Double): Unit = println("parent render")

  def run(): Unit =
    println(s"Current LWJGL-version: ${Version.getVersion()}")

    init()

    var deltaTime: Double = 0.0
    var r = 0.0

    onStart()
    
    // Game loop
    while (!glfwWindowShouldClose(window)) do
      val t0: Double = glfwGetTime()
      
      r += deltaTime
      glClearColor((Math.cos(r).toFloat + 1) / 2,
      (Math.cos(r - Math.PI / 4).toFloat + 1) / 2,
      (Math.sin(r).toFloat + 1),
      0.0f)
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
      
      // Update and render functions
      onUpdate(deltaTime)
      onRender(deltaTime)

      // Swap buffers and handle events
      glfwSwapBuffers(window)
      glfwPollEvents()

      deltaTime = glfwGetTime() - t0

    // OnDestroy
    glfwFreeCallbacks(window)
    glfwDestroyWindow(window)

    glfwTerminate()
    glfwSetErrorCallback(null).free() // äckligt men det funkar :)
  
  def init(): Unit =
    GLFWErrorCallback.createPrint(System.err).set() 
    if (!glfwInit())
      throw new IllegalStateException("Unable to initialize GLFW!")

    // Konfigurera och skapa fönstret
    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
    window = glfwCreateWindow(width, height, title, NULL, NULL)

    if (window == NULL)
      throw new RuntimeException("Failed to create the GLFW window!")

    var keyboardCallback: KeyboardHandler = new KeyboardHandler()
    glfwSetKeyCallback(window, keyboardCallback) // keyboardCallback blir kallad för alla keyboard events

    var vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor()) // skärmens upplösning

    glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2) // centrera fönstret
    glfwMakeContextCurrent(window) // säger till OpenGL att rendera i just detta fönstret
    glfwSwapInterval(1) // aktiverar vertikalsync (v-sync)

    glfwShowWindow(window) // visa fönstret
    GL.createCapabilities()

class KeyboardHandler extends GLFWKeyCallback:
  def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int): Unit =
    if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
      glfwSetWindowShouldClose(window, true)
