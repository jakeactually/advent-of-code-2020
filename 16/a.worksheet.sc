import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

val text = Source.fromFile("16/input.txt").mkString.split("\r\n\r\n")

val ranges = raw"\d+\-\d+".r.findAllIn(text(0)).toList.map { range =>
    val Array(min, max) = range.split("-")
    min.toInt to max.toInt
}

val nearby = text(2)
    .split("\r\n")
    .toList
    .drop(1)
    .flatMap(_.split(",")
    .toList
    .map(_.toInt))

println(nearby.filter(ticket => !ranges.exists(_.contains(ticket))).sum)
