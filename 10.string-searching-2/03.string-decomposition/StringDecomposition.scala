/* Декомпозиция строки
 *
 * Строка "ABCABCDEDEDEF" содержит подстроку "ABC", повторяющуюся два раза
 * подряд, и подстроку "DE", повторяющуюся три раза подряд.  Таким образом, ее
 * можно записать как "ABC*2+DE*3+F", что занимает меньше места, чем исходная
 * запись той же строки.
 *
 * Ваша задача -- построить наиболее экономное представление данной строки s в
 * виде, продемонстрированном выше, а именно, подобрать такие s_1, a_1, ...,
 * s_k, a_k, где s_i -- строки, а a_i -- числа, чтобы s = s_1 * a_1 + ... +
 * + s_k * a_k.  Под операцией умножения строки на целое положительное число
 * подразумевается конкатенация одной или нескольких копий строки, число
 * которых равно числовому множителю, то есть, "ABC" * 2 = "ABCABC".  При этом
 * требуется минимизировать общую длину итогового описания, в котором
 * компоненты разделяются знаком "+", а умножение строки на число записывается
 * как умножаемая строка и множитель, разделенные знаком "*".  Если же
 * множитель равен единице, его вместе со знаком "*" допускается не указывать.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит s (1 <= |s| <= 5 * 10^3).  Строка
 * состоит из букв латинского алфавита.
 *
 * Формат выходного файла:
 *
 * Выведите оптимальное представление строки, данной во входном файле.  Если
 * оптимальных представлений несколько, выведите любое.
 *
 * Примеры:
 *
 * input.txt:
 *   ABCABCDEDEDEF
 * output.txt:
 *   ABC*2+DE*3+F
 *
 * input.txt:
 *   Hello
 * output.txt:
 *   Hello
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.annotation.tailrec
import scala.collection.mutable.Buffer
import scala.util.Using

object StringDecomposition {
  // Вычислить префикс-функцию для суффикса строки, начинающегося с позиции
  // `offset`.
  def calculateP(s: String, offset: Int): Array[Int] = {
    val n = s.length
    val p = new Array[Int](n - offset)

    @tailrec
    def iter(i: Int, j: Int): Array[Int] = {
      if (offset + i < n) {
        if (s(offset + i) == s(offset + j)) {
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

  // Найти повторяющийся префикс максимальной длины для суффикса строки,
  // начинающегося с позиции `offset`.  Метод возвращает кортеж из двух чисел:
  // - длина повторяющегося участка префикса;
  // - количество повторений этого участка в префиксе.
  def findRepeatingPrefix(s: String, offset: Int): (Int, Int) = {
    val p = calculateP(s, offset)
    val n = s.length - offset

    @tailrec
    def iter(i: Int, max: Int, maxIdx: Int): (Int, Int) =
      if (i < n)
        if (p(i) > max && (i + 1) % (i + 1 - p(i)) == 0)
          iter(i + 1, p(i), i)
        else
          iter(i + 1, max, maxIdx)
      else
        (max, maxIdx)

    val (max, maxIdx) = iter(1, p(0), 0)
    if (max > 0) {
      val l = maxIdx + 1 - max
      (l, (maxIdx + 1) / l)
    } else
      (1, 1)
  }

  // Число цифр в десятичном представлении переданного числа большего нуля.
  def digitsNum(num: Int): Int =
    (1 + Math.log10(num)).toInt

  // Осуществить декомпозицию строки в соответствии с описанными в задании
  // условиями.
  def decomposeString(s: String): String = {
    val n = s.length

    // `l1(i)` -- минимальная длина декомпозиции подстроки, начинающейся с
    // позиции `i`, где `i`-й символ просто присоединяется к декомпозиции
    // строки, начинающейся с позиции `i+1`.
    val l1 = new Array[Int](n + 1)

    // `l2(i)` -- минимальная длина декомпозиции подстроки, начинающейся с
    // позиции `i`, где некий повторяющийся префикс, начинающийся с позиции
    // `i`, кодируется указанным в условии способом.
    val l2 = Array.fill[Int](n + 1)(10_000)

    // `p(i)` -- описание префикса (длина повторяющего участка и число
    // повторений), который был использован при вычислении `l2(i)`.
    val p = new Array[(Int, Int)](n)

    // Вычисляем `l1` и `l2` с конца.
    for (i <- n - 1 to 0 by -1) {
      l1(i) = 1 + Math.min(l1(i + 1), 1 + l2(i + 1))

      val (l, k) = findRepeatingPrefix(s, i)
      p(i) = (l, k)

      val minSuffixLen = Math.min(l1(i + l * k), l2(i + l * k))
      l2(i) = l + 1 + digitsNum(k) + (if (minSuffixLen > 0) 1 else 0) + minSuffixLen
    }

    // Восстанавливаем минимальную декомпозицию строки.
    val dsb = Buffer[String]()

    @tailrec
    def iter(i: Int, b: Boolean): String = {
      if (i < n) {
        if (l1(i) <= l2(i)) {
          if (b)
            dsb += "+"

          dsb += s(i).toString
          iter(i + 1, false)
        } else {
          val (l, k) = p(i)
          if (i > 0)
            dsb += "+"

          dsb += s"${s.substring(i, i + l)}*$k"
          iter(i + l * k, true)
        }
      } else
        dsb.mkString
    }

    iter(0, false)
  }

  def main(args: Array[String]): Unit = {
    val s = Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      in.readLine()
    }

    val ds = decomposeString(s)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(ds)
    }
  }
}
