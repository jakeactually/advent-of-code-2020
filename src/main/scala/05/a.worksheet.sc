import scala.io.Source

val max = Source
    .fromFile("05/input.txt")
    .getLines()
    .map { line =>
        val (rowString, columnString) = line.splitAt(7)
        
        val rowBinary = rowString.map { c => if (c == 'F') '0' else '1' }
        val columnBinary = columnString.map { c => if (c == 'L') '0' else '1' }
        val row = Integer.parseInt(rowBinary, 2)
        val column = Integer.parseInt(columnBinary, 2)

        row * 8 + column
    }
    .max

println(max)
