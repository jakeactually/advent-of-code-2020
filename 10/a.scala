import scala.io.Source
import scala.collection.mutable

object Main extends App {
    val numbers = Source
        .fromFile("input.txt")
        .getLines()
        .toList
        .map(_.toInt)
        .sorted

    var ones = 1
    var threes = 1

    for (i <- 0 until numbers.size - 1) {
        val a = numbers(i)
        val b = numbers(i + 1)

        if (b - a == 1) {
            ones += 1
        }
        
        if (b - a == 3) {
            threes += 1
        }
    }

    println(ones * threes)
}
