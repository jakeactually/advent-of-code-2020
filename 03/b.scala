import scala.io.Source

object Day1A extends App {
    val lines = Source.fromFile("input.txt").getLines().toList
    val grid = lines.map(_.split("").toList)
    val (h, w) = (grid.size, grid(0).size)

    var count1 = 0
    var count3 = 0
    var count5 = 0
    var count7 = 0
    var count1_2 = 0
    
    for (i <- 1 until h) {
        val y = i

        val x1 = i % w
        val x3 = i * 3 % w
        val x5 = i * 5 % w
        val x7 = i * 7 % w

        if (grid(y)(x1) == "#") count1 += 1
        if (grid(y)(x3) == "#") count3 += 1
        if (grid(y)(x5) == "#") count5 += 1
        if (grid(y)(x7) == "#") count7 += 1
        if (y % 2 == 0 && grid(y)(i / 2 % w) == "#") count1_2 += 1
    }

    println(count1 * count3 * count5 * count7 * count1_2)
}
