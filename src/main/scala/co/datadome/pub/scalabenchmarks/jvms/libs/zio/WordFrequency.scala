package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import cats.implicits.catsSyntaxSemigroup
import zio.*

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex

object WordFrequency {

  private val Pattern: Regex = """[\W]+""".r

  val dac = new DivideAndConquer(100)

  def wordFrequency(fileName: String): ZIO[Any, Throwable, Map[String, Int]] = {
    val openFile: IO[Throwable, BufferedSource] = ZIO.attempt(Source.fromFile(fileName))
    val closeFile: BufferedSource => URIO[Any, Unit] = (x: BufferedSource) => ZIO.attempt(x.close()).orDie
    val readFile = (x: BufferedSource) => ZIO.attempt(x.getLines().toSeq)
    val getLines: ZIO[Any, Throwable, Seq[String]] = ZIO.acquireReleaseWith(openFile)(closeFile)(readFile)

    getLines.flatMap { lines =>
      dac.window(0, lines.length)(compute(lines))(_ combine _)
    }
  }

  private def compute(lines: Seq[String])(start: Int, end: Int) = ZIO.succeed {
    val words = lines.slice(start, end).flatMap(Pattern.split).filterNot(_.isBlank)
    words.groupMapReduce(identity)(_ => 1)(_ + _)
  }

}
