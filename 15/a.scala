import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

object Main extends App {
    val arr = mutable.ArrayBuffer(9, 12, 1, 4, 17, 0)
    var last = 18

    for (i <- (arr.size + 2) to 2020) {
        val secondLast = arr.lastIndexOf(last)
        arr += last

        if (secondLast == -1) {
            last = 0
        } else {
            last = i - secondLast - 2
        }
    }

    println(last)
}
