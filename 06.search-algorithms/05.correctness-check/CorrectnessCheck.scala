/* Проверка корректности
 *
 * Свойство двоичного дерева поиска можно сформулировать следующим образом: для
 * каждой вершины дерева V выполняется следующее условие:
 * - все ключи вершин из левого поддерева меньше ключа вершины V;
 * - все ключи вершин из правого поддерева больше ключа вершины V.
 *
 * Дано двоичное дерево.  Проверьте, выполняется ли для него свойство двоичного
 * дерева поиска.
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
 * Формат выходного файла:
 *
 * Выведите "YES", если данное во входном файле дерево является двоичным
 * деревом поиска, и "NO", если не является.
 *
 * Примеры:
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
 *   YES
 * input.txt:
 *   0
 * output.txt:
 *   YES
 * input.txt:
 *   3
 *   5 2 3
 *   6 0 0
 *   4 0 0
 * output.txt:
 *   NO
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

class Node(val k: Int, val l: Int, val r: Int) {}

class Tree(val size: Int) {
  val nodes = new Array[Node](size)

  def nodeExists(i: Int): Boolean =
    i >= 0 && !(nodes(i) eq null)

  def addNode(i: Int, node: Node): Unit =
    nodes(i) = node

  // Данное решение не проходит по времени последние тесты, поэтому пришлось
  // написать более императивное.
  //
  // def checkNode(i: Int): (Boolean, Option[Int], Option[Int]) = {
  //   if (nodeExists(i)) {
  //     val (lc, lmin, lmax) = checkNode(nodes(i).l)
  //     val (rc, rmin, rmax) = checkNode(nodes(i).r)
  //     (
  //       lc && rc
  //         && lmax.map(_ < nodes(i).k).getOrElse(true)
  //         && rmin.map(nodes(i).k < _).getOrElse(true),
  //       lmin.orElse(Some(nodes(i).k)),
  //       rmax.orElse(Some(nodes(i).k)),
  //     )
  //   } else
  //     (true, None, None)
  // }

  def checkNode(i: Int): (Boolean, Int, Int) = {
    var min = nodes(i).k
    var max = nodes(i).k

    if (nodeExists(nodes(i).l)) {
      val (lc, lmin, lmax) = checkNode(nodes(i).l)
      if (!lc || lmax >= nodes(i).k)
        return (false, 0, 0)
      min = lmin
    }

    if (nodeExists(nodes(i).r)) {
      val (rc, rmin, rmax) = checkNode(nodes(i).r)
      if (!rc || nodes(i).k >= rmin)
        return (false, 0, 0)
      max = rmax
    }

    (true, min, max)
  }

  def check(): Boolean =
    if (size > 0) checkNode(0)._1 else true
}

object CorrectnessCheck {
  def main(args: Array[String]): Unit = {
    val tree = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      var n = in.nextInt()
      val tree = new Tree(n)
      for (i <- 0 until n)
        tree.addNode(i, new Node(in.nextInt(), in.nextInt() - 1, in.nextInt() - 1))
      tree
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(if (tree.check()) "YES" else "NO")
    }
  }
}
