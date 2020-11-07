/* Удаление из АВЛ-дерева
 *
 * Удаление из АВЛ-дерева вершины с ключом X, при условии ее наличия,
 * осуществляется следующим образом:
 * - путем спуска от корня и проверки ключей находится V -- удаляемая вершина;
 * - если вершина V -- лист (то есть, у нее нет детей):
 *   - удаляем вершину;
 *   - поднимаемся к корню, начиная с бывшего родителя вершины V, при этом если
 *     встречается несбалансированная вершина, то производим поворот.
 * - если у вершины V не существует левого ребенка:
 *   - следовательно, баланс вершины равен единице и ее правый ребенок -- лист;
 *   - заменяем вершину V ее правым ребенком;
 *   - поднимаемся к корню, производя, где необходимо, балансировку.
 * - иначе:
 *   - находим R -- самую правую вершину в левом поддереве;
 *   - переносим ключ вершины R в вершину V;
 *   - удаляем вершину R (у нее нет правого ребенка, поэтому она либо лист,
 *     либо имеет левого ребенка, являющегося листом);
 *   - поднимаемся к корню, начиная с бывшего родителя вершины R, производя
 *     балансировку.
 *
 * Исключением является случай, когда производится удаление из дерева,
 * состоящего из одной вершины -- корня.  Результатом удаления в этом случае
 * будет пустое дерево.
 *
 * Указанный алгоритм не является единственно возможным, но мы просим Вас
 * реализовать именно его, так как тестирующая система проверяет точное
 * равенство получающихся деревьев.
 *
 * Формат входного файла:
 *
 * Входной файл содержит описание двоичного дерева, а также ключ вершины,
 * которую требуется удалить из дерева.
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
 * требуется вставить в дерево.  Гарантируется, что такая вершина в дереве
 * существует.
 *
 * Формат выходного файла:
 *
 * Выведите в том же формате дерево после осуществления операции удаления.
 * Нумерация вершин может быть произвольной при условии соблюдения формата.
 *
 * Пример:
 *
 * input.txt:
 *   3
 *   4 2 3
 *   3 0 0
 *   5 0 0
 *   4
 * output.txt:
 *   2
 *   3 0 2
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

  def delete(key: Int): Tree
  def rebalance: Tree
}

case class Node(
  val id: Int,
  val key: Int,
  val left: Tree = Leaf,
  val right: Tree = Leaf,
) extends Tree {
  val height = 1 + Math.max(left.height, right.height)

  def balance = right.height - left.height

  def size = 1 + left.size + right.size

  def delete(key: Int): Tree = {
    val updatedNode = (
      if (key < this.key)
        new Node(id, this.key, left.delete(key), right)
      else if (key > this.key)
        new Node(id, this.key, left, right.delete(key))
      else
        this match {
          case Node(_, _, Leaf, _)                  => right
          case Node(_, _, left@Node(_, _, _, _), _) =>
            val rightmost = left.rightmostChild
            new Node(id, rightmost.key, left.delete(rightmost.key), right)
        }
    )
    updatedNode.rebalance
  }

  def rightmostChild: Node = right match {
    case right@Node(_, _, _, _) => right.rightmostChild
    case Leaf                   => this
  }

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

  def delete(key: Int): Tree = this
  def rebalance: Tree = this
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

object AVLTreeDelete {
  def main(args: Array[String]): Unit = {
    val (tree, x) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      (TreeReader.inputTree(in), in.next().toInt)
    }

    val updatedTree = tree.delete(x)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      new TreePrinter(out, tree.size + 1).print(updatedTree)
    }
  }
}
