import scala.io.Source
import scala.collection.mutable

var wx = 10
var wy = -1
var x = 0
var y = 0
var a = 0
val regex = raw"(\w)(\d+)".r

for line <- Source.fromFile("12/input.txt").getLines() do
    val regex(char, number) = line
    val n = number.toInt
    
    char(0) match {
        case 'N' => wy -= n
        case 'S' => wy += n
        case 'E' => wx += n
        case 'W' => wx -= n
        case 'R' => {
            val (nx, ny) = (0 until n / 90).foldLeft((wx, wy))(rotateR)
            wx = nx
            wy = ny
        }
        case 'L' =>  {                
            val (nx, ny) = (0 until n / 90).foldLeft((wx, wy))(rotateL)
            wx = nx
            wy = ny
        }
        case 'F' => {
            x += wx * n
            y += wy * n
        }
    }

    println((wx, wy, x, y))

def rotateR(tuple: (Int, Int), index: Int): (Int, Int) = (-tuple._2, tuple._1)
def rotateL(tuple: (Int, Int), index: Int): (Int, Int) = (tuple._2, -tuple._1)

println(Math.abs(x) + Math.abs(y))
