import scala.io.Source

val lines = Source.fromFile("03/input.txt").getLines().toList
val grid = lines.map(_.split("").toList)
val (h, w) = (grid.size, grid(0).size)
var count = 0

for i <- 1 until h do
    val y = i
    val x = i * 3 % w

    if grid(y)(x) == "#" then
        count += 1

println(count)
