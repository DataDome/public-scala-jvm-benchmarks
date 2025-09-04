package co.datadome.pub.scalabenchmarks.jvms.api.string

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class StringRegexMatcherBenchmarkSpec extends AnyFunSuite with Matchers {

  test("benchmark setup initializes state and words are loaded") {
    val bench = new StringRegexMatcherBenchmark()
    bench.regexString = "^[^aeuio]*[aeuio](.*)[aeuio][^aeuio]*$"
    bench.setup()
    // Call at least one method to ensure no exceptions on simple path
    bench.java_regex_match()
  }

  test("Scala regex and Java regex match results should be consistent for the expected pattern") {
    val bench = new StringRegexMatcherBenchmark()
    // Use a pattern that can never match any (finite) string
    bench.regexString = "^[^aeuio]*[aeuio](.*)[aeuio][^aeuio]*$"
    bench.setup()

    val scalaRes = bench.regex_match()
    val javaRes = bench.java_regex_match()

    scalaRes shouldBe true
    javaRes shouldBe true
  }

  test("Scala regex and Java regex match results should be consistent for a never-matching pattern") {
    val bench = new StringRegexMatcherBenchmark()
    // Use a pattern that can never match any (finite) string
    bench.regexString = "a^"
    bench.setup()

    val scalaRes = bench.regex_match()
    val javaRes = bench.java_regex_match()

    scalaRes shouldBe false
    javaRes shouldBe false
  }

  test("Scala capture returns non-negative count and Java capture behavior") {
    val bench = new StringRegexMatcherBenchmark()
    bench.regexString = "^[^aeuio]*[aeuio](.*)[aeuio][^aeuio]*$"
    bench.setup()

    val scalaCount = bench.regex_capture()
    val javaCount = bench.java_regex_capture()
    scalaCount should be >= 0
    javaCount should be >= 0
    scalaCount shouldBe javaCount
  }
}
