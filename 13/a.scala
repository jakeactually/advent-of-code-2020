import scala.io.Source
import scala.collection.mutable

object Main extends App {
    val lines = Source
        .fromFile("input.txt")
        .getLines()
        .toList

    val early = lines(0).toInt

    val min = lines(1)
        .split(",")
        .toList
        .filter(_ != "x")
        .map(_.toInt)
        .map(x => (x, early + x - early % x))
        .minBy(_._2)

    println(min._1 * (min._2 - early))
}
