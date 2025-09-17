package co.datadome.pub.scalabenchmarks.jvms.misc.nqueens

import scala.util.Random

object NQueensSimulatedAnnealing {

  def placeNQueens(n: Int)(using random: Random): Array[Array[Boolean]] = {

    var queenColumns: Array[Int] = Array.fill(n)(random.nextInt(n)) // start with random solution
    var temperature: Double = 100.0
    val coolingRate: Double = 0.003

    while (temperature > 0.01) {
      val nextSolution: Array[Int] = generateNeighbor(queenColumns)
      val currentEnergy: Int = calculateEnergy(queenColumns)
      val nextEnergy: Int = calculateEnergy(nextSolution)

      val acceptanceProbability: Double = calculateAcceptanceProbability(currentEnergy, nextEnergy, temperature)

      if (acceptanceProbability > random.nextDouble()) {
        queenColumns = nextSolution
      }

      temperature *= (1.0 - coolingRate)
    }

    constructBoard(queenColumns)
  }

  private def constructBoard(queenColumns: Array[Int]): Array[Array[Boolean]] = {
    val n = queenColumns.length
    Array.tabulate[Boolean](n, n) { (i, j) =>
      if (queenColumns(i) == j) true else false
    }
  }

  private def generateNeighbor(queenColumns: Array[Int])(using random: Random): Array[Int] = {
    val n = queenColumns.length
    val neighborSolution = queenColumns.clone()

    val row = random.nextInt(n) // Select a random row to move a queen

    // Move the queen to a random new column in its row
    var newColumn = neighborSolution(row)
    while (newColumn == neighborSolution(row)) {
      newColumn = random.nextInt(n)
    }
    neighborSolution(row) = newColumn

    neighborSolution
  }

  private def calculateEnergy(queenColumns: Array[Int]): Int = {
    val n = queenColumns.length
    var energy = 0

    var i = 0
    while (i < n) {
      var j = i + 1
      while (j < n) {
        if (queenColumns(i) == queenColumns(j) || Math.abs(queenColumns(i) - queenColumns(j)) == j - i) {
          energy += 1 // Queens attacking each other
        }
        j += 1
      }
      i += 1
    }

    energy
  }

  private def calculateAcceptanceProbability(currentEnergy: Int, nextEnergy: Int, temperature: Double): Double = {
    if (nextEnergy <= currentEnergy) 1.0 // Always accept better solutions
    else Math.exp((currentEnergy - nextEnergy) / temperature)
  }
}
