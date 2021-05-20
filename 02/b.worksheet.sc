import scala.io.Source

val lines = Source.fromFile("02/input.txt").getLines().toList
val regex = raw"(\d{1,2})-(\d{1,2}) (\w): (\w+)".r
var count = 0

for line <- lines do
    val regex(i1, i2, char, password) = line

    if (password(i1.toInt - 1) == char(0)) != (password(i2.toInt - 1) == char(0)) then
        count += 1

println(count)
