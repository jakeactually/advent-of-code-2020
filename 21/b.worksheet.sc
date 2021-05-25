import scala.io.Source
import scala.collection.mutable

val map = mutable.Map[String, List[List[String]]]()
val ingredients = mutable.ListBuffer[String]()

for line <- Source.fromFile("21/input.txt").getLines() do
    val Array(left, right) = line.split("\\(")
    val food = left.split(" ").toList
    val allergens = right.drop(9).dropRight(1).split(", ").toList
    
    ingredients.addAll(food)

    for item <- allergens do
        if map.contains(item) then
            map(item) ::=  food
        else
            map(item) = List(food)

var set = map.map { (k, v) => (k, v.map(_.toSet).reduce(_ & _)) }
val pairs = mutable.ListBuffer[(String, String)]()

while set.exists(_._2.nonEmpty) do
    val Some((k, v)) = set.find(_._2.size == 1)
    pairs.addOne((k, v.head))
    set = set.map { (k2, v2) => (k2, v2 - v.head) }

pairs.sortBy(_._1).map(_._2).mkString(",")
