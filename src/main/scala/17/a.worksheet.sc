// https://gist.github.com/joshbduncan/f1469f9f5119f30a2203969a5e4ea119

import scala.io.Source

val data = Source.fromFile("17/input.txt").getLines().toArray

var active1 = scala.collection.mutable.Set[(Int, Int, Int)]()
for {
  (line, r) <- data.zipWithIndex
  (char, c) <- line.zipWithIndex
  if char == '#'
} {
  active1.add((r, c, 0))
}

(0 until 6)
  .foldLeft(active1) { (currentActive, _) =>
    val newActive = scala.collection.mutable.Set[(Int, Int, Int)]()
    val xvals = currentActive.map(_._1)
    val yvals = currentActive.map(_._2)
    val zvals = currentActive.map(_._3)

    for {
      x <- (xvals.min - 1) to (xvals.max + 1)
      y <- (yvals.min - 1) to (yvals.max + 1)
      z <- (zvals.min - 1) to (zvals.max + 1)
    } {
      var nbrs = 0
      for {
        dx <- -1 to 1
        dy <- -1 to 1
        dz <- -1 to 1
        if !(dx == 0 && dy == 0 && dz == 0)
      } {
        if (currentActive.contains((x + dx, y + dy, z + dz))) {
          nbrs += 1
        }
      }

      if (!currentActive.contains((x, y, z)) && nbrs == 3) {
        newActive.add((x, y, z))
      }
      if (currentActive.contains((x, y, z)) && (nbrs == 2 || nbrs == 3)) {
        newActive.add((x, y, z))
      }
    }

    newActive
  }
  .size
