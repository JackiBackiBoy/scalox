package scala3d

case class Matrix4x4(data: Vector[Vector[Float]]):

  
  def flatData: Vector[Float] = data.flatten
