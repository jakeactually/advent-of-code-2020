import scala.io.Source
import scala.collection.mutable

val graph: mutable.Map[String, Seq[(Int, String)]] = mutable.Map()

for line <- Source.fromFile("07/input.txt").getLines() do
    val Array(left, right) = line.split(" contain ")

    graph(left.split(" ").take(2).mkString(" ")) = right.split(", ").map(line => {
        line.split(" ").toSeq match {
            case x +: xs => (if (x == "no") 0 else x.toInt, xs.take(2).mkString(" "))
        }            
    }).toSeq.filter(_._1 > 0)

var count = 0
var walkers = Seq("shiny gold")

while !walkers.isEmpty do
    var newWalkers = Seq[String]()

    for w <- walkers do
        if graph.contains(w) && graph(w).size > 0 then
            newWalkers ++= graph(w).flatMap {
                case (n, s) => List.fill(n)(s)
            }
        
        count += 1

    walkers = newWalkers

println(count - 1)
