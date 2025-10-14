package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.*
import cats.effect.*
import cats.implicits.*
import fs2.Stream
import fs2.io.file.{Files, Path}

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex


object WordFrequency {

  private val Threshold = 100

  private val Pattern: Regex = """[\W]+""".r

  val dac = new DivideAndConquer(Threshold)

  def wordFrequency(fileName: String): IO[Map[String, Int]] = {
    val openFile: IO[BufferedSource] = IO(Source.fromFile(fileName))
    val closeFile: BufferedSource => IO[Unit] = (x: BufferedSource) => IO(x.close())
    val fileResource: Resource[IO, BufferedSource] = Resource.make(openFile)(closeFile)

    val readFile: BufferedSource => IO[Seq[String]] = (x: BufferedSource) => IO(x.getLines().toSeq)
    val getLines: IO[Seq[String]] = fileResource.use(readFile)

    getLines.flatMap { lines =>
      dac.window(0, lines.length)(compute(lines))(_ combine _)
    }
  }

  private def compute(lines: Seq[String])(start: Int, end: Int): IO[Map[String, Int]] = IO {
    val words = lines.slice(start, end).flatMap(Pattern.split).filterNot(_.isBlank)
    words.groupMapReduce(identity)(_ => 1)(_ + _)
  }

  def wordFrequencyStreamingFs2(fileName: String): Stream[IO, Map[String, Int]] = {
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
