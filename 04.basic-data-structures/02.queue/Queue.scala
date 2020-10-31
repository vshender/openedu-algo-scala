/* Очередь
 *
 * Реализуйте работу очереди. Для каждой операции изъятия элемента выведите ее
 * результат.
 *
 * На вход программе подаются строки, содержащие команды.  Каждая строка
 * содержит одну команду.  Команда -- это либо "+ N", либо "−".  Команда "+ N"
 * означает добавление в очередь числа N, по модулю не превышающего 10^9.
 * Команда "−" означает изъятие элемента из очереди.  Гарантируется, что размер
 * очереди в процессе выполнения команд не превысит 10^6 элементов.
 *
 * Формат входного файла:
 *
 * В первой строке содержится M (1 <= M <= 10^6) -- число команд.  В
 * последующих строках содержатся команды, по одной в каждой строке.
 *
 * Формат выходного файла:
 *
 * Выведите числа, которые удаляются из очереди с помощью команды "−", по
 * одному в каждой строке.  Числа нужно выводить в том порядке, в котором они
 * были извлечены из очереди.  Гарантируется, что извлечения из пустой очереди
 * не производится.
 *
 * Пример:
 *
 * input.txt:
 *   4
 *   + 1
 *   + 10
 *   -
 *   -
 * output.txt:
 *   1
 *   10
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.reflect.ClassTag
import scala.util.Using

class Queue[T](val size: Int)(implicit ordering: Ordering[T], t: ClassTag[T]) {
  val queue = new Array[T](size)
  var len = 0
  var head = 0
  var tail = 0

  def enqueue(x: T): Unit = {
    if (len == size)
      throw new RuntimeException("queue overflow")

    queue(tail) = x
    tail += 1
    if (tail == size)
      tail = 0

    len += 1
  }

  def dequeue(): T = {
    if (len == 0)
      throw new RuntimeException("queue underflow")

    val t = queue(head)

    head += 1
    if (head == size)
      head = 0

    len -= 1

    t
  }
}

object Queue {
  def main(args: Array[String]): Unit = {
    Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val m = in.readLine().toInt
        val queue = new Queue[Int](m)

        for (i <- 0 until m) {
          val cmd = in.readLine()
          cmd(0) match {
            case '+' => queue.enqueue(cmd.substring(2).toInt)
            case '-' => out.println(queue.dequeue())
          }
        }
      }
    }
  }
}
