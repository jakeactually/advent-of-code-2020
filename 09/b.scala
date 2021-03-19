import scala.io.Source
import scala.collection.mutable

object Main extends App {
    val numbers =
        Source.fromFile("input.txt").getLines().toList.map(_.toLong)

    var start = 0
    var end = 0
    var sum = 0L
    var loop = true

    while (loop) {
        if (sum < 217430975) {
            end += 1
            sum += numbers(end)
        } else if (sum > 217430975) {
            start += 1
            sum -= numbers(start)
        } else {
            loop = false
            val slice = numbers.slice(start + 1, end + 1)
            println(slice.max + slice.min)
        }
    }
}
