package co.datadome.pub.scalabenchmarks.jvms.libs.cats

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class CatsTypesBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int = 1000)(f: CatsTypesBenchmark => A): A = {
    val bench = new CatsTypesBenchmark()
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench() { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.eitherT()
    }
  }

  "eitherT" in {
    withBench() { bench =>
      bench.eitherT().collect { case Ternary.One(value) => value }.sum should be > 0
    }
  }


}
