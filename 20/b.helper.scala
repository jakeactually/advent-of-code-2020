import scala.io.Source
import scala.collection.mutable
import java.io.FileWriter

type Grid = List[List[Char]]

extension (block: Grid)
    def top = block(0)
    def bottom = block(9)
    def left = block.map(_(0))
    def right = block.map(_(9))
    def four: Grid = List(top, bottom, left, right)
    def all: Grid = four ++ four.map(_.reverse)
    def compat(other: Grid): Boolean = (all.toSet & other.all.toSet).size > 0
    def variants = List(
        block,
        block.reverse,
        block.map(_.reverse),
        block.reverse.map(_.reverse),
        block.transpose,
        block.transpose.reverse,
        block.transpose.map(_.reverse),
        block.transpose.reverse.map(_.reverse)
    )
    def joinRight(other: Grid): Option[Grid] =
        other.variants.find(variant => right == variant.left)
    
    def joinBottom(other: Grid): Option[Grid] =
        other.variants.find(variant => bottom == variant.top)

def solve =
    val blocks = Source
        .fromFile("20/input.txt")
        .mkString
        .split("\r\n\r\n")
        .toList

    val parsed = for block <- blocks yield
        val head :: body = block.split("\r\n").toList
        (head.split(" ")(1).dropRight(1).toInt, body.map(_.toList))

    val imageMap = Map.from(parsed)

    var pairs = for
        (k1, v1) <- parsed
        (k2, v2) <- parsed
        if k1 != k2 && (v1 compat v2)
    yield
        (k1, k2)

    val edgeMap = pairs.groupBy(_._1).map((k, v) => (k, v.map(_._2)))

    def row(id: Int, body: Grid, depth: Int): List[Grid] = if depth == 1 then
        List(body)
    else
        val Some(id2) = edgeMap(id) find { id2 =>
            body.joinRight(imageMap(id2)).isDefined
        }
        val Some(variant) = body.joinRight(imageMap(id2))
        body :: row(id2, variant, depth - 1)

    def column(id: Int, body: Grid, depth: Int): List[(Int, Grid)] = if depth == 1 then
        List((id, body))
    else
        val Some(id2) = edgeMap(id) find { id2 =>
            body.joinBottom(imageMap(id2)).isDefined
        }
        val Some(variant) = body.joinBottom(imageMap(id2))
        (id, body) :: column(id2, variant, depth - 1)

    val data = column(3931, imageMap(3931), 12) map { case (id, body) =>
        row(id, body, 12)
    }

    val trimmed = data.map(_.map(_.map(_.slice(1, 9)).slice(1, 9)))

    val fw = new FileWriter("20/log.txt", true)

    for
        y <- 0 until 12 * 8
        x <- 0 until 12 * 8
    do
        fw.write(trimmed(y / 8)(x / 8)(y % 8)(x % 8))

        /*if x % 8 == 7 then
            fw.write(" ")*/

        if x == 12 * 8 - 1 then
            /*if y % 8 == 7 then
                fw.write("\n")*/
            fw.write("\n")

    fw.close()
