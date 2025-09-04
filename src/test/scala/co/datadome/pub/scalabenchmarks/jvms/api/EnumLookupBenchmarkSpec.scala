package co.datadome.pub.scalabenchmarks.jvms.api

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EnumLookupBenchmarkSpec extends AnyFunSuite with Matchers {

  private def withBench[A](f: EnumLookupBenchmark => A): A = {
    val bench = new EnumLookupBenchmark()
    bench.setup()
    f(bench)
    // no tear-down
  }

  test("setup works") {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.enum_values()
    }
  }

  test("enum_values returns the expected value") {
    withBench { bench =>
      val res = bench.enum_values()
      res shouldBe EnumLookupBenchmark.Car.Koenigsegg
    }
  }

  test("cached_enum_values returns the expected value") {
    withBench { bench =>
      val res = bench.cached_enum_values()
      res shouldBe EnumLookupBenchmark.Car.Koenigsegg
    }
  }
}
