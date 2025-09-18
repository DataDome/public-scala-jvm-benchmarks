package co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife

import co.datadome.pub.scalabenchmarks.jvms.utils.fastLoop

object InPlaceGoL {

  def evolve(grid: Array[Array[Byte]], rows: Int, cols: Int, generations: Int): Unit = {
    val gridB = Array.fill[Byte](rows, cols)(0)
    fastLoop(0, generations) { gen =>
      evolveGeneration(grid, gridB, AtoB = (gen % 2 == 0), rows, cols)
    }
    if (generations % 2 != 0) {
      fastLoop(0, rows) { row =>
        fastLoop(0, cols) { col =>
          grid(row)(col) = gridB(row)(col)
        }
      }
    }
  }

  private def evolveGeneration(gridA: Array[Array[Byte]], gridB: Array[Array[Byte]], AtoB: Boolean, rows: Int, cols: Int): Unit = {
    val sourceGrid = if (AtoB) gridA else gridB
    val targetGrid = if (AtoB) gridB else gridA

    fastLoop(0, rows) { row =>
      fastLoop(0, cols) { col =>
        targetGrid(row)(col) = GoLRules.evolveCell(sourceGrid, rows, cols, row, col)
      }
    }
  }


}
