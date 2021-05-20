import scala.io.Source
import scala.collection.mutable

var mask = ""
val regex = raw"\d+".r
val mem = mutable.Map[BigInt, Long]()

for line <- Source.fromFile("14/input.txt").getLines() do
    if line.startsWith("mask") then
        mask = line.split(" = ")(1)
    else
        val digits = regex.findAllIn(line).toList
        var indexes = List(BigInt(digits(0).toInt))

        for (char, i) <- mask.zipWithIndex do
            if char == 'X' then
                indexes = indexes.flatMap { index =>
                    List(                            
                        index | (BigInt(1) << (35 - i)),
                        index & ~(BigInt(1) << (35 - i))  
                    )
                }
                
            if char == '1' then
                indexes = indexes.map { index =>
                    index | (BigInt(1) << (35 - i))
                }                  

        for index <- indexes do
            mem(index) = digits(1).toLong

println(mem.values.sum)
