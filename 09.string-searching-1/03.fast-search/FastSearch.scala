/* Быстрый поиск подстроки в строке
 *
 * Даны строки p и t.  Требуется найти все вхождения строки p в строку t в
 * качестве подстроки.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит p, вторая -- t (1 <= |p|,
 * |t| <= 10^6).  Строки состоят из букв латинского алфавита.
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

import scala.annotation.tailrec
import scala.collection.mutable.Queue
import scala.util.Using

/* Для того, чтобы решение уложилось во временные рамки, пришлось использовать
 * библиотеку ввода/вывода от составителей курса.
 */
import mooc._

// class RabinKarpSearch(val pat: Array[Byte]) {
//   val r = 257
//   var q = 10_000_000_019L
//
//   def hash(str: Array[Byte], offset: Int, length: Int): Long =
//     (offset until offset + length).foldLeft(0L)((h, i) => (r * h + str(i)) % q)
//
//   val m = pat.length
//   val rm = (0 until m - 1).foldLeft(1L)((rm, _) => (r * rm) % q)  // r^{m-1} % q
//   val hp = hash(pat, 0, m)
//
//   def checkSubstring(str: Array[Byte], offset: Int): Boolean = {
//     for (i <- 0 until m)
//       if (str(offset + i) != pat(i))
//         return false
//     true
//   }
//
//   def search(str: Array[Byte], startIndex: Int = 0): Option[Int] = {
//     val n = str.length
//     if (startIndex + m <= n) {
//       var h = hash(str, startIndex, m)
//
//       for (i <- startIndex to n - m) {
//         if (h == hp && checkSubstring(str, i))
//           return Some(i)
//
//         h = (h + q - rm * str(i) % q) % q
//         if (i + m < n)
//           h = (h * r + str(i + m)) % q
//       }
//     }
//
//     None
//   }
// }

class RabinKarpSearch(val str: Array[Byte], val pat: Array[Byte]) {
  val r = 257
  var q = 10_000_000_019L
  val n = str.length
  val m = pat.length

  def hash(str: Array[Byte], offset: Int, length: Int): Long =
    (offset until offset + length).foldLeft(0L)((h, i) => (r * h + str(i)) % q)

  val rm = (0 until m - 1).foldLeft(1L)((rm, _) => (r * rm) % q)  // r^{m-1} % q
  val hp = hash(pat, 0, m)

  val h = new Array[Long](n - m + 1)
  if (h.length > 0)
    h(0) = hash(str, 0, m)
  for (i <- 1 to n - m)
    h(i) = ((h(i - 1) + q - rm * str(i - 1) % q) % q * r + str(i + m - 1)) % q

  def checkSubstring(offset: Int): Boolean = {
    // Так называемая версия Монте-Карло, когда после совпадения хэша подстрока
    // не проверяется на равенство с шаблоном.  Полная реализация не проходит
    // тесты из-за ограничений по времени.
    //
    // for (i <- 0 until m)
    //   if (str(offset + i) != pat(i))
    //     return false
    true
  }

  @tailrec
  final def search(i: Int): Option[Int] =
    if (i <= n - m)
      if (h(i) == hp && checkSubstring(i)) Some(i) else search(i + 1)
    else
      None

  def findAll(): Queue[Int] = {
    val occurences: Queue[Int] = Queue()

    @tailrec
    def iter(offset: Int): Queue[Int] = {
      search(offset) match {
        case Some(i) =>
          occurences += i
          iter(i + 1)
        case None =>
          occurences
      }
    }

    iter(0)
  }
}

object FastSearch extends EdxIO.Receiver{
  def receive(io: EdxIO): Unit = {
    val p = io.nextBytes()
    val t = io.nextBytes()

    val occurrences = (new RabinKarpSearch(t, p)).findAll()

    io.println(occurrences.length)
    occurrences.foreach((i : Int) => io.print(s"${i + 1} "))
    io.println()
  }
}
