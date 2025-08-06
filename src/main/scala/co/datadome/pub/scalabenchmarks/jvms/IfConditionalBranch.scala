package co.datadome.pub.scalabenchmarks.jvms

import scala.util.Random

/*
 * Tests the conditional branch optimizations within a loop using:
 * - a predictable branch pattern
 * - an unpredictable branch pattern
 * - no branch at all
 */
class IfConditionalBranch(size: Int = 0) {

  private val random = new Random(16384)

  private val array = new Array[Int](size)
  for (i <- 0 until size) {
    // all values are within [0, threshold)
    array(i) = random.nextInt(Threshold)
  }

  final private val Threshold = 4096

  def no_if_branch: Int = {
    var sum = 0
    for (value <- array) {
      sum += value
    }
    sum
  }

  // all values are less than the THRESHOLD, therefore the condition is true and the branch is
  // always taken. This could be equivalent or very close to no_if_branch()
  def predictable_if_branch: Int = {
    var sum = 0
    for (value <- array) {
      if (value < Threshold) sum += value
    }
    sum
  }

  // some values are bigger and some are smaller than THRESHOLD / 2, making this condition
  // unpredictable
  def unpredictable_if_branch: Int = {
    var sum = 0
    for (value <- array) {
      if (value <= (Threshold / 2)) sum += value
    }
    sum
  }
}
