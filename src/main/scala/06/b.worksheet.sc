import scala.io.Source

val text = Source.fromFile("06/input.txt").mkString
var count = 0

for group <- text.split("\r\n\r\n").toList do
    val answers = group.split("\r\n").map(_.split("").toSet).reduce(_ & _)
    count += answers.size

println(count)
