import scala.io.Source

object Main extends App {
    val groups = Source
        .fromFile("input.txt")
        .getLines()
        .toList
        .map { line =>
            val (rowString, columnString) = line.splitAt(7)
            
            val rowBinary = rowString.map { c => if (c == 'F') '0' else '1' }
            val columnBinary = columnString.map { c => if (c == 'L') '0' else '1' }
            val row = Integer.parseInt(rowBinary, 2)
            val column = Integer.parseInt(columnBinary, 2)

            (row, column)
        }
        .groupBy(_._1)
        .toList
        .sortBy(_._1)
        .map { case (row, column) =>
            (row, column.map(_._2).sorted)
        }

    groups.foreach(println)
}
