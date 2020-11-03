/* Гирлянда
 *
 * Гирлянда состоит из n лампочек на общем проводе.  Один её конец закреплен
 * на заданной высоте A мм (h_1 = A).  Благодаря силе тяжести гирлянда
 * прогибается: высота каждой неконцевой лампы на 1 мм меньше, чем средняя
 * высота ближайших соседей (h_i = (h_{i-1} + h_{i+1}) / 2 - 1 для 1 < i < n).
 * Требуется найти минимальную высоту второго конца B (B = h_n) при условии,
 * что лишь одна лампочка может касаться земли, а для остальных выполняется
 * условие h_i > 0.
 *
 * Подсказка: для решения этой задачи можно использовать двоичный поиск (метод
 * дихотомии).
 *
 * [garland.png]
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится два числа n и A (3 <= n <= 1000,
 * n -- целое, 10 <= A <= 1000, A -- вещественное и дано не более чем с тремя
 * знаками после десятичной точки).
 *
 * Формат выходного файла:
 *
 * Выведите одно вещественное число B -- минимальную высоту второго конца.  Ваш
 * ответ будет засчитан, если он будет отличаться от правильного не более, чем
 * на 10^{-6}.
 *
 * Примеры:
 *
 * input.txt:
 *   8 15
 * output.txt:
 *   9.75
 *
 * input.txt:
 *   692 532.81
 * output.txt:
 *   446113.34434782615
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.math.BigDecimal
import scala.util.Using

object Garland {
  val eps = 1e-9

  def search(n: Int, a: Double): Double = {
    def searchRec(l: Double, r: Double): Double = {
      // Будем искать высоту второй лампочки, при которой самая нижняя лампочка
      // будет иметь нулевую длину.
      val m = (l + r) / 2
      val (_, h, hmin) = (3 to n).foldLeft(a, m, m)((state, _) => {
        val (hpp, hp, hmin) = state
        val h = 2 * (hp + 1) - hpp
        (hp, h, if (h < hmin) h else hmin)
      })

      if (hmin < -eps)
        // Самая нижняя лампочка имеет отрицательную высоту, подвесим вторую лампочку повыше.
        searchRec(m, r)
      else if (hmin > eps)
        // Самая нижняя лампочка имеет положительную высоту, подвесим вторую лампочку пониже.
        searchRec(l, m)
      else
        h
    }

    searchRec(0, a)
  }

  def main(args: Array[String]): Unit = {
    val (n, a) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      (in.nextInt(), in.nextDouble())
    }

    val b = search(n, a)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(BigDecimal(b).setScale(6, BigDecimal.RoundingMode.HALF_UP).toDouble)
    }
  }
}
