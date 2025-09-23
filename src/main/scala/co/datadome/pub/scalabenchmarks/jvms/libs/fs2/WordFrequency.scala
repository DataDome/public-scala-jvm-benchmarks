package co.datadome.pub.scalabenchmarks.jvms.libs.fs2

import cats.effect.{IO, IOApp, Resource}
import fs2.io.file.{Files, Path}
import fs2.text
import zio.*
import zio.stream.*
import fs2.{Stream, text}
import fs2.io.file.{Files, Path}

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex


object WordFrequency {

  private val Pattern: Regex = """[\W]+""".r

  def wordFrequency(fileName: String): Stream[IO, Map[String, Int]] = {
    val lines: Stream[IO, String] = Files[IO].readUtf8Lines(Path(fileName))
    val words: Stream[IO, String] =
      lines
        .flatMap(line => Stream.emits(Pattern.split(line)))
        .filterNot(_.isBlank)

    words
      .fold(Map[String, Int]()) { (frequencies, word) =>
        val f = frequencies.getOrElse(word, 0)
        frequencies + (word -> (f + 1))
      }
  }

}
