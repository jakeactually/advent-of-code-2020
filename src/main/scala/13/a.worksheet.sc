import scala.io.Source
import scala.collection.mutable

val lines = Source
    .fromFile("13/input.txt")
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
