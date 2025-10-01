package co.datadome.pub.scalabenchmarks.jvms.misc.palindrome

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class PalindromeBenchmarkSpec extends TestSuite {

  // Expected number of palindromes in the file "palindrome.list" is known to be 29
  private val ExpectedPalindromeCount = 29

  private def withBench[A](f: PalindromeBenchmark => A): A = {
    val bench = new PalindromeBenchmark
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.iterative()
    }
  }

  "iterative" in {
    withBench { bench =>
      bench.iterative() should be(ExpectedPalindromeCount)
    }
  }

  "functional" in {
    withBench { bench =>
      bench.functional() should be(ExpectedPalindromeCount)
    }
  }

  "recursive" in {
    withBench { bench =>
      bench.recursive() should be(ExpectedPalindromeCount)
    }
  }

}
