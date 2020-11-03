/* Высота дерева
 *
 * Высотой дерева называется максимальное число вершин дерева в цепочке,
 * начинающейся в корне дерева, заканчивающейся в одном из его листьев, и не
 * содержащей никакую вершину дважды.
 *
 * Так, высота дерева, состоящего из единственной вершины, равна единице.
 * Высота пустого дерева (да, бывает и такое!) равна нулю.  Высота дерева,
 * изображенного на рисунке, равна четырем.
 *
 * [problems1x.png]
 *
 * Дано двоичное дерево поиска.  В вершинах этого дерева записаны ключи --
 * целые числа, по модулю не превышающие 10^9.  Для каждой вершины дерева V
 * выполняется следующее условие:
 * - все ключи вершин из левого поддерева меньше ключа вершины V;
 * - все ключи вершин из правого поддерева больше ключа вершины V.
 *
 * Найдите высоту данного дерева.
 *
 * Формат входного файла:
 *
 * Входной файл содержит описание двоичного дерева.  В первой строке файла
 * находится число N (0 <= N <= 2 * 10^5) -- число вершин в дереве.  В
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
 * Выведите одно целое число -- высоту дерева.
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
 *   4
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

class Node(val k: Int, val l: Int, val r: Int) {
  def nodeExists(i: Int): Boolean = i >= 0

  def height(nodes: Array[Node]): Int =
    1 + Math.max(
      if (nodeExists(l)) nodes(l).height(nodes) else 0,
      if (nodeExists(r)) nodes(r).height(nodes) else 0,
    )
}

object TreeHeight {
  def treeHeight(nodes: Array[Node]): Int =
    if (nodes.length > 0) nodes(0).height(nodes) else 0

  def main(args: Array[String]): Unit = {
    val nodes = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      Array.fill[Node](n)(new Node(in.nextInt(), in.nextInt() - 1, in.nextInt() - 1))
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(treeHeight(nodes))
    }
  }
}