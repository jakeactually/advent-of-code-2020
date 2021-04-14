import scala.io.Source
import scala.collection.mutable

object Main extends App {
    var numbers = Source
        .fromFile("input.txt")
        .getLines()
        .toArray
        .map(_.toCharArray())

    val set = mutable.Set[String]()

    while (!set.contains(numbers.map(_.mkString).mkString("\n"))) {
        set.add(numbers.map(_.mkString).mkString("\n"))
        numbers = round(numbers)
    }

    println(numbers.flatten.filter(_ == '#').size)

    def round(arr: Array[Array[Char]]): Array[Array[Char]] = {
        val copy = arr.map(_.map(identity))

        for ((row, y) <- arr.zipWithIndex) {
            for ((cell, x) <- row.zipWithIndex) {
                var occupied = 0

                for (rx <- Seq(0, 1, -1); ry <- Seq(0, 1, -1))  {
                    if (rx != 0 || ry != 0) {
                        var step = 1
                        var inside = true
                        var collide = false

                        while (inside && !collide) {
                            val (ax, ay) = (rx * step + x, ry * step + y)
                            inside = ax >= 0 && ax < row.size && ay >= 0 && ay < arr.size 

                            if (inside && arr(ay)(ax) != '.') {                                                 
                                collide = true

                                if (arr(ay)(ax) == '#') {
                                    occupied += 1
                                }
                            }

                            step += 1
                        }
                    }
                }

                if (arr(y)(x) == 'L' && occupied == 0) {
                    copy(y)(x) = '#'
                } else if (arr(y)(x) == '#' && occupied >= 5) {
                    copy(y)(x) = 'L'
                }
            }
        }

        copy
    }
}
