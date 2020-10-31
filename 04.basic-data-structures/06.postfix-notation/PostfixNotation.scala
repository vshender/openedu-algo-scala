/* Постфиксная запись
 *
 * В постфиксной записи (или обратной польской записи) операция записывается
 * после двух операндов.  Например, сумма двух чисел A и B записывается как
 * "A B +".  Запись "B C + D *" обозначает привычное нам "(B + C) * D", а
 * запись "A B C + D * +" означает "A + (B + C) * D".  Достоинство постфиксной
 * записи в том, что она не требует скобок и дополнительных соглашений о
 * приоритете операторов для своего чтения.
 *
 * Дано выражение в обратной польской записи.  Определите его значение.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла дано число N (1 <= N <= 10^6) -- число
 * элементов выражения.  Во второй строке содержится выражение в постфиксной
 * записи, состоящее из N элементов.  В выражении могут содержаться
 * неотрицательные однозначные числа и операции +, −, *.  Каждые два соседних
 * элемента выражения разделены ровно одним пробелом.
 *
 * Формат выходного файла:
 *
 * Необходимо вывести значение записанного выражения.  Гарантируется, что
 * результат выражения, а также результаты всех промежуточных вычислений по
 * модулю будут меньше, чем 2^31.
 *
 * Пример:
 *
 * input.txt:
 *   7
 *   8 9 + 1 7 - *
 * output.txt:
 *   -102
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.collection.mutable.Stack
import scala.util.Using

class PostfixEvaluator {
  val stack = Stack[Int]()

  def top(): Int =
    stack.top

  def num(x: Int): Unit =
    stack.push(x)

  def add(): Unit = {
    val b = stack.pop()
    val a = stack.pop()
    num(a + b)
  }

  def sub(): Unit = {
    val b = stack.pop()
    val a = stack.pop()
    num(a - b)
  }

  def mul(): Unit = {
    val b = stack.pop()
    val a = stack.pop()
    num(a * b)
  }

  def execCmd(cmd: String) =
    cmd match {
      case "+" => add()
      case "-" => sub()
      case "*" => mul()
      case _   => num(cmd.toInt)
    }
}

object PostfixNotation {
  def main(args: Array[String]): Unit = {
    val pe = new PostfixEvaluator()

    Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      for (i <- 0 until n)
        pe.execCmd(in.next())
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(pe.top())
    }
  }
}
