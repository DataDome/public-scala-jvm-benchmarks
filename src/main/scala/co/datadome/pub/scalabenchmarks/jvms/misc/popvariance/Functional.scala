package co.datadome.pub.scalabenchmarks.jvms.misc.popvariance

object Functional {
  def variance(ages: Array[Double]): Double = {
    val average = ages.sum / ages.length
    val varianceSum: Double = ages.map(a => (a - average) * (a - average)).sum
    varianceSum / ages.length
  }
}
