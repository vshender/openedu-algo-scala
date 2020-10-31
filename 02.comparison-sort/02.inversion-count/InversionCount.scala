/* Число инверсий
 *
 * /Инверсией/ в последовательности чисел A называется такая ситуация, когда
 * i < j, а A_i > A_j.
 *
 * Дан массив целых чисел.  Ваша задача -- подсчитать число инверсий в нем.
 *
 * Подсказка: чтобы сделать это быстрее, можно воспользоваться модификацией
 * сортировки слиянием.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится число n (1 <= n <= 10^5) -- число
 * элементов в массиве.  Во второй строке находятся n целых чисел, по модулю не
 * превосходящих 10^9.
 *
 * Формат выходного файла:
 *
 * В выходной файл надо вывести число инверсий в массиве.
 *
 * Пример:
 *
 * input.txt:
 *   10
 *   1 8 2 1 4 7 3 2 3 6
 * output.txt
 *   17
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.reflect.ClassTag
import scala.util.Using

object InversionCount {
  def inversionCount[T](array: Array[T])(implicit ordering: Ordering[T], t: ClassTag[T]): Long = {
    val tmp = new Array[T](array.length)

    def merge(l: Int, m: Int, r: Int): Long = {
      var invCount: Long = 0

      var (i, j, k) = (l, m, l)
      while (i < m || j < r) {
        if (j == r || (i < m && ordering.lteq(array(i), array(j)))) {
          tmp(k) = array(i)
          i += 1
        } else {
          tmp(k) = array(j)
          j += 1
          invCount += m - i
        }
        k += 1
      }

      for (k <- l until r)
        array(k) = tmp(k)

      invCount
    }

    def sort(l: Int, r: Int): Long = {
      if (l == r - 1)
        return 0

      var invCount: Long = 0
      val m = l + (r - l) / 2

      invCount += sort(l, m)
      invCount += sort(m, r)

      invCount += merge(l, m, r)

      invCount
    }

    sort(0, array.length)
  }

  def main(args: Array[String]): Unit = {
    val array = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      Array.fill[Int](n)(in.nextInt())
    }

    val invCount = inversionCount(array)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(invCount)
    }
  }
}
