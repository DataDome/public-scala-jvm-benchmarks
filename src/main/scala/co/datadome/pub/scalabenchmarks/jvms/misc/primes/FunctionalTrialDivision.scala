package co.datadome.pub.scalabenchmarks.jvms.misc.primes


object FunctionalTrialDivision {
  def primes(n: Int): Long = (2 until n).count(isPrime)

  private def isPrime(n: Int) = {
    n > 1 && !(2 until Math.sqrt(n).toInt + 1).exists(n % _ == 0)
  }
}
