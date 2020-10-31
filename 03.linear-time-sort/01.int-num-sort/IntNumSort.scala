/* Сортировка целых чисел
 *
 * В этой задаче Вам нужно будет отсортировать много неотрицательных целых
 * чисел.
 *
 * Вам даны два массива, A и B, содержащие соответственно n и m элементов.
 * Числа, которые нужно будет отсортировать, имеют вид A_i * B_j, где
 * 1 <= i <= n и 1 <= j <= m.  Иными словами, каждый элемент первого массива
 * нужно умножить на каждый элемент второго массива.
 *
 * Пусть из этих чисел получится отсортированная последовательность С длиной
 * n * m.  Выведите сумму каждого десятого элемента этой последовательности (то
 * есть, C_1 + C_{11} + C_{21} + ...).
 *
 * Формат входного файла:
 *
 * В первой строке содержатся числа n и m (1 <= n, m <= 6000) -- размеры
 * массивов.  Во второй строке содержится n чисел -- элементы массива A.
 * Аналогично, в третьей строке содержится m чисел -- элементы массива B.
 * Элементы массива неотрицательны и не превосходят 40000.
 *
 * Формат выходного файла:
 *
 * Выведите одно число -- сумму каждого десятого элемента последовательности,
 * полученной сортировкой попарных произведенй элементов массивов A и B.
 *
 * Примеры:
 *
 * input.txt:
 *   4 4
 *   7 1 4 9
 *   2 7 8 11
 * output.txt:
 *   51
 *
 * Пояснение к примеру:
 *
 * Неотсортированная последовательность C выглядит следующим образом:
 *   [14, 2, 8, 18, 49, 7, 28, 63, 56, 8, 32, 72, 77, 11, 44, 99].
 *
 * Отсортировав ее, получим:
 *   [*2*, 7, 8, 8, 11, 14, 18, 28, 32, 44, *49*, 56, 63, 72, 77, 99].
 *
 * Жирным выделены первый и одиннадцатый элементы последовательности, при этом
 * двадцать первого элемента в ней нет.  Их сумма -- 51 -- и будет ответом.
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

object IntNumSort {
  def countingSort(a: Array[Int], b: Array[Int], op: Int => Int, k: Int): Unit = {
    val c = new Array[Int](k)

    for (i <- 0 until a.length)
      c(op(a(i))) += 1
    for (j <- 1 until k)
      c(j) += c(j - 1)
    for (i <- a.length - 1 to 0 by -1) {
      val key = op(a(i))
      c(key) -= 1
      b(c(key)) = a(i)
    }
  }

  def radixSort(a: Array[Int]): Unit = {
    val b = new Array[Int](a.length)
    countingSort(a, b, x => x       & 0xFFFF, 0x10000)
    countingSort(b, a, x => x >> 16 & 0xFFFF, 0x10000)
  }

  def main(args: Array[String]): Unit = {
    val nums = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      val m = in.nextInt()
      val a = Array.fill[Int](n)(in.nextInt())
      val b = Array.fill[Int](m)(in.nextInt())
      Array.tabulate[Int](n * m)((i: Int) => a(i / m) * b(i % m))
    }

    radixSort(nums)

    var sum = 0L
    for (i <- 0 until nums.length by 10) {
      sum += nums(i)
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.printf("%d\n", sum)
    }
  }
}
