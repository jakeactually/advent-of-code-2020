import scala.io.Source

object Day1A extends App {
    val lines = Source.fromFile("input.txt").getLines().toList
    
    for (List(a, b, c) <- lines.combinations(3)) {
        if (a.toInt + b.toInt + c.toInt == 2020) {
            println(a.toInt * b.toInt * c.toInt)
        }
    }
}
