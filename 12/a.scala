import scala.io.Source
import scala.collection.mutable

object Main extends App {
    var x = 0
    var y = 0
    var a = 0
    val regex = raw"(\w)(\d+)".r

    for (line <- Source.fromFile("input.txt").getLines()) {
        val regex(char, number) = line
        val n = number.toInt
        
        char(0) match {
            case 'N' => y -= n
            case 'S' => y += n
            case 'E' => x += n
            case 'W' => x -= n
            case 'R' => a = (a + 360 - n) % 360
            case 'L' => a = (a + n) % 360
            case 'F' => a match {
                case 0 => x += n
                case 90 => y -= n
                case 180 => x -= n
                case 270 => y += n
            }
        }
    }

    println(Math.abs(x) + Math.abs(y))
}
