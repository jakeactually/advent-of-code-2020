import scala.io.Source

val lines = Source.fromFile("01/input.txt").getLines().toList

for List(a, b, c) <- lines.combinations(3) do
    if a.toInt + b.toInt + c.toInt == 2020 then
        println(a.toInt * b.toInt * c.toInt)
