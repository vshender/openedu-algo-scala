/* Задача "a + b"
 *
 * В данной задаче требуется вычислить сумму двух заданных чисел.
 *
 * Формат входного файла:
 *
 * Входной файл состоит из одной строки, которая содержит два целых числа a и
 * b.  Для этих чисел выполняются условия -10^9 <= a <= 10^9,
 * -10^9 <= b <= 10^9.
 *
 * Формат выходного файла:
 *
 * В выходной файл выведите единственное целое число -- результат сложения
 * a + b.
 *
 * Пример 1:
 *
 * input.txt:
 *   23 11
 * output.txt:
 *   34
 *
 * Пример 2:
 *
 * input.txt:
 *   -100 1
 * output.txt:
 *   -99
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

object APlusB {
  def main(args: Array[String]): Unit = {
    val (a, b) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      (in.nextLong(), in.nextLong())
    }

    val result = a + b

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(result)
    }
  }
}
