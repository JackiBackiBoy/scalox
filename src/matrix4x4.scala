package scalox

class Matrix4x4(val data: Array[Array[Float]]):
  import Matrix4x4._

  // Converts row-major to column major
  def this(r1c1: Float, r1c2: Float, r1c3: Float, r1c4: Float,
           r2c1: Float, r2c2: Float, r2c3: Float, r2c4: Float,
           r3c1: Float, r3c2: Float, r3c3: Float, r3c4: Float,
           r4c1: Float, r4c2: Float, r4c3: Float, r4c4: Float) =
    this(Array(Array(r1c1, r2c1, r3c1, r4c1),
               Array(r1c2, r2c2, r3c2, r4c2),
               Array(r1c3, r2c3, r3c3, r4c3),
               Array(r1c4, r2c4, r3c4, r4c4)))
  
  def *(other: Float) =
    var newData = data.toArray.map(_.map(_ * other))
    new Matrix4x4(newData)

  def *(other: Matrix4x4) =
    var newData: Array[Array[Float]] = identity.data

    for (r <- 0 until 4)
      for (c <- 0 until 4)
        var newElement: Float = 0.0f

        for (k <- 0 until 4)
          newElement += this.data(k)(r) * other.data(c)(k)

        newData(c)(r) = newElement

    new Matrix4x4(newData)

  def flatData: Array[Float] = data.flatten

object Matrix4x4:
  val identity: Matrix4x4 = Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f,
                                      0.0f, 1.0f, 0.0f, 0.0f,
                                      0.0f, 0.0f, 1.0f, 0.0f,
                                      0.0f, 0.0f, 0.0f, 1.0f)
