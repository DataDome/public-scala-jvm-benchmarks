package co.datadome.pub.scalabenchmarks.jvms.api.compression

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}


object Gzip {
  def compress(data: Array[Byte]): Array[Byte] = {
    val baos = new ByteArrayOutputStream(data.length)
    val gzipOut = new GZIPOutputStream(baos)
    try {
      gzipOut.write(data)
      gzipOut.finish()
      baos.toByteArray
    } finally {
      gzipOut.close()
    }
  }

  def decompress(compressed: Array[Byte]): Array[Byte] = {
    val bais = new ByteArrayInputStream(compressed)
    val gzipIn = new GZIPInputStream(bais)
    try {
      gzipIn.readAllBytes()
    } finally {
      gzipIn.close()
    }
  }
}