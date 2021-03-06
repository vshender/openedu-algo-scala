/* Скобочная последовательность
 *
 * Последовательность A, состоящую из символов из множества "(", ")", "[" и
 * "]", назовем /правильной скобочной последовательностью/, если выполняется
 * одно из следующих утверждений:
 *
 * - A -- пустая последовательность;
 * - первый символ последовательности A -- это "(", и в этой последовательности
 *   существует такой символ ")", что последовательность можно представить как
 *   A = (B)C, где B и C -- правильные скобочные последовательности;
 * - первый символ последовательности B -- это "[", и в этой последовательности
 *   существует такой символ "]", что последовательность можно представить как
 *   A = [B]C, где B и C -- правильные скобочные последовательности.
 *
 * Так, например, последовательности "(())" и "()[]" являются правильными
 * скобочными последовательностями, а последовательности "[)" и "((" таковыми
 * не являются.
 *
 * Входной файл содержит несколько строк, каждая из которых содержит
 * последовательность символов "(", ")", "[" и "]".  Для каждой из этих строк
 * выясните, является ли она правильной скобочной последовательностью.
 *
 * Формат входного файла:
 *
 * Первая строка входного файла содержит число N (1 <= N <= 500) -- число
 * скобочных последовательностей, которые необходимо проверить.  Каждая из
 * следующих строк содержит скобочную последовательность длиной от 1 до 10^4
 * включительно.  В каждой из последовательностей присутствуют только скобки
 * указанных выше видов.
 *
 * Формат выходного файла:
 *
 * Для каждой строки входного файла (кроме первой, в которой записано число
 * таких строк) выведите в выходной файл "YES", если соответствующая
 * последовательность является правильной скобочной последовательностью, или
 * "NO", если не является.
 *
 * Пример:
 *
 * input.txt:
 *   5
 *   ()()
 *   ([])
 *   ([)]
 *   ((]]
 *   )(
 *
 * output.txt:
 *   YES
 *   YES
 *   NO
 *   NO
 *   NO
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.collection.mutable.Stack
import scala.util.Using


object Parentheses {
  def checkParSequence(seq: String): Boolean = {
    val stack = new Stack[Char]

    for (i <- 0 until seq.length) {
      val c = seq(i)
      c match {
        case '(' | '[' => stack.push(c)
        case ')' | ']' =>
          val p = try {
            stack.pop()
          } catch {
            case _: NoSuchElementException =>
              return false
          }

          if (c == ')' && p != '(' || c == ']' && p != '[')
            return false
      }
    }

    return stack.size == 0
  }

  def main(args: Array[String]): Unit = {
    Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val n = in.readLine().toInt
        for (i <- 0 until n)
          out.println(if (checkParSequence(in.readLine())) "YES" else "NO")
      }
    }
  }
}
