package co.datadome.pub.scalabenchmarks.jvms.misc.nqueens

object NQueensBacktracking {

  def placeNQueens(n: Int): Array[Array[Boolean]] = {
    val board: Array[Array[Boolean]] = Array.fill[Boolean](n, n)(false)

    backtrack(board, n, 0)
    board
  }

  def backtrack(board: Array[Array[Boolean]], n: Int, row: Int): Boolean =
    if (row == n) {
      true
    } else {
      var col = 0
      while (col < n) {
        if (isValidPlacement(board, row, col, n)) {
          board(row)(col) = true
          if (backtrack(board, n, row + 1)) {
            return true
          }
          board(row)(col) = false
        }
        col += 1
      }
      false
    }

  private def isValidPlacement(board: Array[Array[Boolean]], row: Int, col: Int, n: Int): Boolean = {
    // since we're going row by row:
    // - No need to check if the row is already occupied
    // - We only need to check queens in the previous rows
    var i = 0
    while (i < row) {
      if (board(i)(col)) {
        return false
      }
      val leftDiagonal = col - (row - i)
      val rightDiagonal = col + (row - i)
      if (leftDiagonal >= 0 && board(i)(leftDiagonal)) {
        return false
      }
      if (rightDiagonal < n && board(i)(rightDiagonal)) {
        return false
      }
      i += 1
    }

    true
  }
}
