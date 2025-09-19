package co.datadome.pub.scalabenchmarks.jvms.misc.palindrome

object IterativePredicate {

  def isPalindrome(str: String): Boolean = {
    var start = 0
    var end = str.length - 1
    while (start < end) {
      if (!str(start).isLetterOrDigit) {
        start += 1
      } else if (!str(end).isLetterOrDigit) {
        end -= 1
      } else if (str(start).toLower != str(end).toLower) {
        return false
      } else {
        start += 1
        end -= 1
      }
    }
    true
  }

}
