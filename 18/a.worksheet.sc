import scala.io.Source
import scala.collection.mutable
import java.util.ArrayList

enum Token:
    case Number(value: Long)
    case Op(value: Char)
    case Many(values: List[Token])
    case End

def getOne(str: List[Char]): (Token, List[Char]) =
    val (number, rest) = str.span(x => x >= '0' && x <= '9')

    if str.isEmpty then
        (Token.End, List())
    else if number.isEmpty then
        rest match
            case ' ' +: xs => getOne(xs)
            case '(' +: xs =>
                val (many, rest) = getMany(List(), xs)
                (Token.Many(many), rest)
            case ')' +: xs => (Token.End, xs)
            case _ => (Token.Op(str.head), str.tail)
    else
        (Token.Number(number.mkString.toLong), rest)

def getMany(buffer: List[Token], str: List[Char]): (List[Token], List[Char]) =
    val (token, rest) = getOne(str)

    if token == Token.End then
        (buffer, rest)
    else
        getMany(buffer :+ token, rest)

def pretty(token: Token, indent: Int): Unit = token match
    case Token.End => ()
    case Token.Many(values) =>
        println((" " * indent) + "(")
        values.foreach(pretty(_, indent + 1))
        println((" " * indent) + ")")
    case Token.Number(value) => println((" " * indent) + value)
    case Token.Op(value) => println((" " * indent) + value)

def solveOne(token: Token): Long = token match
    case Token.Many(values) => solveMany(values)
    case Token.Number(value) => value
    case _ => 0

def solveMany(tokens: List[Token]): Long = tokens match
    case a :: Token.Op('+') :: b :: xs => solveMany(Token.Number(solveOne(a) + solveOne(b)) :: xs)
    case a :: Token.Op('*') :: b :: xs => solveMany(Token.Number(solveOne(a) * solveOne(b)) :: xs)
    case a :: Nil => solveOne(a)
    case _ => 0

val values: List[Long] = Source
    .fromFile("18/input.txt")
    .getLines()
    .toList
    .map(line => solveMany(getMany(List(), line.toList)._1))

println(values.sum)
