package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.effect.IO


object ParallelFactorial {

  val dac = new DivideAndConquer(100)

  def factorial(n: Int): IO[BigInt] = dac.window(1, n + 1)(compute)(_ * _)

  private def compute(start: Int, end: Int): IO[BigInt] = IO {
    var result = BigInt(start)
    var i = start + 1
    while (i <= end) {
      result = result * i
      i += 1
    }
    result
  }


}
