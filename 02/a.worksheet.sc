import scala.io.Source

val lines = Source.fromFile("02/input.txt").getLines().toList
val regex = raw"(\d{1,2})-(\d{1,2}) (\w): (\w+)".r
var count = 0

for line <- lines do
    val regex(min, max, char, password) = line
    val amount = password.split("").filter(_ == char).size

    if (min.toInt to max.toInt).contains(amount) then
        count += 1

println(count)
