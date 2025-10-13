package co.datadome.pub.scalabenchmarks.jvms.utils

final class DivideAndConquer(val threshold: Int) {

  def window[A](start: Int, end: Int)(compute: (Int, Int) => A)(reduce: (A, A) => A): A = {
    val length = end - start
    if (length < threshold) compute(start, end)
    else {
      val middle = start + length / 2
      val left = window(start, middle)(compute)(reduce)
      val right = window(middle, end)(compute)(reduce)
      reduce(left, right)
    }
  }

}
