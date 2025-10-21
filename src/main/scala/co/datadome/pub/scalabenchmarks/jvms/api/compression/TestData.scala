package co.datadome.pub.scalabenchmarks.jvms.api.compression

import scala.util.Random

/** Generates testData with a mix of patterns for more realistic compression scenario:
 * - some repeating patterns (compressible)
 * - some random data (less compressible)
 */
object TestData {

  def generate(dataSize: Int)(implicit random: Random): Array[Byte] = {
    val word = random.nextBytes(3)

    val res = Array.tabulate(dataSize) { i =>
      (i % 3) match {
        case 0 => random.nextInt(256).toByte // Random data
        case 1 => 'A'.toByte // Repeated character
        case 2 => (i % 10 + 48).toByte // Digit pattern
      }
    }

    (0 until 30).foreach { _ =>
      val ix = random.nextInt(dataSize - 3)
      res(ix) = word(0)
      res(ix + 1) = word(1)
      res(ix + 2) = word(2)
    }

    res
  }
}
