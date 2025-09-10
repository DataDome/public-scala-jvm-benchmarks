package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import zio._


object ParallelFactorial {

  def factorial(n: Int): ZIO[Any, Nothing, BigInt] = factorialWindow(1, n)

  private val Threshold: Int = 100

  private def factorialWindow(start: Int, end: Int): ZIO[Any, Nothing, BigInt] = {
    val length = end - start + 1
    if (length < Threshold) compute(start, end)
    else {
      val middle = start + length / 2
      val left = factorialWindow(start, middle - 1)
      val right = factorialWindow(middle, end)
      (left zipPar right).map { (l, r) => l * r }
    }
  }

  private def compute(start: Int, end: Int): ZIO[Any, Nothing, BigInt] = ZIO.succeed {
    var result = BigInt(start)
    var i = start + 1
    while (i <= end) {
      result = result * i
      i += 1
    }
    result
  }

}
