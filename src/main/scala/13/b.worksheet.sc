// https://www.reddit.com/r/adventofcode/comments/kc4njx/2020_day_13_solutions/gsm8rod?utm_source=share&utm_medium=web2x&context=3

import scala.io.Source
import scala.collection.mutable

val lines = Source
    .fromFile("13/input.txt")
    .getLines()
    .toList

val numbers = lines(1)
    .split(",")
    .toList
    .filter(_ != "x")
    .map(_.toInt)

val equations = lines(1)
    .split(",")
    .zipWithIndex
    .filter(_._1 != "x")
    .map(x => (x._1.toInt, x._2, x._1.toInt - x._2))

val offset = equations
    .map(_._3)
    .groupBy(identity)
    .maxBy(_._2.length)
    ._1

var i = BigInt(offset)

val offseted = equations
    .toList
    .map { case (x, j, _) => (x, (j + offset) % x) }

val step = offseted.filter(_._2 == 0).map(_._1).product

while !offseted.forall { case (x, j) => (i - offset + j) % x == 0 } do
    i += step

println(i)
