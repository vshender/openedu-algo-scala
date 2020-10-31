/* Сортировка пугалом
 *
 * "Сортировка пугалом" -- это давно забытая народная потешка, которую
 * восстановили по летописям специалисты платформы "Открытое образование"
 * специально для этого курса.
 *
 * Участнику под верхнюю одежду продевают деревянную палку, так что у него
 * оказываются растопырены руки, как у огородного пугала.  Перед ним ставятся n
 * матрёшек в ряд.  Из-за палки единственное, что он может сделать -- это взять
 * в руки две матрешки на расстоянии k друг от друга (то есть i-ую и
 * (i + k)-ую), развернуться и поставить их обратно в ряд, таким образом
 * поменяв их местами.
 *
 * Задача участника -- расположить матрёшки по неубыванию размера. Может ли он
 * это сделать?
 *
 * Формат входного файла:
 *
 * В первой строчке содержатся числа n и k (1 <= n, k <= 10^5) -- число
 * матрёшек и размах рук.
 *
 * Во второй строчке содержится n целых чисел, которые по модулю не превосходят
 * 10^9 -- размеры матрёшек.
 *
 * Формат выходного файла:
 *
 * Выведите "YES", если возможно отсортировать матрёшки по неубыванию размера,
 * и "NO" в противном случае.
 *
 * Примеры:
 *
 * input.txt:
 *   3 2
 *   2 1 3
 * output.txt:
 *   NO
 *
 * input.txt:
 *   5 3
 *   1 5 3 4 1
 * output.txt:
 *   YES
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.util.{Using, Sorting}
import scala.collection.mutable.Map

object ScarecrowSort {
  // Это решение, основанное на модификации сортировки "пузырьком", не проходит
  // ограничение по времени работы.
  //
  // def swap(nums: Array[Int], i: Int, j: Int): Unit = {
  //   val t = nums(i)
  //   nums(i) = nums(j)
  //   nums(j) = t
  // }
  //
  // def scarecrowSort(nums: Array[Int], k: Int): Boolean = {
  //   val n = nums.length
  //
  //   for (i <- 0 until (n - 1) / k)
  //     for (j <- 0 until n - k * (i + 1))
  //       if (nums(j) > nums(j + k))
  //         swap(nums, j, j + k)
  //
  //   for (i <- 1 until n)
  //     if (nums(i - 1) > nums(i))
  //       return false
  //
  //   true
  // }

  case class Element(val value: Int, var key: Int) extends Ordered[Element] {
    // https://stackoverflow.com/questions/19345030/easy-idiomatic-way-to-define-ordering-for-a-simple-case-class
    import scala.math.Ordered.orderingToOrdered
    def compare(that: Element): Int = (value, key) compare (that.value, that.key)
  }

  def scarecrowSort(nums: Array[Int], k: Int): Boolean = {
    val n = nums.length
    val elements = Array.tabulate[Element](n)((i: Int) => Element(nums(i), i % k))
    var ki = Map[Int, Int]()

    Sorting.quickSort(elements)

    for (i <- 0 until n) {
      if (i == 0 || elements(i).value != elements(i - 1).value)
        ki.clear()

      val j = ki.getOrElse(elements(i).key, if (elements(i).key < i % k) 1 else 0)
      elements(i).key += k * j
      ki(elements(i).key) = j + 1
    }

    Sorting.quickSort(elements)

    for (i <- 0 until n)
      if (elements(i).key % k != i % k)
        return false

    true
  }

  def main(args: Array[String]): Unit = {
    val (nums, k) = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      val (n, k) = (in.nextInt(), in.nextInt())
      val nums = Array.fill[Int](n)(in.nextInt())
      (nums, k)
    }

    val canSort = scarecrowSort(nums, k)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(if (canSort) "YES" else "NO")
    }
  }
}
