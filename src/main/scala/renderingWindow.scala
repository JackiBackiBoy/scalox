package scala3d

class RenderingWindow extends Window("Scala 3D | Jack Henrikson", 1240, 720):
  override def onUpdate(deltaTime: Double): Unit = println("child update")
  
  override def onRender(deltaTime: Double): Unit = println("child render")