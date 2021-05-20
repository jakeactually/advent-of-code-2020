// https://www.reddit.com/r/adventofcode/comments/ka8z8x/2020_day_10_solutions/gfcxuxf?utm_source=share&utm_medium=web2x&context=3

import scala.io.Source
import scala.collection.mutable

val adapters = 0 :: Source
    .fromFile("10/input.txt")
    .getLines()
    .toList
    .map(_.toInt)

val paths = mutable.Map[Int, Long](0 -> 1)
val set = adapters.toSet

for adapter <- adapters.sorted do
    for diff <- 1 to 3 do
        val next = adapter + diff

        if set.contains(next) then
            paths(next) = paths.get(next).getOrElse(0L) + paths.get(adapter).getOrElse(0L)

println(paths(adapters.max))
