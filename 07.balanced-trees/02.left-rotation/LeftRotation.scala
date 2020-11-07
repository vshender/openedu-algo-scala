/* Делаю я левый поворот...
 *
 * Для балансировки АВЛ-дерева при операциях вставки и удаления производятся
 * /левые/ и /правые/ повороты.  Левый поворот в вершине производится, когда
 * баланс этой вершины больше 1, аналогично, правый поворот производится при
 * балансе, меньшем −1.
 *
 * Существует два разных левых (как, разумеется, и правых) поворота: /большой/
 * и /малый/ левый поворот.
 *
 * Малый левый поворот осуществляется следующим образом:
 *
 * [problems3x.png]
 *
 * Заметим, что если до выполнения малого левого поворота был нарушен баланс
 * только корня дерева, то после его выполнения все вершины становятся
 * сбалансированными, за исключением случая, когда у правого ребенка корня
 * баланс до поворота равен −1.  В этом случае вместо малого левого поворота
 * выполняется большой левый поворот, который осуществляется так:
 *
 * [problems4x.png]
 *
 * Дано дерево, в котором баланс корня равен 2.  Сделайте левый поворот.
 *
 * Формат входного файла:
 *
 * Входной файл содержит описание двоичного дерева.  В первой строке файла
 * находится число N (3 <= N <= 2 * 10^5) -- число вершин в дереве.  В
 * последующих N строках файла находятся описания вершин дерева.  В (i + 1)-ой
 * строке файла (1 <= i <= N) находится описание i-ой вершины, состоящее из
 * трех чисел K_i, L_i, R_i, разделенных пробелами -- ключа в i-ой вершине
 * (|K_i| <= 10^9), номера левого ребенка i-ой вершины (i < L_i <= N или
 * L_i = 0, если левого ребенка нет) и номера правого ребенка i-ой вершины
 * (i < R_i <= N или R_i = 0, если правого ребенка нет).
 *
 * Все ключи различны.  Гарантируется, что данное дерево является деревом
 * поиска.  Баланс корня дерева (вершины с номером 1) равен 2, баланс всех
 * остальных вершин находится в пределах от -1 до 1.
 *
 * Формат выходного файла:
 *
 * Выведите в том же формате дерево после осуществления левого поворота.
 * Нумерация вершин может быть произвольной при условии соблюдения формата.
 * Так, номер вершины должен быть меньше номера ее детей.
 *
 * Пример:
 *
 * input.txt:
 *   7
 *   -2 7 2
 *   8 4 3
 *   9 0 0
 *   3 6 5
 *   6 0 0
 *   0 0 0
 *   -7 0 0
 * output.txt:
 *   7
 *   3 2 3
 *   -2 4 5
 *   8 6 7
 *   -7 0 0
 *   0 0 0
 *   6 0 0
 *   9 0 0
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

sealed trait Tree {
  /* `id` -- уникальный идентификатор узла.  Введен для того, чтобы можно было
   * сохранять ассоциированные с узлом данные в обычном массиве, в частности
   * индексы в массиве `indices` в классе `TreePrinter`.  Решение с
   * хэш-таблицей, где в качестве ключа использовались сами узлы, не проходило
   * из-за ограничений по времени.
   */

  def id: Int
  def height: Int
  def balance: Int
  def size: Int
  def rotateLeft: Tree
}

case class Node(val id: Int, val key: Int, val left: Tree, val right: Tree) extends Tree {
  val height = 1 + Math.max(left.height, right.height)

  def balance = right.height - left.height

  def size = 1 + left.size + right.size

  def rotateLeft: Tree =
    if (right.balance != -1)
      rotateLeft1
    else
      rotateLeft2

  def rotateLeft1: Tree = {
    val a = this

    right match {
      case b@Node(_, _, _, _) =>
        new Node(
          b.id,
          b.key,
          new Node(a.id, a.key, a.left, b.left),
          b.right,
        )
      case Leaf =>
        throw new RuntimeException("rotateLeft1: invalid node")
    }
  }

  def rotateLeft2: Tree = {
    val a = this

    right match {
      case b@Node(_, _, _, _) =>
        b.left match {
          case c@Node(_, _, _, _) =>
            new Node(
              c.id,
              c.key,
              new Node(a.id, a.key, a.left, c.left),
              new Node(b.id, b.key, c.right, b.right),
            )
          case Leaf =>
            throw new RuntimeException("rotateLeft2: invalid node")
        }
      case Leaf =>
        throw new RuntimeException("rotateLeft2: invalid node")
    }
  }
}

case object Leaf extends Tree {
  def id = 0
  def height = -1
  def balance = 0
  def size = 0

  def rotateLeft: Tree = this
}

object TreeReader {
  def makeTree(nodes: Array[(Int, Int, Int)], i: Int): Tree = {
    if (i >= 0) {
      val (k, l, r) = nodes(i)
      new Node(i + 1, k, makeTree(nodes, l), makeTree(nodes, r))
    } else
      Leaf
  }

  def inputTree(in: Scanner): Tree = {
    val n = in.next().toInt
    val nodes = Array.fill[(Int, Int, Int)](n)((
      in.next().toInt,
      in.next().toInt - 1,
      in.next().toInt - 1,
    ))
    if (n > 0) makeTree(nodes, 0) else Leaf
  }
}

class TreePrinter(val out: PrintWriter, val n: Int) {
  val indices = new Array[Int](n)

  def calcIndices(tree: Tree, index: Int): Int = tree match {
    case node@Node(_, _, _, _) =>
      indices(node.id) = (index + 1)
      val index2 = calcIndices(node.left, index + 1)
      val index3 = calcIndices(node.right, index2)
      index3
    case Leaf =>
      index
  }

  def printNode(tree: Tree): Unit = tree match {
    case node@Node(_, _, _, _) =>
      out.printf("%d %d %d\n", node.key, indices(node.left.id), indices(node.right.id))
      printNode(node.left)
      printNode(node.right)
    case Leaf =>
      ()
  }

  def print(tree: Tree) = {
    indices(Leaf.id) = 0
    calcIndices(tree, 0)

    out.println(tree.size)
    printNode(tree)
  }
}

object LeftRotation {
  def main(args: Array[String]): Unit = {
    val tree = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      TreeReader.inputTree(in)
    }

    val updatedTree = tree.rotateLeft

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      new TreePrinter(out, updatedTree.size + 1).print(updatedTree)
    }
  }
}
