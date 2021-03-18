import scala.io.Source
import scala.collection.mutable

sealed abstract class Instruction
case class Acc(val value: Int) extends Instruction
case class Jmp(val value: Int) extends Instruction
case class Nop(val value: Int) extends Instruction

object Main extends App {
    val instructions: List[Instruction] = Source.fromFile("input.txt").getLines().toList.map { line =>
        val Array(left, right) = line.split(" ")

        left match {
            case "nop" => Nop(right.toInt)
            case "acc" => Acc(right.toInt)
            case "jmp" => Jmp(right.toInt)
        }
    }

    var change = 0
    var loop = true

    while (loop) {
        val copy = instructions.toArray

        copy(change) = copy(change) match {
            case Acc(value) => Acc(value)
            case Nop(value) => Jmp(value)
            case Jmp(value) => Nop(value)
        }

        var ip = 0
        var acc = 0
        val set = mutable.Set[Int]()

        while (!set.contains(ip) && ip < copy.length) {
            set.add(ip)

            copy(ip) match {
                case Acc(value) => {
                    acc += value
                    ip += 1
                }
                case Jmp(value) => ip += value
                case _ => ip += 1
            }
        }

        if (ip >= copy.length) {
            println(acc)
            loop = false
        }

        change += 1
    }
}
