package co.datadome.pub.scalabenchmarks.jvms.misc.wordfrequency

import java.nio.charset.StandardCharsets.ISO_8859_1
import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex

object Functional {
  private val Pattern: Regex = """[\W]+""".r

  def frequencies(fileName: String): Map[String, Int] = {
    val source: BufferedSource = Source.fromFile(fileName, ISO_8859_1.name())
    try {
      val lines = source.getLines()
      val words = lines.flatMap(Pattern.split).filterNot(_.isBlank)
      words.toSeq.groupMapReduce(identity)(_ => 1)(_ + _)
    } finally source.close()
  }
}