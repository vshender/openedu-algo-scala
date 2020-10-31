/* k-ая порядковая статистика
 *
 * Дан массив из n элементов.  Какие числа являются k_1-ым, (k_1 + 1)-ым, ...,
 * k_2-ым в порядке неубывания в этом массиве?
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержатся три числа: n -- размер массива, а
 * также границы интервала k_1 и k_2, при этом 2 <= n <= 4 * 10^7,
 * 1 <= k_1 <= k_2 <= n, k_2 - k_1 < 200.
 *
 * Во второй строке находятся числа A, B, C, a_1, a_2, по модулю не
 * превосходящие 10^9.  Вы должны получить элементы массива, начиная с
 * третьего, по формуле: a_i = A * a_{i - 2} + B * a_{i - 1} + C.  Все
 * вычисления должны производится в 32-битном знаковом типе, переполнения
 * должны игнорироваться.
 *
 * Обращаем Ваше внимание, что массив из 32-битных целых чисел занимает в
 * памяти *160 мегабайт*!  Будьте аккуратны!
 *
 * Подсказка: эту задачу лучше всего решать модификацией быстрой сортировки.
 * Однако сортировка массива целиком по времени, скорее всего, не пройдет,
 * поэтому нужно подумать, как модифицировать быструю сортировку, чтобы не
 * сортировать те части массива, которые не нужно сортировать.
 *
 * Эту задачу, скорее всего, *нельзя решить ни на Python, ни на PyPy*.  Мы не
 * нашли способа сгенерировать 32-битных целых чисел и при этом уложиться в
 * ограничение по времени.  Если у Вас тоже не получается, попробуйте другой
 * язык программирования!
 *
 * Формат выходного файла:
 *
 * В первой и единственной строке выходного файла выведите k_1-ое,
 * (k_1 + 1)-ое, ..., k_2-ое в порядке неубывания числа в массиве a.  Числа
 * разделяйте одним пробелом.
 *
 * Примеры:
 *
 * input.txt:
 *   5 3 4
 *   2 3 5 1 2
 * output.txt:
 *   13 48
 *
 * input.txt:
 *   5 3 4
 *   200000 300000 5 1 2
 * output.txt:
 *   2 800005
 *
 * Примечание: Во втором примере элементы массива равны:
 * [1, 2, 800005, −516268571, 1331571109].
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.{Using, Random}

object KOrderStatistic {
  // Изначально написал обобщенный код для `Array[T]`, но такой код не проходил
  // по ограничениям размера памяти.

  def swap(a: Array[Int], i: Int, j: Int): Unit = {
    val t = a(i)
    a(i) = a(j)
    a(j) = t
  }

  def partition(a: Array[Int], lo: Int, hi: Int): (Int, Int) = {
    // Разбиение подмассива на две части: элементы меньшие опорного и элементы
    // большие или равные опорном элементу.

    swap(a, lo, Random.between(lo, hi + 1))
    // Опорный элемент в a(lo)

    var last = lo
    for (i <- lo + 1 to hi)
      // Инвариант:
      // - a(lo) -- опорный элемент;
      // - a(lo+1 .. last) -- элементы меньшие опорного;
      // - a(last+1 .. i-1) -- элементы большие или равные опорному;
      // - a(i .. hi) -- непросмотренные элементы.
      if (a(i) < a(lo)) {
        last += 1
        swap(a, last, i)
      }

    swap(a, lo, last)
    // Теперь:
    // - a(left .. last-1) -- элементы меньшие опорного элемента;
    // - a(last) -- опорный элемент;
    // - a(last+1 .. hi) -- элементы большие или равные опорному.

    // Дополнительное разбиение правой части на элементы равные опорному и
    // элементы большие опорного элемента.  Без этой оптимизации решение не
    // проходило по времени тест максимальной длины с большим количеством
    // одинаковых элементов.
    val pIdx = last

    for (i <- last + 1 to hi)
      if (a(i) == a(pIdx)) {
        last += 1
        swap(a, last, i)
      }

    (pIdx, last)
  }

  def sortInterval(a: Array[Int], k1: Int, k2: Int): Unit = {
    def sortIntervalAux(a: Array[Int], lo: Int, hi: Int): Unit = {
      if (lo >= hi)
        return

      val (pIdxLo, pIdxHi) = partition(a, lo, hi)

      if (k1 < pIdxLo)
        sortIntervalAux(a, lo, pIdxLo - 1)
      if (pIdxHi < k2)
        sortIntervalAux(a, pIdxHi + 1, hi)
    }

    sortIntervalAux(a, 0, a.length - 1)
  }

  def main(args: Array[String]): Unit = {
    val (nums, k1, k2) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val (n, k1, k2) = (in.nextInt(), in.nextInt() - 1, in.nextInt() - 1)
      val (a, b, c) = (in.nextInt(), in.nextInt(), in.nextInt())

      val nums = new Array[Int](n)
      nums(0) = in.nextInt()
      nums(1) = in.nextInt()
      for (i <- 2 until n)
        nums(i) = a * nums(i - 2) + b * nums(i - 1) + c

      (nums, k1, k2)
    }

    sortInterval(nums, k1, k2)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      for (i <- k1 to k2)
        out.printf("%d ", nums(i))
      out.println()
    }
  }
}