package co.datadome.pub.scalabenchmarks.jvms.misc.popvariance

import co.datadome.pub.scalabenchmarks.jvms.utils.*

object Iterative {
  def variance(ages: Array[Double]): Double = {
    val average = ages.sum / ages.length

    var variance: Double = 0.0
    ages.fastForeach { a =>
      val d = a - average
      variance += d * d
    }
    variance / ages.length
  }
}
