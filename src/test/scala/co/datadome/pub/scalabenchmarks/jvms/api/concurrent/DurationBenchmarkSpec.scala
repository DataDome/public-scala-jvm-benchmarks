package co.datadome.pub.scalabenchmarks.jvms.api.concurrent

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

import scala.concurrent.duration.*

class DurationBenchmarkSpec extends TestSuite {

  private def withBench[A](f: DurationBenchmark => A): A = {
    val bench = new DurationBenchmark()
    f(bench)
  }

  "setup works" in {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.add_finite()
    }
  }

  "instantiation" in {
    withBench { bench =>
      val d = bench.instantiation()
      d shouldBe 10.minutes
    }
  }

  "add_finite" in {
    withBench { bench =>
      val d = bench.add_finite()
      d shouldBe (10.minutes + 1.hour)
    }
  }

  "add_infinite" in {
    withBench { bench =>
      val d = bench.add_infinite()
      d shouldBe Duration.Inf
    }
  }

  "mult_finite" in {
    withBench { bench =>
      val d = bench.mult_finite()
      d shouldBe (10.minutes * 3)
    }
  }

  "mult_infinite" in {
    withBench { bench =>
      val d = bench.mult_infinite()
      d shouldBe Duration.Inf
    }
  }
}
