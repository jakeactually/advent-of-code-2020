import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

val text = Source.fromFile("16/input.txt").mkString.split("\r\n\r\n")

val fields = text(0).split("\r\n").toList.map { field =>
    val ranges = raw"\d+\-\d+".r.findAllIn(field).toList.map { range =>
        val Array(min, max) = range.split("-")
        min.toInt to max.toInt
    }

    (field.split(":")(0), ranges)
}

val myTicket = text(1).split("\r\n")(1).split(",").map(_.toInt)

val nearby = text(2)
    .split("\r\n")
    .toList
    .drop(1)
    .map(_.split(",").toList.map(_.toInt))
    
val valid = nearby.filter(tickets => !tickets.exists { ticket =>
    !fields.flatMap(_._2).exists(_.contains(ticket))
}).transpose

val data = fields.map { field =>
    val count = valid.zipWithIndex.filter { case (tickets, index) =>
        tickets.forall { ticket =>
            field._2.exists(_.contains(ticket))
        }
    }.map(_._2)

    (field._1, count)
}
.sortBy(_._2.size)

val result = for {
    i <- 0 until data.size - 1
    field = (data(i + 1)._1, (data(i + 1)._2.toSet -- data(i)._2.toSet).toList)
    if (field._1.contains("departure"))
} yield myTicket(field._2.head)

println(result.map(_.toLong).product)
