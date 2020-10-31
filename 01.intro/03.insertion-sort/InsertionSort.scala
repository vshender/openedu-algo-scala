/* Сортировка вставками
 *
 * Дан массив целых чисел.  Ваша задача -- отсортировать его в порядке
 * неубывания с помощью сортировки вставками.
 *
 * Сортировка вставками проходится по всем элементам массива от меньших
 * индексов к большим ("слева направо"), для каждого элемента определяет его
 * место в предшествующей ему отсортированной части массива и переносит его на
 * это место (возможно, сдвигая некоторые элементы на один индекс вправо).
 * Чтобы проконтролировать, что Вы используете именно сортировку вставками, мы
 * попросим Вас для каждого элемента массива, после того, как он будет
 * обработан, выводить его новый индекс.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится число n (1 <= n <= 1000) -- число
 * элементов в массиве.  Во второй строке находятся n различных целых чисел, по
 * модулю не превосходящих 10^9.
 *
 * Формат выходного файла:
 *
 * В первой строке выходного файла выведите n чисел.  При этом i-ое число равно
 * индексу, на который *в момент обработки его сортировкой вставками* был
 * перемещен i-ый элемент *исходного массива*.  Индексы нумеруются, начиная с
 * единицы.  Между любыми двумя числами должен стоять ровно один пробел.
 *
 * Во второй строке выходного файла выведите отсортированный массив.  Между
 * любыми двумя числами должен стоять ровно один пробел.
 *
 * Пример:
 *
 * input.txt:
 *   10
 *   1 8 4 2 3 7 5 6 9 0
 * output.txt:
 *   1 2 2 2 3 5 5 6 9 1
 *   0 1 2 3 4 5 6 7 8 9
 *
 * Комментарий к примеру:
 *
 * В примере сортировка вставками работает следующим образом:
 * - Первый элемент остается на своем месте, поэтому первое число в ответе --
 *   единица.  Отсортированная часть массива: [1].
 * - Второй элемент больше первого, поэтому он тоже остается на своем месте, и
 *   второе число в ответе -- двойка.  [1 8].
 * - Четверка меньше восьмерки, поэтому занимает второе место.  [1 4 8].
 * - Двойка занимает второе место.  [1 2 4 8].
 * - Тройка занимает третье место.  [1 2 3 4 8].
 * - Семерка занимает пятое место.  [1 2 3 4 7 8].
 * - Пятерка занимает пятое место.  [1 2 3 4 5 7 8].
 * - Шестерка занимает шестое место.  [1 2 3 4 5 6 7 8].
 * - Девятка занимает девятое место.  [1 2 3 4 5 6 7 8 9].
 * - Ноль занимает первое место.  [0 1 2 3 4 5 6 7 8 9].
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.Using

object InsertionSort {
  def sort[T](array: Array[T], debug: Int => Unit = null)(implicit ordering: Ordering[T]): Unit = {
    val debugOpt = if (debug eq null) None else Some(debug)

    debugOpt.foreach(_(0))

    for (i <- 1 until array.length) {
      val t = array(i)
      var j = i
      while (j > 0 && ordering.gt(array(j - 1), t)) {
        array(j) = array(j - 1)
        j -= 1
      }
      array(j) = t

      debugOpt.foreach(_(j))
    }
  }

  def main(args: Array[String]): Unit = {
    val array = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val n = in.nextInt()
      Array.fill[Int](n)(in.nextInt())
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      sort(array, x => out.printf("%d ", x + 1))
      out.println()

      array.foreach(x => out.printf("%d ", x))
      out.println()
    }
  }
}
