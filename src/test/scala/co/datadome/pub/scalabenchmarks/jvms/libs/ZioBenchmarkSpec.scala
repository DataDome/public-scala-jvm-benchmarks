package co.datadome.pub.scalabenchmarks.jvms.libs

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class ZioBenchmarkSpec extends TestSuite {

  private def withBench[A](f: ZioBenchmark => A): A = {
    val bench = new ZioBenchmark()
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.hello_world()
    }
  }

  "hello_world" in {
    withBench { bench =>
      bench.hello_world()
    }
  }
}
