package co.datadome.pub.scalabenchmarks.jvms.misc.factorial

import scala.math.BigInt

object IterativeBigIntMultiply {

  val One = BigInt(1)

  def factorial(n: Int): BigInt = {
    var result = One
    var i = 2
    while (i <= n) {
      result = result * i
      i += 1
    }
    result
  }

}
