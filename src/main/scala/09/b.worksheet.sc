import scala.io.Source
import scala.collection.mutable

val numbers = Source.fromFile("09/input.txt").getLines().toList.map(_.toLong)

var start = 0
var end = 0
var sum = 0L
var loop = true

while loop do
    if sum < 217430975 then
        end += 1
        sum += numbers(end)
    else if sum > 217430975 then
        start += 1
        sum -= numbers(start)
    else
        loop = false
        val slice = numbers.slice(start + 1, end + 1)
        println(slice.max + slice.min)
