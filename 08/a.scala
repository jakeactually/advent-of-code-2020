import scala.io.Source
import scala.collection.mutable

sealed abstract class Instruction
case class Acc(val value: Int) extends Instruction
case class Jmp(val value: Int) extends Instruction
case object  Nop extends Instruction

object Main extends App {
    val instructions: List[Instruction] = Source.fromFile("input.txt").getLines().toList.map { line =>
        val Array(left, right) = line.split(" ")

        left match {
            case "nop" => Nop
            case "acc" => Acc(right.toInt)
            case "jmp" => Jmp(right.toInt)
        }
    }

    var ip = 0
    var acc = 0
    val set = mutable.Set[Int]()

    while (!set.contains(ip)) {
        instructions(ip) match {
            case Acc(value) => acc += value
            case Jmp(value) => ip += value - 1
            case _ => ()
        }

        set.add(ip)
        ip += 1
    }

    println(acc)
}
