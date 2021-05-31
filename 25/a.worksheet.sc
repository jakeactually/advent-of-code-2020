import scala.io.Source
import scala.collection.mutable

def transform(subject: Long, loopSize: Int) =
    (0 until loopSize).foldLeft(1L)((a, _) => a * subject % 20201227)


def loop(goal: Long) =
    var start = 1L
    var size = 0

    while start != goal do
        start = start * 7 % 20201227
        size += 1
    
    size

val card = 10604480
val door = 4126658

transform(card, loop(door))
