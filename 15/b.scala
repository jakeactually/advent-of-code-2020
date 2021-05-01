import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

object Main extends App {
    val arr = Array.fill(30000000)(-1)
    arr(9) = 1
    arr(12) = 2
    arr(1) = 3
    arr(4) = 4
    arr(17) = 5
    arr(0) = 6
    var last = 18

    for (i <- 8 to 30000000) {
        val secondLast = arr(last)
        arr(last) = i - 1

        if (secondLast == -1) {
            last = 0
        } else {
            last = i - secondLast - 1
        }
    }

    println(last)
}
