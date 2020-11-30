/* Z-функция
 *
 * Постройте Z-функцию для заданной строки s.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит s (2 <= |s| <= 10^6).  Строка состоит
 * из букв латинского алфавита.
 *
 * Формат выходного файла:
 *
 * Выведите значения Z-функции для всех индексов 2, 3, ..., |s| строки s в
 * указанном порядке.
 *
 * Примеры:
 *
 * input.txt:
 *   aaaAAA
 * output.txt:
 *   2 1 0 0 0
 *
 * input.txt:
 *   abacaba
 * output.txt:
 *   0 1 0 3 0 1
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.annotation.tailrec
import scala.util.Using

object ZFunction {
  def calculateZ(s: String): Array[Int] = {
    val n = s.length
    val z = new Array[Int](n - 1)

    @tailrec
    def forward(i: Int, j: Int = 0): Int =
      if (i + j < n && s(i + j) == s(j))
        forward(i, j + 1)
      else
        j

    @tailrec
    def iter(i: Int, l: Int, r: Int): Array[Int] = {
      if (i < n) {
        if (r <= i) {
          val j = forward(i)
          z(i - 1) = j
          iter(i + 1, i, i + j)
        } else {
          if (z(i - l - 1) < r - i) {
            z(i - 1) = z(i - l - 1)
            iter(i + 1, l, r)
          } else {
            val j = forward(i, r - i)
            z(i - 1) = j
            iter(i + 1, i, i + j)
          }
        }
      } else
        z
    }

    iter(1, 0, 0)
  }

  def main(args: Array[String]): Unit = {
    val s = Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      in.readLine()
    }

    val z = calculateZ(s)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      z.foreach(out.printf("%d ", _))
      out.println()
    }
  }
}
