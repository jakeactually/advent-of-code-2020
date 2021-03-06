import scala.io.Source

object Main extends App {
    val text = Source.fromFile("input.txt").mkString
    var count = 0

    for (group <- text.split("\r\n\r\n").toList) {
        val answers = group.split("\r\n").map(_.split("").toSet).reduce(_ & _)
        count += answers.size
    }

    println(count)
}
