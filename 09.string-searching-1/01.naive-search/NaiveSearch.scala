/* Наивный поиск подстроки в строке
 *
 * Даны строки p и t.  Требуется найти все вхождения строки p в строку t в
 * качестве подстроки.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит p, вторая -- t (1 <= |p|,
 * |t| <= 10^4).  Строки состоят из букв латинского алфавита.
 *
 * Формат выходного файла:
 *
 * В первой строке выведите число вхождений строки p в строку t.  Во второй
 * строке выведите в возрастающем порядке номера символов строки t, с которых
 * начинаются вхождения p.  Символы нумеруются с единицы.
 *
 * Примеры:
 *
 * input.txt:
 *   aba
 *   abaCaba
 * output.txt:
 *   2
 *   1 5
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.annotation.tailrec
import scala.util.Using

class NaiveSearch(str: String, pat: String) {
  val n = str.length
  val m = pat.length

  def checkSubstring(offset: Int): Boolean = {
    for (i <- 0 until m)
      if (str(offset + i) != pat(i))
        return false
    true
  }

  @tailrec
  final def search(i: Int): Option[Int] =
    if (i <= n - m)
      if (checkSubstring(i)) Some(i) else search(i + 1)
    else
      None

  def findAll(): Seq[Int] = {
    @tailrec
    def iter(offset: Int, occurrences: List[Int]): List[Int] = {
      search(offset) match {
        case Some(i) => iter(i + 1, i :: occurrences)
        case None    => occurrences.reverse
      }
    }

    iter(0, List())
  }
}

object NaiveSearch {
  def main(args: Array[String]): Unit = {
    val (p, t) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      (in.nextLine(), in.nextLine())
    }

    val occurrences = (new NaiveSearch(t, p)).findAll()

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(occurrences.length)
      occurrences.foreach((i : Int) => out.printf("%d ", i + 1))
      out.println()
    }
  }
}
