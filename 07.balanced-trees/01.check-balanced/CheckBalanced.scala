/* Проверка сбалансированности
 *
 * АВЛ-дерево является сбалансированным в следующем смысле: для любой вершины
 * высота ее левого поддерева отличается от высоты ее правого поддерева не
 * больше, чем на единицу.
 *
 * Введем понятие /баланса вершины/: для вершины дерева V ее баланс B(V) равен
 * разности высоты правого поддерева и высоты левого поддерева.  Таким образом,
 * свойство АВЛ-дерева, приведенное выше, можно сформулировать следующим
 * образом: для любой ее вершины V выполняется следующее неравенство:
 *
 *   -1 <= B(V) <= 1
 *
 * Дано двоичное дерево поиска.  Для каждой его вершины требуется определить ее
 * баланс.
 *
 * Формат входного файла:
 *
 * Входной файл содержит описание двоичного дерева.  В первой строке файла
 * находится число N (1 <= N <= 2 * 10^5) -- число вершин в дереве.  В
 * последующих N строках файла находятся описания вершин дерева.  В (i + 1)-ой
 * строке файла (1 <= i <= N) находится описание i-ой вершины, состоящее из
 * трех чисел K_i, L_i, R_i, разделенных пробелами -- ключа в i-ой вершине
 * (|K_i| <= 10^9), номера левого ребенка i-ой вершины (i < L_i <= N или
 * L_i = 0, если левого ребенка нет) и номера правого ребенка i-ой вершины
 * (i < R_i <= N или R_i = 0, если правого ребенка нет).
 *
 * Все ключи различны.  Гарантируется, что данное дерево является деревом
 * поиска.
 *
 * Формат выходного файла:
 *
 * Для i-ой вершины в i-ой строке выведите одно число -- баланс данной вершины.
 *
 * Пример:
 *
 * input.txt:
 *   6
 *   -2 0 2
 *   8 4 3
 *   9 0 0
 *   3 6 5
 *   6 0 0
 *   0 0 0
 * output.txt:
 *   3
 *   -1
 *   0
 *   0
 *   0
 *   0
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

class Node(val k: Int, val l: Int, val r: Int) {}

object CheckBalanced {
  def calcHeightAndBalance(nodes: Array[Node], bs: Array[Int], i: Int): (Int, Int) = {
    if (i >= 0) {
      val (lh, lb) = calcHeightAndBalance(nodes, bs, nodes(i).l)
      val (rh, rb) = calcHeightAndBalance(nodes, bs, nodes(i).r)
      bs(i) = rh - lh
      (1 + Math.max(lh, rh), bs(i))
    } else
      (-1, 0)
  }

  def calcBalance(nodes: Array[Node]): Array[Int] = {
    val bs = new Array[Int](nodes.length)
    calcHeightAndBalance(nodes, bs, 0)
    bs
  }

  def main(args: Array[String]): Unit = {
    val nodes = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      Array.fill[Node](n)(new Node(in.nextInt(), in.nextInt() - 1, in.nextInt() - 1))
    }

    val bs = calcBalance(nodes)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      bs.foreach(out.println(_))
    }
  }
}
