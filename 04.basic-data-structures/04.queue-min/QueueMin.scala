/* Очередь с минимумом
 *
 * Реализуйте работу очереди.  В дополнение к стандартным операциям очереди,
 * необходимо также отвечать на запрос о минимальном элементе из тех, которые
 * сейчас находится в очереди.  Для каждой операции запроса минимального
 * элемента выведите ее результат.
 *
 * На вход программе подаются строки, содержащие команды.  Каждая строка
 * содержит одну команду.  Команда -- это либо "+ N", либо "−", либо "?".
 * Команда "+ N" означает добавление в очередь числа N, по модулю не
 * превышающего 10^9.  Команда "−" означает изъятие элемента из очереди.
 * Команда "?" означает запрос на поиск минимального элемента в очереди.
 *
 * Формат входного файла:
 *
 * В первой строке содержится M (1 <= M <= 10^6) -- число команд.  В
 * последующих строках содержатся команды, по одной в каждой строке.
 *
 * Формат выходного файла:
 *
 * Для каждой операции поиска минимума в очереди выведите её результат.
 * Результаты должны быть выведены в том порядке, в котором эти операции
 * встречаются во входном файле.  Гарантируется, что операций извлечения или
 * поиска минимума для пустой очереди не производится.
 *
 * Пример:
 *
 * input.txt:
 *   7
 *   + 1
 *   ?
 *   + 10
 *   ?
 *   -
 *   ?
 *   -
 * output.txt:
 *   1
 *   1
 *   10
*/

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.collection.mutable.Queue
import scala.reflect.ClassTag
import scala.util.Using

class QueueMin[T]()(implicit ordering: Ordering[T], t: ClassTag[T]) {
  // Очередь для хранения элементов очереди.
  val queue = new Queue[T]()

  // Очередь минимальных элементов.  Каждый элемент -- пара из двух значений:
  // - следующий минимальный элемент;
  // - скольким элементам из первой очереди соответствует этот минимальный
  //   элемент.
  val minQueue = new Queue[(T, Integer)]()

  def enqueue(x: T): Unit = {
    queue.enqueue(x)

    // Заменяем все бо́льшие элементы из конца очереди минимальных элементов на
    // новый элемент.
    var xc = 1
    while (minQueue.size > 0 && ordering.lt(x, minQueue.last._1)) {
      val (_, lc) = minQueue.removeLast()
      xc += lc
    }
    minQueue.enqueue((x, xc))
  }

  def dequeue(): T = {
    val x = queue.dequeue()

    val (m, xc) = minQueue.dequeue()
    if (xc > 1)
      minQueue.prepend((m, xc - 1))

    x
  }

  def min: T = {
    minQueue.front._1
  }
}

object QueueMin {
  def main(args: Array[String]): Unit = {
    Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val m = in.readLine().toInt
        val qm = new QueueMin[Int]()

        for (i <- 0 until m) {
          val cmd = in.readLine()
          cmd(0) match {
            case '+' => qm.enqueue(cmd.substring(2).toInt)
            case '-' => qm.dequeue()
            case '?' => out.println(qm.min)
          }
        }
      }
    }
  }
}
