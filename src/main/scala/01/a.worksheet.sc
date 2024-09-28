import scala.io.Source

val lines = Source.fromFile("01/input.txt").getLines().toList
    
for List(a, b) <- lines.combinations(2) do
    if a.toInt + b.toInt == 2020 then
        println(a.toInt * b.toInt)
