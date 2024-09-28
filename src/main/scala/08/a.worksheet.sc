import scala.io.Source
import scala.collection.mutable

enum Instruction:
    case Acc(val value: Int)
    case Jmp(val value: Int)
    case Nop

val instructions: List[Instruction] = Source.fromFile("08/input.txt").getLines().toList.map { line =>
    val Array(left, right) = line.split(" ")

    left match {
        case "nop" => Instruction.Nop
        case "acc" => Instruction.Acc(right.toInt)
        case "jmp" => Instruction.Jmp(right.toInt)
    }
}

var ip = 0
var acc = 0
val set = mutable.Set[Int]()

while !set.contains(ip) do
    instructions(ip) match {
        case Instruction.Acc(value) => acc += value
        case Instruction.Jmp(value) => ip += value - 1
        case _ => ()
    }

    set.add(ip)
    ip += 1

println(acc)
