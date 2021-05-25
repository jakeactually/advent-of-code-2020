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

val set = map.map { (k, v) => (k, v.map(_.toSet).reduce(_ & _)) } .values.reduce(_ | _)

ingredients.filter(!set.contains(_)).size
