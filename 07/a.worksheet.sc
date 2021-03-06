import scala.io.Source
import scala.collection.mutable

val graph: mutable.Map[String, Seq[String]] = mutable.Map()

for line <- Source.fromFile("07/input.txt").getLines() do
    val Array(left, right) = line.split(" contain ")

    for bag <- right.split(", ").map(_.split(" bag")(0).substring(2)) do
        if (!graph.contains(bag)) then
            graph(bag) = Seq()

        graph(bag) :+= left.split(" bags")(0)

var walkers = Seq("shiny gold")
var result: Set[String] = Set()

while !walkers.isEmpty do
    walkers = walkers
        .flatMap(x => graph.get(x).getOrElse(Seq()))
        .filter(x => !result.contains(x))
    result |= walkers.toSet

println(result.size)
