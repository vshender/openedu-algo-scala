/* Quack
 *
 * Язык Quack -- забавный язык, который фигурирует в одной из задач с Internet
 * Problem Solving Contest (http://ipsc.ksp.sk/).  В этой задаче вам требуется
 * написать интерпретатор языка Quack.
 *
 * Виртуальная машина, на которой исполняется программа на языке Quack, имеет
 * внутри себя очередь, содержащую целые числа по модулю 65536 (то есть, числа
 * от 0 до 65535, соответствующие беззнаковому 16-битному целому типу).  Слово
 * get в описании операций означает извлечение из очереди, put -- добавление в
 * очередь.  Кроме того, у виртуальной машины есть 26 регистров, которые
 * обозначаются буквами от 'a' до 'z'.  Изначально все регистры хранят нулевое
 * значение.  В языке Quack существуют следующие команды (далее под `a` и `b`
 * подразумеваются некие абстрактные временные переменные):
 *
 * +
 * Сложение: get `a`, get `b`, put (`a` + `b`) mod 65536.
 *
 * -
 * Вычитание: get `a`, get `b`, put (`a` - `b`) mod 65536.
 *
 * *
 * Умножение: get `a`, get `b`, put (`a` * `b`) mod 65536.
 *
 * /
 * Целочисленное деление: get `a`, get `b`, put `a` div `b` (будем считать, что
 * `a` div 0 = 0).
 *
 * %
 * Взятие по модулю: get `a`, get `b`, put `a` mod `b` (будем считать, что
 * `a` mod 0 = 0).
 *
 * >[register]
 * Положить в регистр: get `a`, установить значение [register] в `a`.
 *
 * <[register]
 * Взять из регистра: put значение [register].
 *
 * P
 * Напечатать: get `a`, вывести `a` в стандартный поток вывода и перевести
 * строку.
 *
 * P[register]
 * Вывести значение регистра [register] в стандартный поток вывода и перевести
 * строку.
 *
 * C
 * Вывести как символ: get `a`, вывести символ с ASCII-кодом `a` mod 256 в
 * стандартный поток вывода.
 *
 * C[register]
 * Вывести регистр как символ: вывести символ с ASCII-кодом `a` mod 256 (где
 * `a` -- значение регистра [register]) в стандартный поток вывода.
 *
 * :[label]
 * Метка: эта строка программы имеет метку [label].
 *
 * J[label]
 * Переход на строку с меткой [label]
 *
 * Z[register][label]
 * Переход если 0: если значение регистра [register] равно нулю, выполнение
 * программы продолжается с метки [label].
 *
 * E[register1][register2][label]
 * Переход если равны: если значения регистров [register1] и [register2] равны,
 * исполнение программы продолжается с метки [label].
 *
 * G[register1][register2][label]
 * Переход если больше: если значение регистра [register1] больше, чем значение
 * регистра [register2], исполнение программы продолжается с метки [label].
 *
 * Q
 * Завершить работу программы.  Работа также завершается, если выполнение
 * доходит до конца программы.
 *
 * [number]
 * Просто число во входном файле -- put это число.
 *
 * Формат входного файла:
 *
 * Входной файл содержит синтаксически корректную программу на языке Quack.
 * Известно, что программа завершает работу не более чем за 10^5  шагов.
 * Программа содержит не менее одной и не более 10^5 инструкций.  *Метки имеют
 * длину от 1 до 10 и состоят из цифр и латинских букв*.
 *
 * Формат выходного файла:
 *
 * Выведите содержимое стандартного потока вывода виртуальной машины в выходной
 * файл.
 *
 * Примеры:
 *
 * input.txt:
 *   100
 *   0
 *   :start
 *   >a
 *   Zaend
 *   <a
 *   <a
 *   1
 *   +
 *   -
 *   >b
 *   <b
 *   Jstart
 *   :end
 *   P
 * output.txt:
 *   5050
 *
 * Второй пример подразумевает UNIX-переводы строки в ответе (один символ с
 * кодом 10).
 *
 * input.txt:
 *   58
 *   49
 *   10
 *   62
 *   97
 *   10
 *   80
 *   97
 *   10
 *   90
 *   97
 *   50
 *   10
 *   60
 *   97
 *   10
 *   74
 *   49
 *   10
 *   58
 *   50
 *   10
 *   48
 *   10
 *   58
 *   51
 *   10
 *   62
 *   97
 *   10
 *   90
 *   97
 *   52
 *   10
 *   67
 *   97
 *   10
 *   74
 *   51
 *   10
 *   58
 *   52
 *   10
 *   0
 *   :1
 *   >a
 *   Pa
 *   Za2
 *   <a
 *   J1
 *   :2
 *   0
 *   :3
 *   >a
 *   Za4
 *   Ca
 *   J3
 *   :4
 * output.txt:
 *   58
 *   49
 *   10
 *   62
 *   97
 *   10
 *   80
 *   97
 *   10
 *   90
 *   97
 *   50
 *   10
 *   60
 *   97
 *   10
 *   74
 *   49
 *   10
 *   58
 *   50
 *   10
 *   48
 *   10
 *   58
 *   51
 *   10
 *   62
 *   97
 *   10
 *   90
 *   97
 *   52
 *   10
 *   67
 *   97
 *   10
 *   74
 *   51
 *   10
 *   58
 *   52
 *   10
 *   0
 *   :1
 *   >a
 *   Pa
 *   Za2
 *   <a
 *   J1
 *   :2
 *   0
 *   :3
 *   >a
 *   Za4
 *   Ca
 *   J3
 *   :4
 */

import java.io.{BufferedReader, FileReader, FileWriter, PrintWriter}
import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap, Queue}
import scala.util.Using

abstract class BaseCmd {
  def addToProgram(program: Program): Unit
}

abstract class ExecCmd extends BaseCmd {
  def exec(program: Program): Unit

  override def addToProgram(program: Program): Unit =
    program.addCmd(this)
}

class CmdAdd extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromQueue()
    val y = program.getFromQueue()
    program.putToQueue((x + y) & 0xFFFF)
    program.goToNext()
  }

  override def toString: String = "+"
}

class CmdSub extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromQueue()
    val y = program.getFromQueue()
    program.putToQueue((x - y) & 0xFFFF)
    program.goToNext()
  }

  override def toString: String = "-"
}

class CmdMul extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromQueue()
    val y = program.getFromQueue()
    program.putToQueue((x * y) & 0xFFFF)
    program.goToNext()
  }

  override def toString: String = "*"
}

class CmdDiv extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromQueue()
    val y = program.getFromQueue()
    program.putToQueue(if (y != 0) x / y else 0)
    program.goToNext()
  }

  override def toString: String = "/"
}

class CmdMod extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromQueue()
    val y = program.getFromQueue()
    program.putToQueue(if (y != 0) x % y else 0)
    program.goToNext()
  }

  override def toString: String = "%"
}

class CmdSetReg(reg: Char) extends ExecCmd {
  override def exec(program: Program): Unit = {
    program.putToMem(reg, program.getFromQueue())
    program.goToNext()
  }

  override def toString: String = s">$reg"
}

class CmdGetReg(reg: Char) extends ExecCmd {
  override def exec(program: Program): Unit = {
    program.putToQueue(program.getFromMem(reg))
    program.goToNext()
  }

  override def toString: String = s"<$reg"
}

class CmdPrint extends ExecCmd {
  override def exec(program: Program): Unit = {
    program.println(program.getFromQueue())
    program.goToNext()
  }

  override def toString: String = "P"
}

class CmdPrintReg(reg: Char) extends ExecCmd {
  override def exec(program: Program): Unit = {
    program.println(program.getFromMem(reg))
    program.goToNext()
  }

  override def toString: String = s"P$reg"
}

class CmdPrintChar extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromQueue()
    program.print((x & 0xFF).toChar)
    program.goToNext()
  }

  override def toString: String = "C"
}

class CmdPrintRegChar(reg: Char) extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromMem(reg)
    program.print((x & 0xFF).toChar)
    program.goToNext()
  }

  override def toString: String = s"C$reg"
}

class CmdLabel(label: String) extends BaseCmd {
  override def addToProgram(program: Program): Unit =
    program.addLabel(label)
}

class CmdJump(label: String) extends ExecCmd {
  override def exec(program: Program): Unit =
    program.goTo(label)

  override def toString: String = s"J$label"
}

class CmdJumpIfZero(reg: Char, label: String) extends ExecCmd {
  override def exec(program: Program): Unit = {
    if (program.getFromMem(reg) == 0)
      program.goTo(label)
    else
      program.goToNext()
  }

  override def toString: String = s"Z$reg$label"
}

class CmdJumpIfEqual(reg1: Char, reg2: Char, label: String) extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromMem(reg1)
    val y = program.getFromMem(reg2)
    if (x == y)
      program.goTo(label)
    else
      program.goToNext()
  }

  override def toString: String = s"E$reg1$reg2$label"
}

class CmdJumpIfGreater(reg1: Char, reg2: Char, label: String) extends ExecCmd {
  override def exec(program: Program): Unit = {
    val x = program.getFromMem(reg1)
    val y = program.getFromMem(reg2)
    if (x > y)
      program.goTo(label)
    else
      program.goToNext()
  }

  override def toString: String = s"G$reg1$reg2$label"
}

class CmdQuit extends ExecCmd {
  override def exec(program: Program): Unit =
    program.quit()

  override def toString: String = "Q"
}

class CmdNumber(num: Int) extends ExecCmd {
  override def exec(program: Program): Unit = {
    program.putToQueue(num)
    program.goToNext()
  }

  override def toString: String = num.toString
}

object CmdFactory {
  def parseCmd(str: String): BaseCmd =
    str(0) match {
      case '+' => new CmdAdd()
      case '-' => new CmdSub()
      case '*' => new CmdMul()
      case '/' => new CmdDiv()
      case '%' => new CmdMod()
      case '>' => new CmdSetReg(str(1))
      case '<' => new CmdGetReg(str(1))
      case 'P' => if (str.length == 1) new CmdPrint() else new CmdPrintReg(str(1))
      case 'C' => if (str.length == 1) new CmdPrintChar() else new CmdPrintRegChar(str(1))
      case ':' => new CmdLabel(str.substring(1))
      case 'J' => new CmdJump(str.substring(1))
      case 'Z' => new CmdJumpIfZero(str(1), str.substring(2))
      case 'E' => new CmdJumpIfEqual(str(1), str(2), str.substring(3))
      case 'G' => new CmdJumpIfGreater(str(1), str(2), str.substring(3))
      case 'Q' => new CmdQuit()
      case _   => new CmdNumber(str.toInt)
    }
}

class Program(val out: PrintWriter) {
  val queue = Queue[Int]()
  val mem = new Array[Int](26)
  val code = ArrayBuffer[ExecCmd]()
  val labels = HashMap[String, Int]()
  val codeLabels = HashMap[Int, List[String]]()
  var ip = 0

  def getFromQueue(): Int =
    queue.dequeue()

  def putToQueue(x: Int): Unit =
    queue.enqueue(x)

  def getFromMem(reg: Char): Int =
    mem(reg - 'a')

  def putToMem(reg: Char, x: Int): Unit =
    mem(reg - 'a') = x

  def println(x: Int): Unit =
    out.println(x)

  def print(c: Char): Unit =
    out.print(c)

  def goTo(label: String): Unit =
    ip = labels(label)

  def goToNext(): Unit =
    ip += 1

  def quit(): Unit =
    ip = code.length

  def addCmd(cmd: ExecCmd): Unit =
    code += cmd

  def addLabel(label: String): Unit = {
    labels += (label -> code.length)
    codeLabels += code.length -> (label :: codeLabels.getOrElse(code.length, Nil))
  }

  def read(in: BufferedReader): Unit =
    LazyList
      .continually(in.readLine())
      .takeWhile(_ != null)
      .foreach(CmdFactory.parseCmd(_).addToProgram(this))

  def run(debug: Boolean = false): Unit = {
    if (debug) {
      Predef.println(this)

      Predef.println("START")
      printState()
    }

    while (ip < code.length) {
      val cmd = code(ip)
      cmd.exec(this)

      if (debug) {
        System.out.println(cmd)
        printState()
      }
    }
  }

  override def toString: String = {
    val sb = Buffer[String]()

    for (i <- 0 until code.length) {
      if (codeLabels.contains(i))
        codeLabels(i).reverse.foreach((label) => sb += s":$label\n")
      sb += s"${code(i)}\n"
    }

    sb.mkString
  }

  def printState(): Unit = {
    Predef.print("  QUEUE: ")
    queue.foreach(printf("%d ", _))
    Predef.println()

    Predef.print("  REGISTERS: ")
    mem.zipWithIndex.foreach { case (x, i) => printf("%c:%d ", i + 'a', x) }
    Predef.println()
  }
}

object Quack {
  def main(args: Array[String]): Unit = {
    Using.resource(new BufferedReader(new FileReader("input.txt"))) { in =>
      Using.resource(new PrintWriter(new FileWriter("output.txt"))) { out =>
        val program = new Program(out)
        program.read(in)
        program.run(false)
      }
    }
  }
}
