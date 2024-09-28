import scala.util.parsing.combinator.RegexParsers
import scala.io.Source

enum Exp:
  case Number(a: Long)
  case Add(a: Exp, b: Exp)
  case Multiply(a: Exp, b: Exp)

object ExpressionParser extends RegexParsers {
  def number: Parser[Exp] = """\d+(\.\d*)?""".r ^^ { x => Exp.Number(x.toLong) }

  def factor: Parser[Exp] = number | "(" ~> expr <~ ")"

  def term: Parser[Exp] = factor ~ rep("+" ~ factor) ^^ { case num ~ list =>
    list.foldLeft(num) { case (x, "+" ~ y) =>
      Exp.Add(x, y)
    }
  }

  def expr: Parser[Exp] = term ~ rep("*" ~ term) ^^ { case num ~ list =>
    list.foldLeft(num) { case (x, "*" ~ y) =>
      Exp.Multiply(x, y)
    }
  }

  def parseExpression(expression: String): Exp =
    parseAll(expr, expression) match {
      case Success(result, _) => result
    }
}

def evaluate(expr: Exp): Long = expr match {
  case Exp.Number(value)         => value
  case Exp.Add(left, right)      => evaluate(left) + evaluate(right)
  case Exp.Multiply(left, right) => evaluate(left) * evaluate(right)
}

Source
  .fromFile("src/main/scala/18/input.txt")
  .getLines()
  .toList
  .map(evaluate.compose(ExpressionParser.parseExpression))
  .sum
