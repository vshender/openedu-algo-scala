/* Удаление поддеревьев
 *
 * Дано некоторое двоичное дерево поиска.  Также даны запросы на удаление из
 * него вершин, имеющих заданные ключи, причем вершины удаляются целиком вместе
 * со своими поддеревьями.
 *
 * После каждого запроса на удаление выведите число оставшихся вершин в дереве.
 *
 * В вершинах данного дерева записаны ключи -- целые числа, по модулю не
 * превышающие 10^9.  Гарантируется, что данное дерево является двоичным
 * деревом поиска, в частности, для каждой вершины дерева V выполняется
 * следующее условие:
 * - все ключи вершин из левого поддерева меньше ключа вершины V;
 * - все ключи вершин из правого поддерева больше ключа вершины V.
 *
 * *Высота дерева не превосходит 25*, таким образом, можно считать, что оно
 * сбалансировано.
 *
 * Формат входного файла:
 *
 * Входной файл содержит описание двоичного дерева и описание запросов на
 * удаление.
 *
 * В первой строке файла находится число N (1 <= N <= 2 * 10^5) -- число вершин
 * в дереве.  В последующих N строках файла находятся описания вершин дерева.
 * В (i + 1)-ой строке файла (1 <= i <= N) находится описание i-ой вершины,
 * состоящее из трех чисел K_i, L_i, R_i, разделенных пробелами -- ключа в i-ой
 * вершине (|K_i| <= 10^9), номера левого ребенка i-ой вершины (i < L_i <= N
 * или L_i = 0, если левого ребенка нет) и номера правого ребенка i-ой вершины
 * (i < R_i <= N или R_i = 0, если правого ребенка нет).
 *
 * Все ключи различны.  Гарантируется, что данное дерево является деревом
 * поиска.
 *
 * В следующей строке находится число M (1 <= M <= 2 * 10^5) -- число запросов
 * на удаление.  В следующей строке находятся M чисел, разделенных пробелами --
 * ключи, вершины с которыми (вместе с их поддеревьями) необходимо удалить.
 * Все эти числа не превосходят 10^9 по абсолютному значению.  Вершина с таким
 * ключом не обязана существовать в дереве -- в этом случае дерево изменять не
 * требуется.  Гарантируется, что корень дерева никогда не будет удален.
 *
 * Формат выходного файла:
 *
 * Выведите M строк.  На i-ой строке требуется вывести число вершин, оставшихся
 * в дереве после выполнения i-го запроса на удаление.
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
 *   4
 *   6 9 7 8
 * output.txt:
 *   5
 *   4
 *   4
 *   1
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

  def deleteNode(i: Int): Int = {
    if (nodeExists(i)) {
      val count = 1 + deleteNode(nodes(i).l) + deleteNode(nodes(i).r)
      nodes(i) = null
      count
    } else
      0
  }

  def findNode(k: Int): Option[Int] = {
    var i = 0
    while (nodeExists(i)) {
      if (k < nodes(i).k)
        i = nodes(i).l
      else if (nodes(i).k < k)
        i = nodes(i).r
      else
        return Some(i)
    }

    None
  }
}

object SubtreesDelete {
  def main(args: Array[String]): Unit = {
    Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        var n = in.nextInt()
        val tree = new Tree(n)
        for (i <- 0 until n)
          tree.addNode(i, new Node(in.nextInt(), in.nextInt() - 1, in.nextInt() - 1))

        val m = in.nextInt()
        for (i <- 0 until m) {
          val x = in.nextInt()
          tree.findNode(x) match {
            case Some(j) => n -= tree.deleteNode(j)
            case None    => null
          }

          out.println(n)
        }
      }
    }
  }
}