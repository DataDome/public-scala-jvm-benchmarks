package co.datadome.pub.scalabenchmarks.jvms.misc.dijkstra

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife.GameOfLifeBenchmark

class DijkstraBenchmarkSpec extends TestSuite {

  private def withBench[A](size: Int)(f: DijkstraBenchmark => A): A = {
    val bench = new DijkstraBenchmark
    bench.size = size
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(64) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.adjacency_matrix()
    }
  }

  "adjacency_matrix and adjacency_lists should return the same result" in {
    withBench(64) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      val a = bench.adjacency_matrix()
      val b = bench.adjacency_lists()
      a.toList should be(b.toList)
    }
  }


}
