import scala.io.Source
import scala.collection.mutable

object Main extends App {
    var mask = ""
    val regex = raw"\d+".r
    val mem = mutable.Map[BigInt, Long]()

    for (line <- Source.fromFile("input.txt").getLines()) {
        if (line.startsWith("mask")) {
            mask = line.split(" = ")(1)
        } else {
            val digits = regex.findAllIn(line).toList
            var indexes = List(BigInt(digits(0).toInt))

            for ((char, i) <- mask.zipWithIndex) {
                if (char == 'X') {
                    indexes = indexes.flatMap { index =>
                        List(                            
                            index | (BigInt(1) << (35 - i)),
                            index & ~(BigInt(1) << (35 - i))  
                        )
                    }
                }
                if (char == '1') {
                    indexes = indexes.map { index =>
                        index | (BigInt(1) << (35 - i))
                    }                  
                }
            }

            for (index <- indexes) {
                mem(index) = digits(1).toLong
            }
        }
    }
    
    println(mem.values.sum)
}
