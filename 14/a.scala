import scala.io.Source
import scala.collection.mutable

object Main extends App {
    var mask = ""
    val regex = raw"\d+".r
    var mem: Array[BigInt] = Array.fill(99999)(0)

    for (line <- Source.fromFile("input.txt").getLines()) {
        if (line.startsWith("mask")) {
            mask = line.split(" = ")(1)
        } else {
            val digits = regex.findAllIn(line).toList
            val index = digits(0).toInt
            mem(index) = digits(1).toLong

            for ((char, i) <- mask.zipWithIndex) {
                if (char == '1') {
                    mem(index) |= (BigInt(1) << (35 - i))                    
                }
                if (char == '0') {
                    mem(index) &= ~(BigInt(1) << (35 - i))                  
                }
            }
        }
    }

    
    println(mem.sum)
}
