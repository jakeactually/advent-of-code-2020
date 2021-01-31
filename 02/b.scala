import scala.io.Source

object Day1A extends App {
    val lines = Source.fromFile("input.txt").getLines().toList
    val regex = raw"(\d{1,2})-(\d{1,2}) (\w): (\w+)".r
    var count = 0
    
    for (line <- lines) {
        val regex(i1, i2, char, password) = line

        if ((password(i1.toInt - 1) == char(0)) != (password(i2.toInt - 1) == char(0)) ) {
            count += 1
        }
    }

    println(count)
}
