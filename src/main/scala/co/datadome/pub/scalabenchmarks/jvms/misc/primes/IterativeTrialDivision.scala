package co.datadome.pub.scalabenchmarks.jvms.misc.primes

import co.datadome.pub.scalabenchmarks.jvms.utils.*


object IterativeTrialDivision {

  val Epsilon = 0.1

  def primes(n: Int): Long = {
    var total = 0
    fastLoop(2, n) { i =>
      if (isPrime(i)) {
        total += 1
      }
    }
    total
  }

  private def isPrime(n: Int): Boolean = if (n <= 1) false else {
    var stillOk = true
    val nRoot = Math.sqrt(n) + Epsilon
    fastLoop(2, i => stillOk && i < nRoot, _ + 1) { i =>
      stillOk = n % i != 0
    }
    stillOk
  }

}
