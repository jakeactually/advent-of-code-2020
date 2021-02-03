import scala.io.Source

object Main extends App {
    val text = Source.fromFile("input.txt").mkString

    val requirements = List(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    );

    var count = 0

    for (passport <- text.split("\r\n\r\n").toList) {
        val tokens = passport.split("\r\n").flatMap(_.split(" ")).toList

        val valid = requirements.forall(requirement => {
            tokens.find(_.contains(requirement)).map(token => { 
                val Array(key, value) = token.split(":")
                Util.validations(key)(value)
            }).getOrElse(false)
        })

        if (valid) count += 1
    }

    println(count)
}

object Util {
    val height = "^(\\d+)(cm|in)$".r
    val validations: Map[String, String => Boolean] = Map(
        "byr" -> byr,
        "iyr" -> iyr,
        "eyr" -> eyr,
        "hgt" -> hgt,
        "hcl" -> hcl,
        "ecl" -> ecl,
        "pid" -> pid
    )

    def byr(s: String): Boolean = (1920 to 2002).contains(s.toInt)
    def iyr(s: String): Boolean = (2010 to 2020).contains(s.toInt)
    def eyr(s: String): Boolean = (2020 to 2030).contains(s.toInt)

    def hgt(s: String): Boolean = {
        s match {
            case height(number, unit) => {
                val range = if (unit == "cm") (150 to 193) else (59 to 76)
                range.contains(number.toInt)
            }
            case _ => false
        }
    }

    def hcl(s:String): Boolean = "^#[0-9a-fA-F]{6}$".r.findFirstIn(s).isDefined
    def ecl(s:String): Boolean = List("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(s)
    def pid(s:String): Boolean = "^[0-9]{9}$".r.findFirstIn(s).isDefined
}
