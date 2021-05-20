import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

val arr = mutable.ArrayBuffer(9, 12, 1, 4, 17, 0)
var last = 18

for i <- (arr.size + 2) to 2020 do
    val secondLast = arr.lastIndexOf(last)
    arr += last

    if (secondLast == -1) then
        last = 0
    else
        last = i - secondLast - 2

println(last)
