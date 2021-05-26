import scala.io.Source
import scala.collection.mutable

val Array(p1, p2) = Source.fromFile("22/input.txt").mkString.split("\r\n\r\n")

var deck1 = p1.split("\r\n").drop(1).map(_.toInt).toList
var deck2 = p2.split("\r\n").drop(1).map(_.toInt).toList

while deck1.nonEmpty && deck2.nonEmpty do
    val top1 :: rest1 = deck1
    val top2 :: rest2 = deck2

    if top1 > top2 then
        deck1 = rest1 ++ List(top1, top2)
        deck2 = rest2

    if top1 < top2 then
        deck1 = rest1
        deck2 = rest2 ++ List(top2, top1)

deck1.reverse.zipWithIndex.map { case (n, i) => (i + 1) * n } .sum
