// https://gist.github.com/joshbduncan/f1469f9f5119f30a2203969a5e4ea119

import scala.io.Source

val data = Source.fromFile("17/input.txt").getLines().toArray

var active2 = scala.collection.mutable.Set[(Int, Int, Int, Int)]()
for {
  (line, r) <- data.zipWithIndex
  (char, c) <- line.zipWithIndex
  if char == '#'
} {
  active2.add((r, c, 0, 0))
}

(0 until 6)
  .foldLeft(active2) { (currentActive, _) =>
    val newActive = scala.collection.mutable.Set[(Int, Int, Int, Int)]()
    val xvals = currentActive.map(_._1)
    val yvals = currentActive.map(_._2)
    val zvals = currentActive.map(_._3)
    val wvals = currentActive.map(_._4)

    for {
      x <- (xvals.min - 1) to (xvals.max + 1)
      y <- (yvals.min - 1) to (yvals.max + 1)
      z <- (zvals.min - 1) to (zvals.max + 1)
      w <- (wvals.min - 1) to (wvals.max + 1)
    } {
      var nbrs = 0
      for {
        dx <- -1 to 1
        dy <- -1 to 1
        dz <- -1 to 1
        dw <- -1 to 1
        if !(dx == 0 && dy == 0 && dz == 0 && dw == 0)
      } {
        if (currentActive.contains((x + dx, y + dy, z + dz, w + dw))) {
          nbrs += 1
        }
      }

      if (!currentActive.contains((x, y, z, w)) && nbrs == 3) {
        newActive.add((x, y, z, w))
      }
      if (currentActive.contains((x, y, z, w)) && (nbrs == 2 || nbrs == 3)) {
        newActive.add((x, y, z, w))
      }
    }

    newActive
  }
  .size
