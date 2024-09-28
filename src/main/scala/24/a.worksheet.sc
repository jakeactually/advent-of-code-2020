import scala.io.Source
import scala.collection.mutable

extension (vector: (Int, Int, Int))
    def +(other: (Int, Int, Int)) = (
        vector._1 + other._1,
        vector._2 + other._2,
        vector._3 + other._3
    )

val regex = raw"se|sw|ne|nw|e|w".r
val grid = mutable.Set[(Int, Int, Int)]()

val directions = Map(
    "se" -> (1, 1, 0),
    "sw" -> (0, 1, -1),
    "ne" -> (0, -1, 1),
    "nw" -> (-1, -1, 0),
    "e" -> (1, 0, 1),
    "w" -> (-1, 0, -1)
)

for line <-  Source.fromFile("24/input.txt").getLines() do
    var coord = (0, 0, 0)

    for direction <- regex.findAllIn(line) do
        coord += directions(direction)       
        
    if grid.contains(coord) then
        grid.remove(coord)
    else
        grid.add(coord)

grid.size
