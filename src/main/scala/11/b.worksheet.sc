import scala.io.Source
import scala.collection.mutable

var numbers = Source
    .fromFile("11/input.txt")
    .getLines()
    .toArray
    .map(_.toCharArray())

val set = mutable.Set[String]()

while !set.contains(numbers.map(_.mkString).mkString("\n")) do
    set.add(numbers.map(_.mkString).mkString("\n"))
    numbers = round(numbers)

println(numbers.flatten.filter(_ == '#').size)

def round(arr: Array[Array[Char]]): Array[Array[Char]] =
    val copy = arr.map(_.map(identity))

    for (row, y) <- arr.zipWithIndex do
        for (cell, x) <- row.zipWithIndex do
            var occupied = 0

            for rx <- Seq(0, 1, -1); ry <- Seq(0, 1, -1)  do
                if rx != 0 || ry != 0 then
                    var step = 1
                    var inside = true
                    var collide = false

                    while inside && !collide do
                        val (ax, ay) = (rx * step + x, ry * step + y)
                        inside = ax >= 0 && ax < row.size && ay >= 0 && ay < arr.size 

                        if inside && arr(ay)(ax) != '.' then                                                 
                            collide = true

                            if arr(ay)(ax) == '#' then
                                occupied += 1

                        step += 1

            if arr(y)(x) == 'L' && occupied == 0 then
                copy(y)(x) = '#'
            else if arr(y)(x) == '#' && occupied >= 5 then
                copy(y)(x) = 'L'

    copy
