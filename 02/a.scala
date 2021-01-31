import scala.io.Source

object Day1A extends App {
    val lines = Source.fromFile("input.txt").getLines().toList
    val regex = raw"(\d{1,2})-(\d{1,2}) (\w): (\w+)".r
    var count = 0
    
    for (line <- lines) {
        val regex(min, max, char, password) = line
        val amount = password.split("").filter(_ == char).size

        if ((min.toInt to max.toInt).contains(amount)) {
            count += 1
        }
    }

    println(count)
}
