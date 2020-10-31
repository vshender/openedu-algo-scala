/* Задача "a + b^2"
 *
 * В данной задаче требуется вычислить значение выражения a + b^2.
 *
 * Формат входного файла:
 *
 * Входной файл состоит из одной строки, которая содержит два целых числа a и
 * b.  Для этих чисел выполняются условия -10^9 <= a <= 10^9,
 * -10^9 <= b <= 10^9.
 *
 * Формат выходного файла:
 *
 * В выходной файл выведите единственное целое число -- результат вычисления
 * выражения a + b^2.
 *
 * Пример 1:
 *
 * input.txt:
 *   23 11
 * output.txt:
 *   144
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

object APlusSquareB {
  def main(args: Array[String]): Unit = {
    val (a, b) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      (in.nextLong(), in.nextLong())
    }

    val result = a + b * b

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(result)
    }
  }
}
