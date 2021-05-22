import scala.io.Source
import scala.collection.mutable

type Grid = List[List[Char]]

extension (block: Grid)
    def top = block(0)
    def bottom = block(9)
    def left = block.map(_(0))
    def right = block.map(_(9))
    def four: Grid = List(top, bottom, left, right)
    def all: Grid = four ++ four.map(_.reverse)
    def compat(other: Grid): Boolean = (all.toSet & other.all.toSet).size > 0

val blocks = Source
    .fromFile("20/input.txt")
    .mkString
    .split("\r\n\r\n")
    .toList

val parsed = for block <- blocks yield
    val head :: body = block.split("\r\n").toList
    (head.split(" ")(1).dropRight(1).toInt, body.map(_.toList))

var pairs = for
    (k1, v1) <- parsed
    (k2, v2) <- parsed
    if k1 != k2 && (v1 compat v2)
yield
    (k1, k2)

pairs.groupBy(_._1).filter(_._2.size == 2).keys.map(_.toLong).product
