import scala.io.Source
import scala.collection.mutable

object Main extends App {
    val numbers =
        Source.fromFile("input.txt").getLines().toList.map(_.toLong)

    for (i <- 25 until numbers.size) {
        val n = numbers(i)
        val before = numbers.slice(i - 25, i)

        if (!before.combinations(2).map(_.sum).exists(_ == n)) {
            println(n)
        }
    }
}
