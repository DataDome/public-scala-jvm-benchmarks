package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import zio._


object ParallelFactorial {

  val dac = new DivideAndConquer(100)

  def factorial(n: Int): ZIO[Any, Nothing, BigInt] = dac.window(1, n + 1)(compute)(_ * _)

  private def compute(start: Int, end: Int): ZIO[Any, Nothing, BigInt] = ZIO.succeed {
    var result = BigInt(start)
    var i = start + 1
    while (i < end) {
      result = result * i
      i += 1
    }
    result
  }

}
