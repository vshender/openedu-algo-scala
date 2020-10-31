/* Цифровая сортировка
 *
 * Дано n строк, выведите их порядок после k фаз цифровой сортировки.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержатся числа n -- число строк, m --
 * их длина и k -- число фаз цифровой сортировки (1 <= n <= 10^6,
 * 1 <= k <= m <= 10^6, n * m <= 5 * 10^7).  Далее находится описание строк,
 * *но в нетривиальном формате*.  Так, i-ая строка (1 <= i <= n) записана в
 * i-ых символах второй, ..., (m + 1)-ой строк входного файла. Иными словами,
 * строки написаны по вертикали.  *Это сделано специально, чтобы сортировка
 * занимала меньше времени*.
 *
 * Строки состоят из строчных латинских букв: от символа "a" до символа "z"
 * включительно.  В таблице символов ASCII все эти буквы располагаются подряд и
 * в алфавитном порядке, код буквы "a" равен 97, код буквы "z" равен 122.
 *
 * Формат выходного файла:
 *
 * Выведите номера строк в том порядке, в котором они будут после k фаз
 * цифровой сортировки.
 *
 * Примеры:
 *
 * input.txt:
 *   3 3 1
 *   bab
 *   bba
 *   baa
 * output.txt:
 *   2 3 1
 *
 * input.txt:
 *   3 3 2
 *   bab
 *   bba
 *   baa
 * output.txt:
 *   3 2 1
 *
 * input.txt:
 *   3 3 3
 *   bab
 *   bba
 *   baa
 * output.txt:
 *   2 3 1
 *
 * Примечание 1:
 *
 * Во всех примерах входных данных даны следующие строки:
 * - "bbb", имеющая индекс 1;
 * - "aba", имеющая индекс 2;
 * - "baa", имеющая индекс 3.
 *
 * Разберем первый пример.  Первая фаза цифровой сортировки отсортирует строки
 * по последнему символу, таким образом, первой строкой окажется "aba" (индекс
 * 2), затем "baa" (индекс 3), затем "bbb" (индекс 1).  Таким образом, ответ
 * равен "2 3 1".
 *
 * Примечание 2:
 *
 * В этой задаче очень много тестов.  Не удивляйтесь, если тестирование Вашего
 * решения происходит в течение нескольких минут.
 *
 * Примечание 3:
 *
 * У нас есть решение на PyPy, которое проходит все тесты.  Для ускорения
 * чтения входного файла это решение использует технику memory mapping, которая
 * читает весь входной файл в память средствами операционной системы.  Для
 * удобства использования, обертка ввода-вывода для PyPy теперь использует
 * memory mapping, поэтому решить задачу можно с использованием одной лишь этой
 * обертки.  Напоминаем, что обертка также доступна и в тестирующей системе, ее
 * не надо пытаться посылать вместе с основным решением.
 */

/* Для того, чтобы решение уложилось во временные рамки, пришлось использовать
 * библиотеку ввода/вывода от составителей курса.
 */
import mooc._

object RadixSort extends EdxIO.Receiver {
  def countingSort(
    nums: Array[Byte],
    indices: Array[Int],
    newIndices: Array[Int],
    c: Array[Int],
  ): Unit = {
    val n = indices.length

    for (i <- 0 until 26)
      c(i) = 0

    for (i <- 0 until n)
      c(nums(i)) += 1
    for (i <- 1 until 26)
      c(i) += c(i - 1)
    for (i <- n - 1 to 0 by -1) {
      c(nums(indices(i))) -= 1
      newIndices(c(nums(indices(i)))) = indices(i)
    }
  }

  def radixSort(strs: Array[Array[Byte]], indices: Array[Int], k: Int): Array[Int] = {
    val n = indices.length
    val m = strs.length
    val c = new Array[Int](26)
    var tmp = new Array[Int](n)

    for (i <- 0 until k) {
      val nums = strs(m - i - 1)
      for (j <- 0 until n)
        nums(j) = (nums(j) - 'a').toByte

      if (i % 2 == 0)
        countingSort(nums, indices, tmp, c)
      else
        countingSort(nums, tmp, indices, c)
    }

    if (k % 2 == 0) indices else tmp
  }

  def receive(io: EdxIO): Unit = {
    val n = io.nextInt()
    val m = io.nextInt()
    val k = io.nextInt()
    val strs = Array.fill[Array[Byte]](m)(io.nextBytes())

    val indices = radixSort(strs, Array.tabulate[Int](n)((i : Int) => i), k)

    indices.foreach(i => { io.print(i + 1); io.print(' ') })
    io.println()
  }
}