package co.datadome.pub.scalabenchmarks.jvms.misc.palindrome

object FunctionalPredicate {

  def isPalindrome(str: String): Boolean = {
    val cleanedStr = str.toLowerCase.filter(_.isLetterOrDigit)
    cleanedStr == cleanedStr.reverse
  }
}
