/* Упорядоченное множество на АВЛ-дереве
 *
 * Если Вы сдали все предыдущие задачи, Вы уже можете написать эффективную
 * реализацию упорядоченного множества на АВЛ-дереве.  Сделайте это.
 *
 * Для проверки того, что множество реализовано именно на АВЛ-дереве, мы просим
 * Вас выводить баланс корня после каждой операции вставки и удаления.
 *
 * Операции вставки и удаления требуется реализовать точно так же, как это было
 * сделано в предыдущих двух задачах, потому что в ином случае баланс корня
 * может отличаться от требуемого.
 *
 * Формат входного файла:
 *
 * В первой строке файла находится число N (1 <= N <= 2 * 10^5) -- число
 * операций над множеством.  Изначально множество пусто.  В каждой из
 * последующих N строк файла находится описание операции.
 *
 * Операции бывают следующих видов:
 * - A x -- вставить число x в множество.  Если число x там уже содержится,
 *   множество изменять не следует.
 * - D x -- удалить число x из множества.  Если числа x нет в множестве,
 *   множество изменять не следует.
 * - C x -- проверить, есть ли число x в множестве.
 *
 * Формат выходного файла:
 *
 * Для каждой операции вида C x выведите Y, если число x содержится в
 * множестве, и N, если не содержится.
 *
 * Для каждой операции вида A x или D x выведите баланс корня дерева после
 * выполнения операции.  Если дерево пустое (в нем нет вершин), выведите 0.
 *
 * Вывод для каждой операции должен содержаться на отдельной строке.
 *
 * Пример:
 *
 * input.txt:
 *   6
 *   A 3
 *   A 4
 *   A 5
 *   C 4
 *   C 6
 *   D 5
 * output.txt:
 *   0
 *   1
 *   0
 *   Y
 *   N
 *   -1
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

sealed trait Tree {
  def height: Int
  def balance: Int
  def size: Int

  def insert(key: Int): Tree =
    insertNode(new Node(key, Leaf, Leaf))
  def contains(key: Int): Boolean
  def insertNode(node: Node): Node
  def delete(key: Int): Tree
  def rebalance: Tree
}

case class Node(val key: Int, val left: Tree, val right: Tree) extends Tree {
  val height = 1 + Math.max(left.height, right.height)

  val balance = right.height - left.height

  val size = 1 + left.size + right.size

  def contains(key: Int): Boolean =
    if (key < this.key)
      left.contains(key)
    else if (key > this.key)
      right.contains(key)
    else
      true

  def insertNode(node: Node): Node =
    if (node.key < key)
      new Node(key, left.insertNode(node), right).rebalance
    else if (node.key > key)
      new Node(key, left, right.insertNode(node)).rebalance
    else
      this

  def delete(key: Int): Tree = {
    val updatedNode = (
      if (key < this.key)
        new Node(this.key, left.delete(key), right)
      else if (key > this.key)
        new Node(this.key, left, right.delete(key))
      else
        this match {
          case Node(_, Leaf, _)               => right
          case Node(_, left@Node(_, _, _), _) =>
            val rightmost = left.rightmostChild
            new Node(rightmost.key, left.delete(rightmost.key), right)
        }
    )
    updatedNode.rebalance
  }

  def rightmostChild: Node = right match {
    case right@Node(_, _, _) => right.rightmostChild
    case Leaf                => this
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
      case b@Node(_, _, _) =>
        new Node(
          b.key,
          new Node(a.key, a.left, b.left),
          b.right,
        )
      case Leaf =>
        throw new RuntimeException("rotateLeft1: invalid node")
    }
  }

  def rotateLeft2: Node = {
    val a = this

    right match {
      case b@Node(_, _, _) =>
        b.left match {
          case c@Node(_, _, _) =>
            new Node(
              c.key,
              new Node(a.key, a.left, c.left),
              new Node(b.key, c.right, b.right),
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
      case b@Node(_, _, _) =>
        new Node(
          b.key,
          b.left,
          new Node(a.key, b.right, a.right),
        )
      case Leaf =>
        throw new RuntimeException("rotateRight1: invalid node")
    }
  }

  def rotateRight2: Node = {
    val a = this

    left match {
      case b@Node(_, _, _) =>
        b.right match {
          case c@Node(_, _, _) =>
            new Node(
              c.key,
              new Node(b.key, b.left, c.left),
              new Node(a.key, c.right, a.right),
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
  val id = 0
  val size = 0
  val height = -1
  val balance = 0

  def contains(key: Int): Boolean = false
  def insertNode(node: Node): Node = node
  def delete(key: Int): Tree = this
  def rebalance: Tree = this
}

object OrderedSet {
  def main(args: Array[String]): Unit = {
    var tree: Tree = Leaf

    Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val n = in.next().toInt
        for (i <- 0 until n) {
          val cmd = in.next()
          val x = in.next().toInt

          cmd match {
            case "A" =>
              tree = tree.insert(x)
              out.println(tree.balance)
            case "D" =>
              tree = tree.delete(x)
              out.println(tree.balance)
            case "C" =>
              out.println(if (tree.contains(x)) "Y" else "N")
          }
        }
      }
    }
  }
}
