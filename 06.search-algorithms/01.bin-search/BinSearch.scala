/* Двоичный поиск
 *
 * Дан массив из n элементов, упорядоченный в порядке неубывания, и m запросов:
 * найти первое и последнее вхождение некоторого числа в массив.  Требуется
 * ответить на эти запросы.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится одно число n -- размер массива
 * (1 <= n <= 10^5).  Во второй строке находятся n чисел в порядке
 * неубывания -- элементы массива.  В третьей строке находится число m -- число
 * запросов (1 <= m <= 10^5).  В следующей строке находятся m чисел -- запросы.
 * Элементы массива и запросы являются целыми числами, неотрицательны и не
 * превышают 10^9.
 *
 * Формат выходного файла:
 *
 * Для каждого запроса выведите в отдельной строке номер (индекс) первого и
 * последнего вхождения этого числа в массив.  Ecли числа в массиве нет,
 * выведите два раза -1.
 *
 * Пример:
 *
 * input.txt:
 *   5
 *   1 1 2 2 2
 *   3
 *   1 2 3
 * output.txt:
 *   1 2
 *   3 5
 *   -1 -1
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.annotation.tailrec
import scala.util.Using

object BinSearch {
  def binSearch[T](a: Array[T], x: T, left: Int, right: Int)
    (implicit ordering: Ordering[T]): Option[Int] = {

    var l = left
    var r = right

    while (l < r) {
      val m = l + (r - l) / 2
      if (ordering.lt(a(m), x))
        l = m + 1
      else if (ordering.lt(x, a(m)))
        r = m
      else
        return Some(m)
    }

    return None
  }

  // Линейный поиск границ интервала не проходит ограничение по времени.
  //
  // def findInterval[T](a: Array[T], i: Int): (Int, Int) = {
  //   var l = i
  //   while (l > 0 && a(l - 1) == a(l))
  //     l -= 1
  //   var r = i
  //   while (r < a.length - 1 && a(r) == a(r + 1))
  //     r += 1
  //   (l, r)
  // }

  def findInterval[T](a: Array[T], i: Int)(implicit ordering: Ordering[T]): (Int, Int) = {
    @tailrec
    def findLeft(l: Int): Int = binSearch(a, a(i), 0, l) match {
      case Some(i) => findLeft(i)
      case None    => l
    }

    @tailrec
    def findRight(r: Int): Int = binSearch(a, a(i), r + 1, a.length) match {
      case Some(i) => findRight(i)
      case None    => r
    }

    (findLeft(i), findRight(i))
  }

  def main(args: Array[String]): Unit = {
    Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val n = in.nextInt()
        val nums = Array.fill(n)(in.nextInt())

        val m = in.nextInt()
        for (i <- 0 until m) {
          val x = in.nextInt()
          val (l, r) = binSearch(nums, x, 0, nums.length) match {
            case Some(j) => findInterval(nums, j) match {
              case (l, r) => (l + 1, r + 1)
            }
            case None    => (-1, -1)
          }
          out.printf("%d %d\n", l, r)
        }
      }
    }
  }
}
