package co.datadome.pub.scalabenchmarks.jvms.libs.zio

import cats.implicits.catsSyntaxSemigroup
import zio.*
import zio.stream.*

import scala.io.{BufferedSource, Source}
import scala.util.matching.Regex

object WordFrequency {

  private val Parallelism: Int = 100

  private val Pattern: Regex = """[\W]+""".r

  val dac = new DivideAndConquer(Parallelism)

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

  def wordFrequencyStream(fileName: String): ZStream[Any, Throwable, (String, Long)] = {

    val readFile: IO[Throwable, BufferedSource] = ZIO.attempt(Source.fromFile(fileName))
    val closeFile = (x: BufferedSource) => ZIO.attempt(x.close()).orDie

    val words: ZStream[Any, Throwable, String] =
      ZStream.acquireReleaseWith(readFile)(closeFile)
        .flatMap { is => ZStream.fromIterator(is.getLines()) }
        .flatMapPar(Parallelism) { line => ZStream.fromIterable(Pattern.split(line)) }
        .filterNot(_.isBlank)

    words.groupByKey(identity) { (key, streamForKey) =>
      val counts: ZIO[Any, Throwable, (String, Long)] = streamForKey.runCount.map(key -> _)
      ZStream.fromZIO(counts)
    }
  }

}
