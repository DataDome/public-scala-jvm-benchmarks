package co.datadome.pub.scalabenchmarks.jvms.api

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumLookupBenchmarkSpec extends AnyFunSuite with Matchers {

  test("Setup works") {
    val bench = new EnumLookupBenchmark()
    bench.setup()
    // Call at least one method to ensure no exceptions on simple path
    bench.enum_values()
  }

  test("Call to enum_values returns the expected value") {
    val bench = new EnumLookupBenchmark()
    bench.setup()

    val res = bench.enum_values()
    res shouldBe EnumLookupBenchmark.Car.Koenigsegg
  }

  test("Call to cached_enum_values returns the expected value") {
    val bench = new EnumLookupBenchmark()
    bench.setup()

    val res = bench.cached_enum_values()
    res shouldBe EnumLookupBenchmark.Car.Koenigsegg
  }
}
