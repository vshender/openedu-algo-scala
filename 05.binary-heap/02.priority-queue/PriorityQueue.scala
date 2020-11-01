/* Очередь с приоритетами
 *
 * Реализуйте очередь с приоритетами.  Ваша очередь должна поддерживать
 * следующие операции: добавить элемент, извлечь минимальный элемент, уменьшить
 * элемент, добавленный во время одной из операций.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится число n (1 <= n <= 10^6) -- число
 * операций с очередью.
 *
 * Следующие n строк содержат описание операций с очередью, по одному описанию
 * в строке.  Операции могут быть следующими:
 * - A x -- требуется добавить элемент x в очередь.
 * - X -- требуется удалить из очереди минимальный элемент и вывести его в
 *   выходной файл.  Если очередь пуста, в выходной файл требуется вывести
 *   звездочку "∗".
 * - D x y -- требуется заменить значение элемента, добавленного в очередь
 *   операцией A в строке входного файла номер x + 1, на y.  Гарантируется, что
 *   в строке x + 1 действительно находится операция A, что этот элемент не был
 *   ранее удален операцией X, и что y меньше, чем предыдущее значение этого
 *   элемента.
 *
 * В очередь помещаются и извлекаются только целые числа, не превышающие по
 * модулю 10^9.
 *
 * Формат выходного файла:
 *
 * Выведите последовательно результат выполнения всех операций X, по одному в
 * каждой строке выходного файла.  Если перед очередной операцией X очередь
 * пуста, выведите вместо числа звездочку "∗".
 *
 * Пример:
 *
 * input.txt
 *   8
 *   A 3
 *   A 4
 *   A 2
 *   X
 *   D 2 1
 *   X
 *   X
 *   X
 * output.txt:
 *   2
 *   1
 *   3
 *   *
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.util.Using

case class Element(var value: Int, val ordnum: Int) extends Ordered[Element] {
  def compare(that: Element): Int = this.value compare that.value
}

class PriorityQueue(val size: Int) {
  val pqueue = new Array[Element](size)
  var len = 0

  // Индекс элемента по номеру операции, в которой он был добавлен.
  val elmIdx = new Array[Int](size)

  def parentIdx(i: Int): Int =
    (i - 1) >>> 1

  def leftChildIdx(i: Int): Int =
    (i << 1) + 1

  def rightChildIdx(i: Int): Int =
    (i << 1) + 2

  def swap(i: Int, j: Int): Unit = {
    val tmp = pqueue(i)
    pqueue(i) = pqueue(j)
    pqueue(j) = tmp

    elmIdx(pqueue(i).ordnum) = i
    elmIdx(pqueue(j).ordnum) = j
  }

  def heapify(i: Int): Unit = {
    var smallestElmIdx = i
    val leftIdx = leftChildIdx(i)
    val rightIdx = rightChildIdx(i)
    if (leftIdx < len && pqueue(smallestElmIdx) > pqueue(leftIdx))
      smallestElmIdx = leftIdx
    if (rightIdx < len && pqueue(smallestElmIdx) > pqueue(rightIdx))
      smallestElmIdx = rightIdx
    if (smallestElmIdx != i) {
      swap(i, smallestElmIdx)
      heapify(smallestElmIdx)
    }
  }

  def moveUp(i: Int): Unit = {
    var idx = i
    while (idx > 0 && pqueue(parentIdx(idx)) > pqueue(idx)) {
      swap(idx, parentIdx(idx))
      idx = parentIdx(idx)
    }
  }

  def insert(x: Int, ordnum: Int): Unit = {
    if (len == size)
      throw new RuntimeException("queue overflow")

    pqueue(len) = new Element(x, ordnum)
    elmIdx(ordnum) = len
    moveUp(len)
    len += 1
  }

  def extractMin(): Option[Int] = {
    if (len == 0)
      return None

    val minElm = Some(pqueue(0).value)

    len -= 1
    pqueue(0) = pqueue(len)
    elmIdx(pqueue(0).ordnum) = 0
    heapify(0)

    minElm
  }

  def decrease(i: Int, x: Int): Unit = {
    pqueue(elmIdx(i)).value = x
    moveUp(elmIdx(i))
  }
}

object PriorityQueue {
  def main(args: Array[String]): Unit = {
    Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val n = in.readLine().toInt
        val pqueue = new PriorityQueue(n)

        for (i <- 0 until n) {
          val cmd = in.readLine().split(" ")
          cmd(0) match {
            case "A" =>
              val x = cmd(1).toInt
              pqueue.insert(x, i)
            case "X" =>
              pqueue.extractMin() match {
                case Some(num) => out.println(num)
                case None      => out.println("*")
              }
            case "D" =>
              val x = cmd(1).toInt
              val y = cmd(2).toInt
              pqueue.decrease(x - 1, y)
          }
        }
      }
    }
  }
}
