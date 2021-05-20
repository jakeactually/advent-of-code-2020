import scala.io.Source
import scala.collection.mutable

enum Instruction:
    case Acc(val value: Int)
    case Jmp(val value: Int)
    case Nop(val value: Int)

val instructions: List[Instruction] = Source.fromFile("08/input.txt").getLines().toList.map { line =>
    val Array(left, right) = line.split(" ")

    left match {
        case "nop" => Instruction.Nop(right.toInt)
        case "acc" => Instruction.Acc(right.toInt)
        case "jmp" => Instruction.Jmp(right.toInt)
    }
}

var change = 0
var loop = true

while loop do
    val copy = instructions.toArray

    copy(change) = copy(change) match {
        case Instruction.Acc(value) => Instruction.Acc(value)
        case Instruction.Nop(value) => Instruction.Jmp(value)
        case Instruction.Jmp(value) => Instruction.Nop(value)
    }

    var ip = 0
    var acc = 0
    val set = mutable.Set[Int]()

    while !set.contains(ip) && ip < copy.length do
        set.add(ip)

        copy(ip) match {
            case Instruction.Acc(value) => {
                acc += value
                ip += 1
            }
            case Instruction.Jmp(value) => ip += value
            case _ => ip += 1
        }

    if ip >= copy.length then
        println(acc)
        loop = false

    change += 1
