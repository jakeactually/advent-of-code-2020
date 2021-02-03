import scala.io.Source

object Main extends App {
    val lines = Source.fromFile("input.txt").getLines().toList
    
    for (List(a, b) <- lines.combinations(2)) {
        if (a.toInt + b.toInt == 2020) {
            println(a.toInt * b.toInt)
        }
    }
}
