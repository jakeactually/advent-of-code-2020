import scala.io.Source

object Day1A extends App {
    val lines = Source.fromFile("input.txt").getLines().toList
    val grid = lines.map(_.split("").toList)
    val (h, w) = (grid.size, grid(0).size)
    var count = 0
    
    for (i <- 1 until h) {
        val y = i
        val x = i * 3 % w

        if (grid(y)(x) == "#") {
            count += 1
        }
    }

    println(count)
}
