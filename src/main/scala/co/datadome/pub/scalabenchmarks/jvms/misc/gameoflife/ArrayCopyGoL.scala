package co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife

import co.datadome.pub.scalabenchmarks.jvms.utils.fastLoop

object ArrayCopyGoL {

  def evolve(grid: Array[Array[Byte]], rows: Int, cols: Int, generations: Int): Array[Array[Byte]] = {
    var modifiedGrid = grid
    fastLoop(0, generations) { _ =>
      modifiedGrid = evolveGeneration(modifiedGrid, rows, cols)
    }
    modifiedGrid
  }

  private def evolveGeneration(grid: Array[Array[Byte]], rows: Int, cols: Int) = {
    Array.tabulate[Byte](rows, cols) { (row, col) =>
      GoLRules.evolveCell(grid, rows, cols, row, col)
    }
  }

}
