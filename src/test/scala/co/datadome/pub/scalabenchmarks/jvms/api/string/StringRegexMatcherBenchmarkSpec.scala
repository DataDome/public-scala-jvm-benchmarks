package co.datadome.pub.scalabenchmarks.jvms.api.string

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class StringRegexMatcherBenchmarkSpec extends AnyFunSuite with Matchers {

  private def withBench[A](regexString: String = "^[^aeuio]*[aeuio](.*)[aeuio][^aeuio]*$")(f: StringRegexMatcherBenchmark => A): A = {
    val bench = new StringRegexMatcherBenchmark()
    bench.regexString = regexString
    bench.setup()
    f(bench)
    // no tear-down
  }

  test("setup works") {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.java_regex_match()
    }
  }

  test("regex_match and java_regex_match return the expected results") {
    withBench() { bench =>
      val scalaRes = bench.regex_match()
      val javaRes = bench.java_regex_match()

      scalaRes shouldBe true
      javaRes shouldBe true
    }
  }

  test("regex_match and java_regex_match return the expected results for a never-matching pattern") {
    // Use a pattern that can never match any (finite) string
    withBench("a^") { bench =>
      val scalaRes = bench.regex_match()
      val javaRes = bench.java_regex_match()

      scalaRes shouldBe false
      javaRes shouldBe false
    }
  }

  test("regex_capture and java_regex_capture return the expected results") {
    withBench() { bench =>
      val scalaCount = bench.regex_capture()
      val javaCount = bench.java_regex_capture()
      scalaCount should be >= 0
      javaCount should be >= 0
      scalaCount shouldBe javaCount
    }
  }
}
