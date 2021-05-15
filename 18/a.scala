import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

sealed abstract class Token
case class Number(value: Long) extends Token
case class Op(value: Char) extends Token
case class Many(values: List[Token]) extends Token
case object End extends Token

object Main extends App {
    def getOne(str: List[Char]): (Token, List[Char]) = {
        val (number, rest) = str.span(x => x >= '0' && x <= '9')

        if (str.isEmpty) {
            (End, List())
        } else if (number.isEmpty) {
            rest match {
                case ' ' +: xs => getOne(xs)
                case '(' +: xs => {
                    val (many, rest) = getMany(List(), xs)
                    (Many(many), rest)
                }
                case ')' +: xs => {
                    (End, xs)
                }
                case _ => (Op(str.head), str.tail)
            }
        } else {
            (Number(number.mkString.toLong), rest)
        }
    }

    def getMany(buffer: List[Token], str: List[Char]): (List[Token], List[Char]) = {
        val (token, rest) = getOne(str)

        if (token == End) {
            (buffer, rest)
        } else {
            getMany(buffer :+ token, rest)
        }
    }

    def pretty(token: Token, indent: Int): Unit = token match {
        case End => ()
        case Many(values) => {
            println((" " * indent) + "(")
            values.foreach(pretty(_, indent + 1))
            println((" " * indent) + ")")
        }
        case Number(value) => println((" " * indent) + value)
        case Op(value) => println((" " * indent) + value)
    }

    def solveOne(token: Token): Long = token match {
        case Many(values) => solveMany(values)
        case Number(value) => value
        case _ => 0
    }

    def solveMany(tokens: List[Token]): Long = tokens match {
        case a :: Op('+') :: b :: xs => solveMany(Number(solveOne(a) + solveOne(b)) :: xs)
        case a :: Op('*') :: b :: xs => solveMany(Number(solveOne(a) * solveOne(b)) :: xs)
        case a :: Nil => solveOne(a)
        case _ => 0
    }
    
    val values: List[Long] = Source
        .fromFile("input.txt")
        .getLines()
        .toList
        .map(line => solveMany(getMany(List(), line.toList)._1))

    println(values.sum)
}
