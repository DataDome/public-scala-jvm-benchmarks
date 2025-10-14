package co.datadome.pub.scalabenchmarks.jvms.misc.wordfrequency

import cats.implicits.catsSyntaxSemigroup
import co.datadome.pub.scalabenchmarks.jvms.utils.DivideAndConquer

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex

object Dac {

  private val Threshold = 100

  private val Pattern: Regex = """[\W]+""".r

  val dac = new DivideAndConquer(Threshold)

  def frequencies(fileName: String): Map[String, Int] = {
    val file: BufferedSource = Source.fromFile(fileName)
    try {
      val lines = file.getLines().toSeq
      dac.window(0, lines.length)(compute(lines))(_ combine _)
    } finally file.close()
  }

  private def compute(lines: Seq[String])(start: Int, end: Int): Map[String, Int] = {
    val words = lines.slice(start, end).flatMap(Pattern.split).filterNot(_.isBlank)
    words.groupMapReduce(identity)(_ => 1)(_ + _)
  }
}
