import scala.io.Source
import scala.collection.mutable

val numbers =
    Source.fromFile("09/input.txt").getLines().toList.map(_.toLong)

for i <- 25 until numbers.size do
    val n = numbers(i)
    val before = numbers.slice(i - 25, i)

    if !before.combinations(2).map(_.sum).exists(_ == n) then
        println(n)
