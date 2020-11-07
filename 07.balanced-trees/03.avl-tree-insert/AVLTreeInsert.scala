/* Вставка в АВЛ-дерево
 *
 * Вставка в АВЛ-дерево вершины V с ключом X при условии, что такой вершины в
 * этом дереве нет, осуществляется следующим образом:
 * - находится вершина W, ребенком которой должна стать вершина V;
 * - вершина V делается ребенком вершины W;
 * - производится подъем от вершины W к корню, при этом, если какая-то из
 *   вершин несбалансирована, производится, в зависимости от значения баланса,
 *   левый или правый поворот.
 *
 * Первый этап нуждается в пояснении.  Спуск до будущего родителя вершины V
 * осуществляется, начиная от корня, следующим образом:
 *
 * - Пусть ключ текущей вершины равен Y.
 * - Если X < Y и у текущей вершины есть левый ребенок, переходим к левому
 *   ребенку.
 * - Если X < Y и у текущей вершины нет левого ребенка, то останавливаемся,
 *   текущая вершина будет родителем новой вершины.
 * - Если X > Y и у текущей вершины есть правый ребенок, переходим к правому
 *   ребенку.
 * - Если X > Y и у текущей вершины нет правого ребенка, то останавливаемся,
 *   текущая вершина будет родителем новой вершины.
 *
 * Отдельно рассматривается следующий крайний случай -- если до вставки дерево
 * было пустым, то вставка новой вершины осуществляется проще: новая вершина
 * становится корнем дерева.
 *
 * Формат входного файла:
 *
 * Входной файл содержит описание двоичного дерева, а также ключ вершины,
 * которую требуется вставить в дерево.
 *
 * В первой строке файла находится число N (0 <= N <= 2 * 10^5) -- число вершин
 * в дереве.  В последующих N строках файла находятся описания вершин дерева.
 * В (i + 1)-ой строке файла (1 <= i <= N) находится описание i-ой вершины,
 * состоящее из трех чисел K_i, L_i, R_i, разделенных пробелами -- ключа в i-ой
 * вершине (|K_i| <= 10^9), номера левого ребенка i-ой вершины (i < L_i <= N
 * или L_i = 0, если левого ребенка нет) и номера правого ребенка i-ой вершины
 * (i < R_i <= N или R_i = 0, если правого ребенка нет).
 *
 * Все ключи различны.  Гарантируется, что данное дерево является корректным
 * АВЛ-деревом.
 *
 * В последней строке содержится число X (|X| <= 10^9) -- ключ вершины, которую
 * требуется вставить в дерево.  Гарантируется, что такой вершины в дереве нет.
 *
 * Формат выходного файла:
 *
 * Выведите в том же формате дерево после осуществления операции вставки.
 * Нумерация вершин может быть произвольной при условии соблюдения формата.
 *
 * Пример:
 *
 * input.txt:
 *   2
 *   3 0 2
 *   4 0 0
 *   5
 * output.txt:
 *   3
 *   4 2 3
 *   3 0 0
 *   5 0 0
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

sealed trait Tree {
  /* `id` -- уникальный идентификатор узла.  Введен для того, чтобы можно было
   * сохранять ассоциированные с узлом данные в обычном массиве, в частности
   * индексы в массиве `indices` в классе `TreePrinter`.
   */

  def id: Int
  def height: Int
  def balance: Int
  def size: Int

  def insert(key: Int): Tree =
    insertNode(new Node(size + 1, key, Leaf, Leaf))

  def insertNode(node: Node): Node
}

case class Node(val id: Int, val key: Int, val left: Tree, val right: Tree) extends Tree {
  val height = 1 + Math.max(left.height, right.height)

  def balance = right.height - left.height

  def size = 1 + left.size + right.size

  def insertNode(node: Node): Node =
    if (node.key < key)
      new Node(id, key, left.insertNode(node), right).rebalance
    else if (node.key > key)
      new Node(id, key, left, right.insertNode(node)).rebalance
    else
      this

  def rebalance: Node =
    if (balance == 2)
      rotateLeft
    else if (balance == -2)
      rotateRight
    else
      this

  def rotateLeft: Node =
    if (right.balance != -1)
      rotateLeft1
    else
      rotateLeft2

  def rotateRight: Node =
    if (left.balance != 1)
      rotateRight1
    else
      rotateRight2

  def rotateLeft1: Node = {
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

  def rotateLeft2: Node = {
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

  def rotateRight1: Node = {
    val a = this

    left match {
      case b@Node(_, _, _, _) =>
        new Node(
          b.id,
          b.key,
          b.left,
          new Node(a.id, a.key, b.right, a.right),
        )
      case Leaf =>
        throw new RuntimeException("rotateRight1: invalid node")
    }
  }

  def rotateRight2: Node = {
    val a = this

    left match {
      case b@Node(_, _, _, _) =>
        b.right match {
          case c@Node(_, _, _, _) =>
            new Node(
              c.id,
              c.key,
              new Node(b.id, b.key, b.left, c.left),
              new Node(a.id, a.key, c.right, a.right),
            )
          case Leaf =>
            throw new RuntimeException("rotateRight2: invalid node")
        }
      case Leaf =>
        throw new RuntimeException("rotateRight2: invalid node")
    }
  }
}

case object Leaf extends Tree {
  def id = 0
  def size = 0
  def height = -1
  def balance = 0

  def insertNode(node: Node): Node = node
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

object AVLTreeInsert {
  def main(args: Array[String]): Unit = {
    val (tree, x) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      (TreeReader.inputTree(in), in.next().toInt)
    }

    val updatedTree = tree.insert(x)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      new TreePrinter(out, updatedTree.size + 1).print(updatedTree)
    }
  }
}
