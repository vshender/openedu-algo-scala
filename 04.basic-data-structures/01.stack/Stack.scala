/* Стек
 *
 * Реализуйте работу стека. Для каждой операции изъятия элемента выведите ее
 * результат.
 *
 * На вход программе подаются строки, содержащие команды.  Каждая строка
 * содержит одну команду.  Команда -- это либо "+ N", либо "−".  Команда "+ N"
 * означает добавление в стек числа N, по модулю не превышающего 10^9.  Команда
 * "−" означает изъятие элемента из стека.  Гарантируется, что не происходит
 * извлечения из пустого стека.  Гарантируется, что размер стека в процессе
 * выполнения команд не превысит 10^6 элементов.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится M (1 <= M <= 10^6) -- число
 * команд.  Каждая последующая строка исходного файла содержит ровно одну
 * команду.
 *
 * Формат выходного файла:
 *
 * Выведите числа, которые удаляются из стека с помощью команды "−", по одному
 * в каждой строке.  Числа нужно выводить в том порядке, в котором они были
 * извлечены из стека.  Гарантируется, что изъятий из пустого стека не
 * производится.
 *
 * Пример:
 *
 * input.txt:
 *   6
 *   + 1
 *   + 10
 *   -
 *   + 2
 *   + 1234
 *   -
 * output.txt:
 *   10
 *   1234
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.reflect.ClassTag
import scala.util.Using

class Stack[T](val size: Int)(implicit ordering: Ordering[T], t: ClassTag[T]) {
  val stack = new Array[T](size)
  var top = 0

  def push(x: T): Unit = {
    if (top == size)
      throw new RuntimeException("stack overflow")

    stack(top) = x
    top += 1
  }

  def pop(): T = {
    if (top == 0)
      throw new RuntimeException("stack underflow")

    top -= 1
    stack(top)
  }
}

object Stack {
  def main(args: Array[String]): Unit = {
    Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val m = in.readLine().toInt
        val stack = new Stack[Int](m)

        for (i <- 0 until m) {
          val cmd = in.readLine()
          cmd(0) match {
            case '+' => stack.push(cmd.substring(2).toInt)
            case '-' => out.println(stack.pop())
          }
        }
      }
    }
  }
}
