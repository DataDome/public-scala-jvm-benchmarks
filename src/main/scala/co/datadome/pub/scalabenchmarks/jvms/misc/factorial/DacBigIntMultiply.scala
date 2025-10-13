package co.datadome.pub.scalabenchmarks.jvms.misc.factorial

import co.datadome.pub.scalabenchmarks.jvms.utils.DivideAndConquer

import scala.math.BigInt

object DacBigIntMultiply {

  val dac = new DivideAndConquer(100)

  def factorial(n: Int): BigInt = dac.window(1, n + 1)(compute)(_ * _)

  private def compute(start: Int, end: Int): BigInt = {
    var result = BigInt(start)
    var i = start + 1
    while (i < end) {
      result = result * i
      i += 1
    }
    result
  }
}
