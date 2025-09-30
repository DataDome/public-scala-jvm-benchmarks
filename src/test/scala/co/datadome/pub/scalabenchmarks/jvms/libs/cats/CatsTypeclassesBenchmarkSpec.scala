package co.datadome.pub.scalabenchmarks.jvms.libs.cats

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import co.datadome.pub.scalabenchmarks.jvms.libs.cats.CatsTypeclassesBenchmark

class CatsTypeclassesBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 1000)(f: CatsTypeclassesBenchmark => A): A = {
    val bench = new CatsTypeclassesBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.monoid()
    }
  }

  "monoid" in {
    withBench() { bench =>
      bench.monoid().collect { case Ternary.One(value) => value }.sum should be > 0
    }
  }

  "functor" in {
    withBench() { bench =>
      bench.functor().collect { case Ternary.One(value) => value }.sum shouldBe
        42 * bench.ternaries.collect { case Ternary.One(value) => value }.sum
    }
  }

  "monad" in {
    withBench() { bench =>
      bench.monad().collect { case Ternary.One(value) => value }.sum shouldBe
        42 * bench.ternaries.collect { case Ternary.One(value) if value % 3 == 1 => value }.sum
    }
  }

  "forComprehension" in {
    withBench() { bench =>
      bench.forComprehension().collect { case Ternary.One(value) => value }.sum should be > 0
    }
  }

}
