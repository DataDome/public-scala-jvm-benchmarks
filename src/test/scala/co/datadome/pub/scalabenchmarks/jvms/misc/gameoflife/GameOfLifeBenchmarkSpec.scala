package co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife

import co.datadome.pub.scalabenchmarks.jvms.TestSuite
import co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife.GameOfLifeBenchmark

class GameOfLifeBenchmarkSpec extends TestSuite {

  private def withBench[A](f: GameOfLifeBenchmark => A): A = {
    val bench = new GameOfLifeBenchmark
    bench.setup()
    f(bench)
  }

  "setup" in {
    withBench { bench =>
      // println(bench.grid.map(_.map(n => if (n == 0) "·" else "X").mkString(" ")).mkString("\n"))
      // Call at least one method to ensure no exceptions on simple path
      bench.array_copy()
    }
  }

  "GoLRules" - {
    "simple line" in {
      val grid = Array(
        Array[Byte](0, 1, 0),
        Array[Byte](0, 1, 0),
        Array[Byte](0, 1, 0)
      )
      GoLRules.evolveCell(grid, 3, 3, 0, 1) should be(0.toByte)
      GoLRules.evolveCell(grid, 3, 3, 1, 1) should be(1.toByte)
      GoLRules.evolveCell(grid, 3, 3, 1, 0) should be(1.toByte)
    }
  }

  "iterative and functional should return the same result" in {
    withBench { bench =>
      /* Order is important: the in-place one will modify the grid in the benchmark */
      val grid1 = bench.array_copy()
      val grid2 = bench.in_place()
      (grid1 zip grid2).foreach { (row1, row2) =>
        row1.toList should be(row2.toList)
      }
      // println(grid1.map(_.map(n => if (n == 0) "·" else "X").mkString(" ")).mkString("\n"))
    }
  }

}
