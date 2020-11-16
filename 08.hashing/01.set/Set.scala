/* Множество
 *
 * Реализуйте множество с операциями "добавление ключа", "удаление ключа",
 * "проверка существования ключа".
 *
 * Формат входного файла:
 *
 * В первой строке входного файла находится строго положительное целое число
 * операций N, не превышающее 5 * 10^5.  В каждой из последующих N строк
 * находится одна из следующих операций:
 * - A x -- добавить элемент x в множество.  Если элемент уже есть в множестве,
 *   то ничего делать не надо.
 * - D x -- удалить элемент x.  Если элемента x нет, то ничего делать не надо.
 * - ? x -- если ключ x есть в множестве, выведите "Y", если нет, то выведите
 *   "N".
 *
 * Аргументы указанных выше операций -- целые числа, не превышающие по модулю
 * 10^18.
 *
 * Формат выходного файла:
 *
 * Выведите последовательно результат выполнения всех операций "?".  Следуйте
 * формату выходного файла из примера.
 *
 * Пример:
 *
 * input.txt:
 *   8
 *   A 2
 *   A 5
 *   A 3
 *   ? 2
 *   ? 4
 *   A 2
 *   D 2
 *   ? 2
 * output.txt:
 *   Y
 *   Y
 *   N
 *
 * Примечание:
 *
 * Эту задачу можно решить совершенно разными способами, включая использование
 * различных средств стандартных библиотек (правда, не всех -- в стандартных
 * библиотеках некоторых языков программирования используются слишком
 * предсказуемые методы хеширования).  Именно по этой причине ее разумно
 * использовать для проверки реализаций хеш-таблиц, которые понадобятся в
 * следующих задачах этой недели.  После окончания текущей порции
 * экспериментов, пожалуйста, не забудьте сдать правильное решение, чтобы эта
 * задача была зачтена!
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

class HashTable(val capacity: Int) {
  val htable = Array.fill[List[Long]](capacity)(Nil)

  def hash(x: Long): Int =
    (Math.abs(x) % capacity).toInt

  def contains(x: Long): Boolean =
    htable(hash(x)).exists(_ == x)

  def insert(x: Long): Unit =
    if (!contains(x)) {
      val h = hash(x)
      htable(h) = x :: htable(h)
    }

  def delete(x: Long): Unit = {
    val h = hash(x)
    htable(h) = htable(h).filter(_ != x)
  }
}

object Set {
  def main(args: Array[String]): Unit = {
    Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val n = in.nextInt()
        val ht = new HashTable(n)
        for (i <- 0 until n) {
          val cmd = in.next()
          val x = in.nextLong()

          cmd match {
            case "A" => ht.insert(x)
            case "D" => ht.delete(x)
            case "?" => out.println(if (ht.contains(x)) "Y" else "N")
          }
        }
      }
    }
  }
}
