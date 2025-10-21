package co.datadome.pub.scalabenchmarks.jvms.api.compression

import net.jpountz.lz4.LZ4Factory


object Lz4 {

  private val lz4Factory = LZ4Factory.fastestInstance()

  private val fastCompressor = lz4Factory.fastCompressor()
  private val highCompressor = lz4Factory.highCompressor()

  private val fastDecompressor = lz4Factory.fastDecompressor()
  private val safeDecompressor = lz4Factory.safeDecompressor()

  def compressFast(data: Array[Byte]): Array[Byte] = {
    val maxCompressedLength = fastCompressor.maxCompressedLength(data.length)
    val compressed = new Array[Byte](maxCompressedLength)
    val compressedLength = fastCompressor.compress(data, 0, data.length, compressed, 0, maxCompressedLength)
    compressed.take(compressedLength)
  }

  def compressHigh(data: Array[Byte]): Array[Byte] = {
    val maxCompressedLength = highCompressor.maxCompressedLength(data.length)
    val compressed = new Array[Byte](maxCompressedLength)
    val compressedLength = highCompressor.compress(data, 0, data.length, compressed, 0, maxCompressedLength)
    compressed.take(compressedLength)
  }

  def decompressFast(compressed: Array[Byte], originalSize: Int): Array[Byte] = {
    val decompressed = new Array[Byte](originalSize)
    fastDecompressor.decompress(compressed, 0, decompressed, 0, originalSize)
    decompressed
  }

  def decompressSafe(compressed: Array[Byte], originalSize: Int): Array[Byte] = {
    val decompressed = new Array[Byte](originalSize)
    safeDecompressor.decompress(compressed, 0, compressed.length, decompressed, 0)
    decompressed
  }
}