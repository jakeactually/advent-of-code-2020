import scala.io.Source
import scala.collection.mutable

val numbers = Source
    .fromFile("10/input.txt")
    .getLines()
    .toList
    .map(_.toInt)
    .sorted

var ones = 1
var threes = 1

for i <- 0 until numbers.size - 1 do
    val a = numbers(i)
    val b = numbers(i + 1)

    if b - a == 1 then
        ones += 1
    
    if b - a == 3 then
        threes += 1

println(ones * threes)
