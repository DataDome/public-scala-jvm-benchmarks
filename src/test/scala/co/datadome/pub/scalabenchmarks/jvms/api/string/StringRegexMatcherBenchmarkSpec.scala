package co.datadome.pub.scalabenchmarks.jvms.api.string

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class StringRegexMatcherBenchmarkSpec extends TestSuite {

  private def withBench[A](regexString: String = "^[^aeuio]*[aeuio](.*)[aeuio][^aeuio]*$")(f: StringRegexMatcherBenchmark => A): A = {
    val bench = new StringRegexMatcherBenchmark()
    bench.regexString = regexString
    bench.setup()
    f(bench)
  }


  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.java_regex_match()
    }
  }


  "regex_match and java_regex_match" - {
    "with typical pattern" in {
      withBench() { bench =>
        val scalaRes = bench.regex_match()
        val javaRes = bench.java_regex_match()

        scalaRes shouldBe true
        javaRes shouldBe true
      }
    }

    "with a never-matching pattern" in {
      // Use a pattern that can never match any (finite) string
      withBench("a^") { bench =>
        val scalaRes = bench.regex_match()
        val javaRes = bench.java_regex_match()

        scalaRes shouldBe false
        javaRes shouldBe false
      }
    }
  }


  "regex_capture and java_regex_capture" in {
    withBench() { bench =>
      val scalaCount = bench.regex_capture()
      val javaCount = bench.java_regex_capture()
      scalaCount should be >= 0
      javaCount should be >= 0
      scalaCount shouldBe javaCount
    }
  }
}
