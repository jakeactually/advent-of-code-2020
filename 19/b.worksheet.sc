// scala -J-Xmx3g a.scala

import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList
import java.io.FileWriter

enum Rule:
    case Refs(ids: List[Int])
    case Character(value: Char)
    case Or(ids: List[Rule])

val Array(rules, messages) = Source.fromFile("19/input.txt").mkString.split("\r\n\r\n")
val map = mutable.Map[Int, Rule]()

for rule <- rules.split("\r\n") do
    val Array(key, value) = rule.split(": ")

    map(key.toInt) = if (value.contains("|")) then
        Rule.Or(value.split(" \\| ").toList.map(part => Rule.Refs(part.split(" ").toList.map(_.toInt))))
    else if raw"\d".r.findFirstIn(value).isDefined then
        Rule.Refs(value.split(" ").toList.map(_.toInt))
    else
        Rule.Character(value(1))

def expand(rule: Rule): List[List[Char]] = rule match
    case Rule.Character(value) => List(List(value))
    case Rule.Refs(ids) => ids.map(id => {
            val value = expand(map(id))
            value
        }).reduce(cross(_, _))
    case Rule.Or(rules) => rules.flatMap(rule => expand(rule))

def cross[A](as: List[List[A]], bs: List[List[A]]): List[List[A]] =
    for a <- as; b <- bs yield a ++ b

val set = expand(map(0)).toSet
val msgList = messages.split("\r\n").map(_.toList).toList

val fw = new FileWriter("log.txt", true)    
set.foreach(item => fw.write(item.toString() + "\n"))
fw.close()
