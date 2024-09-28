import scala.util.parsing.combinator.RegexParsers

object ExpressionParser extends RegexParsers {
  def number: Parser[Double] = """\d+(\.\d*)?""".r ^^ { _.toDouble }

  def factor: Parser[Double] = number | "(" ~> expr <~ ")"

  def term: Parser[Double] = factor ~ rep("*" ~ factor | "/" ~ factor) ^^ {
    case num ~ list =>
      list.foldLeft(num) {
        case (x, "*" ~ y) => x * y
        case (x, "/" ~ y) => x / y
      }
  }

  def expr: Parser[Double] = term ~ rep("+" ~ term | "-" ~ term) ^^ {
    case num ~ list =>
      list.foldLeft(num) {
        case (x, "+" ~ y) => x + y
        case (x, "-" ~ y) => x - y
      }
  }

  def parseExpression(expression: String): Either[String, Double] =
    parseAll(expr, expression) match {
      case Success(result, _) => Right(result)
      case Failure(msg, _)    => Left(s"Parsing failed: $msg")
      case Error(msg, _)      => Left(s"Error: $msg")
    }
}

val expression = "1 + 1 + (2 * 2) + 1 + 1"
val result = ExpressionParser.parseExpression(expression)

result match {
  case Right(value) => println(s"Result: $value")
  case Left(error)  => println(s"Error: $error")
}
