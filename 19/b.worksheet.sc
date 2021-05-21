import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList
import java.io.{FileWriter, File}
import java.nio.file.{Files, Path} 
import scala.util.matching.Regex

enum Rule:
    case Refs(ids: List[Int])
    case Character(value: Char)
    case Or(ids: List[Rule])

val Array(rules, messages) = Source.fromFile("19/input.txt").mkString.split("\r\n\r\n")
val map = mutable.Map[Int, Rule]()  

def expand(rule: Rule): List[List[Char]] = rule match
        case Rule.Character(value) => List(List(value))
        case Rule.Refs(ids) => ids.map(id => {
                if id == 42 || id == 31 then
                    expand(map(id)).map(list => List('<') ++ list ++ List('>'))
                else
                    expand(map(id))
            }).reduce(cross(_, _))
        case Rule.Or(rules) => rules.flatMap(rule => expand(rule))

def cross[A](as: List[List[A]], bs: List[List[A]]): List[List[A]] =
    for a <- as; b <- bs yield a ++ b

for rule <- rules.split("\r\n") do
    val Array(key, value) = rule.split(": ")

    map(key.toInt) = if (value.contains("|")) then
        Rule.Or(value.split(" \\| ").toList.map(part => Rule.Refs(part.split(" ").toList.map(_.toInt))))
    else if raw"\d".r.findFirstIn(value).isDefined then
        Rule.Refs(value.split(" ").toList.map(_.toInt))
    else
        Rule.Character(value(1))

val a = expand(map(42)).map(_.mkString)
val b = expand(map(31)).map(_.mkString)
val msgList = messages.split("\r\n").toList

val valid = msgList.filter { msg =>
    val chunks = msg.grouped(8).toList
    val size = chunks.size
    val offset = 1 - size % 2

    (0 until (chunks.size / 2) - offset).exists { i =>
        val indexes =
            (0 to i).map(j => b contains chunks(chunks.size - j - 1)) ++
            (0 to size - i - 2).map(j => a contains chunks(j))

        indexes.forall(identity)
    }
}

println(valid.length)
