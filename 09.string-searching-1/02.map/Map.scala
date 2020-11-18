/* Карта
 *
 * Даже самый последний матрос знает, что мы едем искать сокровища.  Не
 * нравится мне всё это!
 *                                                     Капитан Смоллетт
 *
 * В далеком 1744 году во время долгого плавания в руки капитана Александра
 * Смоллетта попала древняя карта с указанием местонахождения сокровищ.  Однако
 * расшифровать ее содержание было не так уж и просто.
 *
 * Команда Александра Смоллетта догадалась, что сокровища находятся на x шагов
 * восточнее красного креста, однако определить значение числа она не смогла.
 * По возвращению на материк Александр Смоллетт решил обратиться за помощью в
 * расшифровке послания к знакомому мудрецу.  Мудрец поведал, что данное
 * послание таит за собой некоторое число.  Для вычисления этого числа
 * необходимо было удалить все пробелы между словами, а потом посчитать
 * количество способов вычеркнуть все буквы кроме трех так, чтобы полученное
 * слово из трех букв одинаково читалось слева направо и справа налево.
 *
 * Александр Смоллетт догадывался, что число, зашифрованное в послании, и есть
 * число x.  Однако, вычислить это число у него не получилось.
 *
 * После смерти капитана карта была безнадежно утеряна до тех пор, пока не
 * оказалась в ваших руках.  Вы уже знаете все секреты, осталось только
 * вычислить число x.
 *
 * Формат входного файла:
 *
 * В единственной строке входного файла дано послание, написанное на карте.
 * Длина послания не превышает 3 * 10^5.  Гарантируется, что послание может
 * содержать только строчные буквы английского алфавита и пробелы.  Также
 * гарантируется, что послание не пусто.  Послание не может начинаться с
 * пробела или заканчиваться им.
 *
 * Формат выходного файла:
 *
 * Выведите одно число x -- число способов вычеркнуть из послания все буквы
 * кроме трех так, чтобы оставшееся слово одинаково читалось слева направо и
 * справа налево.
 *
 * Примеры:
 *
 * input.txt:
 *   treasure
 * output.txt:
 *   8
 *
 * input.txt
 *   you will never find the treasure
 * output.txt
 *   146
 */

import java.io.{FileReader, FileWriter, PrintWriter}
import java.util.Scanner
import scala.collection.mutable.HashMap
import scala.util.Using

object Map {
  // Такое наивное решение не проходит по времени.
  //
  // def decode(message: String): Long = {
  //   val messageWithoutSpaces = message.replaceAll(" ", "")
  //   var x = 0L
  //   for (i <- 0 until messageWithoutSpaces.length - 2)
  //     for (j <- i + 2 until messageWithoutSpaces.length)
  //       if (messageWithoutSpaces(i) == messageWithoutSpaces(j))
  //         x += j - i - 1
  //   x
  // }

  def decode(message: String): Long = {
    // Состояние вычислений для каждой буквы сообщения.
    case class CharState(
      var n: Int,     // количество таких же букв, которые идут в сообщение перед данной
      var cnt: Long,  // количество способов вычеркнуть буквы так, чтобы данная была последней
    ) {}

    val messageWithoutSpaces = message.replaceAll(" ", "")
    val prevCharIndex = new HashMap[Char, Int]
    val states = new Array[CharState](messageWithoutSpaces.length)

    messageWithoutSpaces.zipWithIndex.foreach{
      case (chr, idx) =>
        states(idx) = prevCharIndex.get(chr) match {
          case Some(prevIdx) =>
            CharState(
              n = states(prevIdx).n + 1,
              cnt = states(prevIdx).cnt + states(prevIdx).n * (idx - prevIdx) + (idx - prevIdx - 1)
            )
          case None =>
            CharState(0, 0)
        }

        prevCharIndex += chr -> idx
    }

    states.map(_.cnt).sum
  }

  def main(args: Array[String]): Unit = {
    val message = Using.resource(new Scanner(new FileReader("input.txt"))) { in =>
      in.nextLine()
    }

    val x = decode(message)

    Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
      out.println(x)
    }
  }
}
