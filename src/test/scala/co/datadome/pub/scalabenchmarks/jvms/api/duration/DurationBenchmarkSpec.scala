package co.datadome.pub.scalabenchmarks.jvms.api.duration

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration.*

class DurationBenchmarkSpec extends AnyFunSuite with Matchers {

  private def withBench[A](f: DurationBenchmark => A): A = {
    val bench = new DurationBenchmark()
    f(bench)
  }

  test("setup works") {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.add_finite()
    }
  }

  test("instantiation returns the expected result") {
    withBench { bench =>
      val d = bench.instantiation()
      d shouldBe 10.minutes
    }
  }

  test("add_finite returns 70 minutes (10 minutes + 1 hour)") {
    withBench { bench =>
      val d = bench.add_finite()
      d shouldBe (10.minutes + 1.hour)
    }
  }

  test("add_infinite returns Duration.Inf") {
    withBench { bench =>
      val d = bench.add_infinite()
      d shouldBe Duration.Inf
    }
  }

  test("mult_finite returns 30 minutes") {
    withBench { bench =>
      val d = bench.mult_finite()
      d shouldBe (10.minutes * 3)
    }
  }

  test("mult_infinite returns Duration.Inf") {
    withBench { bench =>
      val d = bench.mult_infinite()
      d shouldBe Duration.Inf
    }
  }
}
