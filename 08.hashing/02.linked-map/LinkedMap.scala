/* Прошитый ассоциативный массив
 *
 * Реализуйте прошитый ассоциативный массив.
 *
 * Формат входного файла:
 *
 * В первой строке входного файла находится строго положительное целое число
 * операций N, не превышающее 5 * 10^5.  В каждой из последующих N строк
 * находится одна из следующих операций:
 * - get x -- если ключ x есть в множестве, выведите соответствующее ему
 *   значение, если нет, то выведите "<none>".
 * - prev x -- вывести значение, соответствующее ключу, находящемуся в
 *   ассоциативном массиве, который был вставлен позже всех, но до x, или
 *   "<none>", если такого нет или в массиве нет x.
 * - next x -- вывести значение, соответствующее ключу, находящемуся в
 *   ассоциативном массиве, который был вставлен раньше всех, но после x,
 *   или "<none>", если такого нет или в массиве нет x.
 * - put x -- поставить в соответствие ключу x значение y.  При этом следует
 *   учесть, что:
 *   - eсли, независимо от предыстории, этого ключа на момент вставки в массиве
 *     не было, то он считается только что вставленным и оказывается самым
 *     последним среди добавленных элементов -- то есть, вызов next с этим же
 *     ключом сразу после выполнения текущей операции put должен вернуть
 *     "<none>";
 *   - если этот ключ уже есть в массиве, то значение необходимо изменить, и в
 *     этом случае ключ не считается вставленным еще раз, то есть, не меняет
 *     своего положения в порядке добавленных элементов.
 * - delete x -- удалить ключ x.  Если ключа x в ассоциативном массиве нет, то
 *   ничего делать не надо.
 *
 * Ключи и значения -- строки из латинских букв длиной не менее одного и не
 * более 20 символов.
 *
 * Формат выходного файла:
 *
 * Выведите последовательно результат выполнения всех операций get, prev, next.
 * Следуйте формату выходного файла из примера.
 *
 * Пример:
 *
 * input.txt:
 *   14
 *   put zero a
 *   put one b
 *   put two c
 *   put three d
 *   put four e
 *   get two
 *   prev two
 *   next two
 *   delete one
 *   delete three
 *   get two
 *   prev two
 *   next two
 *   next four
 * output.txt:
 *   c
 *   b
 *   d
 *   c
 *   a
 *   e
 *   <none>
 */

/* В данной задаче довольно жесткие ограничения по времени и памяти, поэтому
 * в решении пришлось реализовать ряд ухищрений; комментарии об этом есть ниже
 * в тексте программы.
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.annotation.tailrec
import scala.util.Using

class LinkedHashTable(n: Int) {
  // Отличная идея: использование trait с тремя наследниками (пустой элемент,
  // удаленный элемент, существующий элемент).  В первых реализациях хранил
  // в хэш-массиве либо сами элементы и `null` в качестве отсутствующего
  // значения, либо `Option[Item]`, при этом флаг того, был ли удален из данной
  // ячейки элемент, хранился в отдельном битовом (для экономии памяти)
  // массиве.
  sealed trait HashItem {
    def item: Item
  }

  object Empty extends HashItem {
    def item: Item = throw new NoSuchElementException("Empty.item")
  }

  object Deleted extends HashItem {
    def item: Item = throw new NoSuchElementException("Deleted.item")
  }

  case class Item(
    // Для хранения ключа и значения используются массивы байтов вместо строк.
    // Именно это в конечном счете позволило уложиться в ограничения по памяти.
    var key: Array[Byte],
    var value: Array[Byte],
    // Хранение предыдущего и следующего элементов в виде `Option`-типа
    // замедляет программу по сравнению с использованием простого типа и
    // `null`-значений, но благодаря оптимизации вычисления хэш-функции текущая
    // реализация тем не менее укладывается в ограничения по времени, так что
    // было решено оставить более идеоматичный для Scala код с использованием
    // for-comprehension и метода `foreach`.
    var prev: Option[Item] = None,
    var next: Option[Item] = None,
  ) extends HashItem {
    def item: Item = this
  }

  val primes: LazyList[Int] =
    2 #:: LazyList.from(3).filter(n => primes.takeWhile(p => p * p <= n).forall(p => n % p != 0))
  val capacity = primes.dropWhile(_ < n).head

  val htable = Array.fill[HashItem](capacity)(Empty)
  var size = 0
  var last: Option[Item] = None

  def strKey(key: Array[Byte]): Long =
    // Отказ от цикла с изменением внешней переменной в пользу `foldLeft`
    // позволил увеличить производительность.
    //
    // var h = 0L
    // for (i <- 0 until key.length)
    //   h = (h * 269 + key(i)) & Long.MaxValue
    // h
    key.foldLeft(0L)((h, char) => (h * 269 + char) & Long.MaxValue)

  // При использовании функции `hash` происходит постоянное перевычисление
  // `h1` и `h2` для каждой из проб, при этом эти значения не меняются для
  // одного и того же ключа.  Вычисление этих значений всего лишь один раз
  // позволило ускорить программу примерно на 12%.
  //
  // def h1(k: Long): Long =
  //   k % capacity
  //
  // def h2(k: Long): Long =
  //   1 + k % (capacity - 1)
  //
  // def hash(k: Long, i: Int): Int =
  //   ((h1(k) + i * h2(k)) % capacity).toInt

  def h1(k: Long): Int =
    (k % capacity).toInt

  def h2(k: Long): Int =
    (1 + k % (capacity - 1)).toInt

  // Найти индекс элемента в хэш-массиве по ключу.  Метод возвращает кортеж из
  // двух значений:
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
  def findIndex(keyBytes: Array[Byte]): (Boolean, Int) = {
    // Отказ от цикла с изменением внешней переменной в пользу хвостовой
    // рекурсии позволил увеличить производительность.

    val k = strKey(keyBytes)
    val dh = h2(k)

    @tailrec
    def iter(i: Int, h: Int, placeToInsert: Int = -1): (Boolean, Int) =
      if (i <= size + 1)
        htable(h) match {
          case Empty =>
            (false, if (placeToInsert >= 0) placeToInsert else h)
          case Deleted =>
            iter(i + 1, (h + dh) % capacity, if (placeToInsert == -1) h else placeToInsert)
          case Item(key, _, _, _) if (key(0) == keyBytes(0) && (key sameElements keyBytes)) =>
            // Сравниваем первые байты и только в случае равенства вызываем проверку
            // `sameElements`.
            (true, h)
          case _ =>
            iter(i + 1, (h + dh) % capacity, placeToInsert)
        }
      else
        (false, placeToInsert)

    iter(0, h1(k))
  }

  def find(key: String): Option[Item] =
    findIndex(key.getBytes) match {
      case (true, h)  => Some(htable(h).item)
      case (false, _) => None
    }

  def get(key: String): Option[String] =
    find(key).map(_.value.map(_.toChar).mkString)

  def prev(key: String): Option[String] =
    // find(key).flatMap(_.prev.map(_.value.map(_.toChar).mkString))
    for (item <- find(key); prev <- item.prev)
      yield prev.value.map(_.toChar).mkString

  def next(key: String): Option[String] =
    // find(key).flatMap(_.next.map(_.value.map(_.toChar).mkString))
    for (item <- find(key); next <- item.next)
      yield next.value.map(_.toChar).mkString

  def insert(key: String, value: String): Unit = {
    val keyBytes = key.getBytes
    findIndex(keyBytes) match {
      case (true, h) =>
        htable(h).item.value = value.getBytes
      case (false, h) =>
        size += 1

        val item = new Item(keyBytes, value.getBytes, last)

        htable(h) = item

        last.foreach(_.next = Some(item))
        last = Some(item)
    }
  }

  def delete(key: String): Unit = {
    findIndex(key.getBytes) match {
      case (true, h) =>
        size -= 1

        val item = htable(h).item

        htable(h) = Deleted

        item.prev.foreach(_.next = item.next)
        item.next.foreach(_.prev = item.prev)

        last.foreach((lastItem: Item) => if (lastItem eq item) last = item.prev)
      case (false, _) =>
        ()
    }
  }
}

object LinkedMap {
  def main(args: Array[String]): Unit = {
    Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val n = in.nextInt()
        val ht = new LinkedHashTable(n)
        for (i <- 0 until n) {
          val cmd = in.next()
          val x = in.next()
          cmd match {
            case "get"    => out.println(ht.get(x).getOrElse("<none>"))
            case "prev"   => out.println(ht.prev(x).getOrElse("<none>"))
            case "next"   => out.println(ht.next(x).getOrElse("<none>"))
            case "put"    => val y = in.next(); ht.insert(x, y)
            case "delete" => ht.delete(x)
          }
        }
      }
    }
  }
}
