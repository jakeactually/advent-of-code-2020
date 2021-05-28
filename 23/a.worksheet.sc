import scala.io.Source
import scala.collection.mutable

extension [A](list: List[A])
    def rotateLeft(i: Int): List[A] =
        val size = list.size
        val (first, last) = list.splitAt(i % size)
        last ++ first

    def rotateRight(i: Int) =
        val size = list.size
        val (first, last) = list.splitAt(size - (i % size))
        last ++ first

    def center(n: Int, position: Int) =
        val offset = list.indexOf(n) - position

        if offset > 0 then list.rotateLeft(offset)
        else if offset < 0 then list.rotateRight(-offset)
        else list

var input = "712643589".toList.map(_.toString.toInt)
var current = 0

for _ <- 0 to 99 do
    val (a :: as, b) = input.rotateLeft(current).splitAt(4)
    val split :: _ = b.sortBy(x => if x < a then a - x else 20 - x)
    val (c, d :: ds) = (b :+ a).span(_ != split)
    
    input = (List(d) ++ as ++ ds ++ c).center(a, current)
    current += 1

input.center(1, 0).drop(1).mkString
