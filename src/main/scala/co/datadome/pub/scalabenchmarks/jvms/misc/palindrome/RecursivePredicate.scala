package co.datadome.pub.scalabenchmarks.jvms.misc.palindrome

import scala.annotation.tailrec

object RecursivePredicate {
  def isPalindrome(str: String): Boolean = isPalindrome(str, 0, str.length - 1)

  @tailrec
  private def isPalindrome(str: String, start: Int, end: Int): Boolean = {
    if (start >= end) true
    else if (!str(start).isLetterOrDigit) isPalindrome(str, start + 1, end)
    else if (!str(end).isLetterOrDigit) isPalindrome(str, start, end - 1)
    else if (str(start).toLower != str(end).toLower) false
    else isPalindrome(str, start + 1, end - 1)
  }
}
