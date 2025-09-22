package co.datadome.pub.scalabenchmarks.jvms.misc.wordfrequency

import co.datadome.pub.scalabenchmarks.jvms.utils.*

import java.nio.charset.StandardCharsets.ISO_8859_1
import scala.collection.mutable
import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex

object Iterative {
  private val Pattern: Regex = """[\W]+""".r

  def frequencies(fileName: String): mutable.Map[String, Int] = {
    val wordFrequencies: mutable.Map[String, Int] = mutable.Map[String, Int]()
    val source: BufferedSource = Source.fromFile(fileName, ISO_8859_1.name())
    try {
      val linesIterator = source.getLines()
      linesIterator.fastForeach { line =>
        val words = Pattern.split(line)
        words.fastForeach { word =>
          if (!word.isBlank) {
            val frequency = wordFrequencies.getOrElse(word, 0)
            wordFrequencies(word) = frequency + 1
          }
        }
      }
    } finally source.close()

    wordFrequencies
  }
}