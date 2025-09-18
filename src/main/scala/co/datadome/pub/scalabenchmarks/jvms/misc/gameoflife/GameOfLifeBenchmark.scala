package co.datadome.pub.scalabenchmarks.jvms.misc.gameoflife

import org.openjdk.jmh.annotations.*

import java.util.concurrent.TimeUnit
import scala.compiletime.uninitialized
import scala.util.Random


/**
 * Conway's Game of Life, often referred to simply as the Game of Life, is a cellular automaton devised by mathematician John Conway in 1970.
 * It is a mathematical "zero-player" game, meaning that its evolution is determined by its initial state, with no further input required.
 *
 * The Game of Life is played on a 2D grid of cells, where each cell can be in one of two states: alive or dead (0 or 1).
 * The game progresses through generations, with the state of each cell in a generation being determined by the state of its neighboring cells
 * in the previous generation according to a set of rules. These rules are based on the concept of birth, death, and survival:
 * - Birth: A dead cell with exactly three live neighbors becomes a live.
 * - Death: A live cell with fewer than two live neighbors (underpopulation) or more than three live neighbors (overpopulation) becomes a dead cell.
 * - Survival: A live cell with two or three live neighbors remains alive.
 *
 * Despite its simple rules, the Game of Life can produce complex and intricate patterns, including gliders (moving structures),
 * oscillators (repeating patterns), and even structures that can act as logic gates and memory cells.
 *
 * The benchmark involves several alternative strategies:
 * - Game of Life with Functional Programming
 * - Game of Life with Imperative Style
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Fork(value = 5)
@State(Scope.Benchmark)
class GameOfLifeBenchmark {
  private val random = new Random(16384)

  private val Rows = 24
  private val Cols = 24
  private val Generations = 2048

  var grid: Array[Array[Byte]] = uninitialized

  @Setup def setup(): Unit = {
    grid = Array.fill(Rows, Cols)(if (random.nextBoolean) 1 else 0)
  }

  @Benchmark
  def array_copy(): Array[Array[Byte]] = {
    ArrayCopyGoL.evolve(grid, Rows, Cols, Generations)
  }

  @Benchmark
  def in_place(): Array[Array[Byte]] = {
    InPlaceGoL.evolve(grid, Rows, Cols, Generations)
    grid
  }

}
