package scalox

class Matrix4x4(data: Vector[Vector[Float]]):
  // Converts row-major to column major
  def this(r1c1: Float, r1c2: Float, r1c3: Float, r1c4: Float,
           r2c1: Float, r2c2: Float, r2c3: Float, r2c4: Float,
           r3c1: Float, r3c2: Float, r3c3: Float, r3c4: Float,
           r4c1: Float, r4c2: Float, r4c3: Float, r4c4: Float) =
    this(Vector(Vector(r1c1, r2c1, r3c1, r4c1),
                Vector(r1c2, r2c2, r3c2, r4c2),
                Vector(r1c3, r2c3, r3c3, r4c3),
                Vector(r1c4, r2c4, r3c4, r4c4)))

  def flatData: Vector[Float] = data.flatten

object Matrix4x4:
  val identity: Matrix4x4 = Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                      0.0f, 1.0f, 0.0f, 0.0f,
                                      0.0f, 0.0f, 1.0f, 0.0f,
                                      0.0f, 0.0f, 0.0f, 1.0f)
