/* Куча ли?
 *
 * Структуру данных "куча", или, более конкретно, "неубывающая пирамида", можно
 * реализовать на основе массива.
 *
 * Для этого должно выполнятся основное свойство неубывающей пирамиды, которое
 * заключается в том, что для каждого 1 <= i <= n выполняются условия:
 * - если 2i <= n, то a[i] <= a[2i];
 * - если 2i + 1 <= n, то a[i] <= a[2i + 1].
 *
 * Дан массив целых чисел.  Определите, является ли он неубывающей пирамидой.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит целое число n (1 <= n <= 10^6).
 * Вторая строка содержит n целых чисел, по модулю не превосходящих 2 * 10^9.
 *
 * Формат выходного файла:
 *
 * Выведите "YES", если массив является неубывающей пирамидой, и "NO" в
 * противном случае.
 *
 * Примеры:
 *
 * input.txt:
 *   5
 *   1 0 1 2 0
 * output.txt:
 *   NO
 *
 * input.txt:
 *   5
 *   1 3 2 5 4
 * output.txt:
 *   YES
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

object IsHeap {
  def leftChildIdx(i: Int): Int =
    i * 2 + 1

  def rightChildIdx(i: Int): Int =
    i * 2 + 2

  def isHeap(nums: Array[Int]): Boolean = {
    for (i <- 0 until nums.length / 2) {
      if (nums(i) > nums(leftChildIdx(i)))
        return false
      if (rightChildIdx(i) < nums.length && nums(i) > nums(rightChildIdx(i)))
        return false
    }

    true
  }

  def main(args: Array[String]): Unit = {
    val nums = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      Array.fill(n)(in.nextInt())
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(if (isHeap(nums)) "YES" else "NO")
    }
  }
}
