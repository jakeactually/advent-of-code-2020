import scala.io.Source

val text = Source.fromFile("06/input.txt").mkString
var count = 0

for group <- text.split("\r\n\r\n").toList do
    val answers = group.split("\r\n").flatMap(_.split("")).toList
    count += answers.toSet.size

println(count)
