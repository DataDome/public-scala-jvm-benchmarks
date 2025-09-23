package co.datadome.pub.scalabenchmarks.jvms.libs.catseffect

import cats.*
import cats.implicits.*
import cats.effect.*

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex

object WordFrequency {

  private val Pattern: Regex = """[\W]+""".r

  val dac = new DivideAndConquer(100)

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

}
