package co.datadome.pub.scalabenchmarks.jvms.misc.nqueens

import co.datadome.pub.scalabenchmarks.jvms.TestSuite

class NQueensBenchmarkSpec extends TestSuite {

  private def withBench[A](n: Int)(f: NQueensBenchmark => A): A = {
    val bench = new NQueensBenchmark()
    bench.n = n
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench(4) { bench =>
      // Call at least one method to ensure no exceptions on simple path
      bench.simulated_annealing()
    }
  }

  def checkSolution(board: Array[Array[Boolean]]): Unit = {
    board.foreach { line =>
      val str = line.map(b => if (b) "Q" else ".").mkString(" ")
      println(str)
    }
    board.foreach { line =>
      line.count(identity) should be(1)
    }
    board.transpose.foreach { column =>
      column.count(identity) should be(1)
    }
  }

  "backtracking" in {
    // For higher dimensions than 8, we don't run long enough to find a correct solution by simulated annealing
    withBench(24) { bench =>
      val result = bench.backtracking()
      checkSolution(result)
    }
  }

  "simulated_annealing" in {
    // For higher dimensions than 8, we don't run long enough to find a correct solution by simulated annealing
    withBench(8) { bench =>
      val result = bench.simulated_annealing()
      checkSolution(result)
    }
  }

}
