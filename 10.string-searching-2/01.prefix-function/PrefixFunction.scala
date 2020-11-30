/* Префикс-функция
 *
 * Постройте префикс-функцию для всех непустых префиксов заданной строки s.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит s (1 <= |s| <= 10^6).  Строка состоит
 * из букв латинского алфавита.
 *
 * Формат выходного файла:
 *
 * Выведите значения префикс-функции для всех префиксов строки s длиной 1,
 * 2, ..., |s| в указанном порядке.
 *
 * Примеры:
 *
 * input.txt
 *   aaaAAA
 * output.txt
 *   0 1 2 0 0 0
 *
 * input.txt
 *   abacaba
 * output.txt
 *   0 0 1 0 1 2 3
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.annotation.tailrec
import scala.util.Using

object PrefixFunction {
  def calculateP(s: String): Array[Int] = {
    val n = s.length
    val p = new Array[Int](n)

    @tailrec
    def iter(i: Int, j: Int): Array[Int] = {
      if (i < n) {
        if (s(i) == s(j)) {
          p(i) = j + 1
          iter(i + 1, j + 1)
        } else if (j > 0) {
          iter(i, p(j - 1))
        } else {
          p(i) = 0
          iter(i + 1, 0)
        }
      } else
        p
    }

    iter(1, 0)
  }

  def main(args: Array[String]): Unit = {
    val s = Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      in.readLine()
    }

    val p = calculateP(s)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      p.foreach(out.printf("%d ", _))
      out.println()
    }
  }
}
