/* Почти интерактивная хеш-таблица
 *
 * В данной задаче у Вас не будет проблем ни с вводом, ни с выводом.  Просто
 * реализуйте быструю хеш-таблицу.
 *
 * В этой хеш-таблице будут храниться целые числа из диапазона [0; 10^15 - 1].
 * Требуется поддерживать добавление числа x и проверку того, есть ли в таблице
 * число x. Числа, с которыми будет работать таблица, генерируются следующим
 * образом. Пусть имеется четыре целых числа N, X, A, B, такие что:
 *
 * - 1 <= N <= 10^7;
 * - 0 <= X < 10^15;
 * - 0 <= A < 10^3;
 * - 0 <= B < 10^15.
 *
 * Требуется N раз выполнить следующую последовательность операций:
 *
 * - Если X содержится в таблице, то установить A <- (A + A_C) mod 10^3,
 *   B <- (B + B_C) mod 10^15.
 * - Если X не содержится в таблице, то добавить X в таблицу и установить
 *   A <- (A + A_D) mod 10^3, B <- (B + B_D) mod 10^15.
 * - Установить X <- (X * A + B) mod 10^15.
 *
 * Начальные значения X, A и B, а также N, A_C, B_C, A_D и B_D даны во входном
 * файле.  Выведите значения X, A и B после окончания работы.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла содержится четыре целых числа N, X, A, B.
 * Во второй строке содержится еще четыре целых числа A_C, B_C, A_D и B_D,
 * такие что 0 <= A_C, A_D < 10^3, 0 <= B_C, B_D < 10^15.
 *
 * Формат выходного файла:
 *
 * Выведите значения X, A и B после окончания работы.
 *
 * Пример:
 *
 * input.txt:
 *   4 0 0 0
 *   1 1 0 0
 *
 * output.txt:
 *   3 1 1
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.annotation.tailrec
import scala.util.Using

class HashSet(val capacity: Int) {
  val EMPTY = -1
  val DELETED = -2

  // Изначально вычислял `capacity` как простое число "на лету", оказалось, что
  // это вычисление сильно "тормозило", теперь передаю заведомо большое простое
  // число сразу в конструктор (с запасом в 1.5 раза, чтобы уменьшить число
  // коллизий).  При таком раскладе вполне быстро работает весьма простая
  // хэш-функция.
  val htable = Array.fill[Long](capacity)(EMPTY)
  var size = 0

  // Найти индекс элемента в хэш-массиве.  Метод возвращает кортеж из двух
  // значений:
  // - булево значение, указывающее, был ли элемент найден;
  // - индекс в хэш-массиве, который по разному интерпретируется в зависимости
  //   от первого элемента кортежа:
  //   - если булево значение истинно, то есть элемент был найден, то данное
  //     значение содержит индекс этого элемента;
  //   - если булево значение ложно, то есть элемент найден не был, то данное
  //     значение содержит индекс, куда можно вставить элемент с указанным
  //     строковым ключом.
  //
  // Данный подход был применен для оптимизации операции вставки, чтобы не
  // осуществлять два прохода по хэш-таблице: первый для проверки, есть ли уже
  // элемент в хэш-таблице, и второй непосредственно для вставки.
  def findIndex(x: Long): (Boolean, Int) = {
    @tailrec
    def iter(i: Int, h: Int, placeToInsert: Int = -1): (Boolean, Int) =
      if (i <= size + 1) {
        htable(h) match {
          case EMPTY =>
            (false, if (placeToInsert >= 0) placeToInsert else h)
          case DELETED =>
            iter(i + 1, (h + 41) % capacity, if (placeToInsert >= 0) placeToInsert else h)
          case y if y == x =>
            (true, h)
          case _ =>
            iter(i + 1, (h + 41) % capacity, placeToInsert)
        }
      } else
        (false, placeToInsert)

    iter(0, (x % capacity).toInt)
  }

  // Вставить элемент в хэш-таблицу.  Метод возвращает булево значение,
  // указывающее, был ли элемент вставлен (элемент не вставляется, если уже
  // находится в хэш-таблице).
  def insert(x: Long): Boolean =
    findIndex(x) match {
      case (false, h) =>
        size += 1
        htable(h) = x
        true
      case (true, _) =>
        false
    }
}

object FastHashTable {
  val al = 1_000
  val bl = 1_000_000_000_000_000L

  def main(args: Array[String]): Unit = {
    var (n, x, a, b, ac, bc, ad, bd) =
      Using.resource(new Scanner(new FileReader("input.txt"))) { in =>

      val n = in.nextInt()
      val x = in.nextLong()
      val a = in.nextInt()
      val b = in.nextLong()
      val ac = in.nextInt()
      val bc = in.nextLong()
      val ad = in.nextInt()
      val bd = in.nextLong()
      (n, x, a, b, ac, bc, ad, bd)
    }

    val set = new HashSet(15_000_017)
    for (i <- 0 until n) {
      if (!set.insert(x)) {
        a = (a + ac) % al
        b = (b + bc) % bl
      } else {
        a = (a + ad) % al
        b = (b + bd) % bl
      }
      x = (x * a + b) % bl
    }

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.printf("%d %d %d\n", x, a, b)
    }
  }
}
