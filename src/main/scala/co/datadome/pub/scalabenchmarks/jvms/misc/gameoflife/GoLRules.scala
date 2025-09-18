package co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife

import co.datadome.pub.scalabenchmarks.jvms.utils.fastLoop

object GoLRules {

  def evolveCell(grid: Array[Array[Byte]], rows: Int, cols: Int, row: Int, col: Int): Byte = {
    var neighbors = 0
    fastLoop(row - 1, row + 2) { r =>
      fastLoop(col - 1, col + 2) { c =>
        if (r >= 0 && r < rows && c >= 0 && c < cols) { // still in the grid
          if (!(r == row && c == col)) { // not the same cell
            if (grid(r)(c) == 1) { // neighbor is alive
              neighbors += 1
            }
          }
        }
      }
    }

    if (grid(row)(col) == 1) {
      if (neighbors == 2 || neighbors == 3) 1
      else 0
    } else {
      if (neighbors == 3) 1
      else 0
    }
  }
  
}
