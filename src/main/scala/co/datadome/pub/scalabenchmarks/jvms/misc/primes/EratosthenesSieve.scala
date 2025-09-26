package co.datadome.pub.scalabenchmarks.jvms.misc.primes

import co.datadome.pub.scalabenchmarks.jvms.utils.*

object EratosthenesSieve {

  def primes(number: Int): Int = {
    val primes = Array.fill(number)(true)
    val numberRoot = Math.sqrt(number).toInt + 1
    fastLoop(2, numberRoot) { i =>
      if (primes(i)) {
        fastLoop(i * i, _ < number, _ + i) { j =>
          primes(j) = false
        }
      }
    }

    primes.fastCount(identity) - 2 // removing 0 and 1
  }
}
