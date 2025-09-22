package co.datadome.pub.scalabenchmarks.jvms.misc.primes

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

import scala.collection.mutable

class PrimesBenchmarkSpec extends TestSuite {

  private def withBench[A](n: Int = 8388608)(f: PrimesBenchmark => A): A = {
    val bench = new PrimesBenchmark
    bench.n = n
    bench.setup()
    f(bench)
  }

  /** Calculate the minimum number of prime numbers expected based on the Prime Number Theorem (PNT). */
  private def getMinPrimes(n: Int): Long = Math.ceil(n / Math.log(n)).toLong

  "setup" in {
    withBench() { bench =>
      bench.setup()
      // Call at least one method to ensure no exceptions on simple path
      bench.eratosthenes_sieve()
    }
  }

  "trial_division_iterative" in {
    withBench(10) { bench =>
      val result = bench.trial_division_iterative()
      result should be(4)
    }

    withBench() { bench =>
      val result = bench.trial_division_iterative()
      result should be >= getMinPrimes(bench.n)
      result should be(564163)
    }
  }

  "trial_division_functional" in {
    withBench(10) { bench =>
      val result = bench.trial_division_functional()
      result should be(4)
    }

    withBench() { bench =>
      val result = bench.trial_division_functional()
      result should be >= getMinPrimes(bench.n)
      result should be(564163)
    }
  }

  "eratosthenes_sieve" in {
    withBench(10) { bench =>
      val result = bench.eratosthenes_sieve()
      result should be(4)
    }

    withBench() { bench =>
      val result = bench.eratosthenes_sieve()
      result should be >= getMinPrimes(bench.n)
      result should be(564163)
    }
  }

  "miller_rabin" in {
    withBench(10) { bench =>
      val result = bench.miller_rabin()
      result should be(4)
    }

    withBench() { bench =>
      val result = bench.miller_rabin()
      result should be >= getMinPrimes(bench.n)
      result should be(564163)
    }
  }

  "miller_rabin getPower" in {
    MillerRabin.powerModulo(2, 10, 2) should be(0)
    MillerRabin.powerModulo(2, 10, 3) should be(1)
    MillerRabin.powerModulo(2, 10, 5) should be(4)
    MillerRabin.powerModulo(5, 7, 2) should be(1)
    MillerRabin.powerModulo(5, 7, 3) should be(2)
    MillerRabin.powerModulo(5, 7, 5) should be(0)
  }
}
