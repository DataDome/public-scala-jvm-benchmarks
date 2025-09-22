package co.datadome.pub.scalabenchmarks.jvms.misc.primes

import scala.util.Random
import co.datadome.pub.scalabenchmarks.jvms.utils.*

object MillerRabin {

  // Number of iterations the test does to determine whether a given number is likely prime or not.
  // Note: Number of iterations is a trade-off between computational efficiency and accuracy:
  // - a larger number reduces the chances of false positives,
  // - but it also increases the time it takes to perform the test.

  private val Iterations = 5

  def primes(n: Int)(using Random): Long = {
    var numberOfPrimes = 0
    for (num <- 2 to n) {
      if (isPrime(num, Iterations)) numberOfPrimes += 1
    }
    numberOfPrimes
  }

  private def isPrime(n: Long, k: Int)(using random: Random): Boolean =
    if (n <= 1 || n == 4) false
    else if (n <= 3) true
    else {
      var d = n - 1
      while (d % 2 == 0) {
        d = d / 2
      }

      fastLoop(0, k) { _ =>
        val a = 2 + random.nextInt((n - 4).toInt)
        var x = powerModulo(a, d, n)
        if (x != 1 && x != n - 1) {
          var found = false
          while (!found && d != n - 1) {
            x = (x * x) % n
            d *= 2
            if (x == 1) return false
            found = x == n - 1
          }
          if (!found) return false
        }
      }
      true
    }

  // Returns (x^y) % p
  def powerModulo(x: Long, y: Long, p: Long): Long = {
    var result = 1L
    var modifiedX = x % p
    var modifiedY = y

    while (modifiedY > 0) {
      if (modifiedY % 2 == 1) {
        result = (result * modifiedX) % p
      }
      modifiedY = modifiedY >> 1 // y = y / 2
      modifiedX = (modifiedX * modifiedX) % p
    }
    result
  }
}
