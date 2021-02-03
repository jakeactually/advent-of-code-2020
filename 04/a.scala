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

        val valid = requirements.forall(requirement =>
            tokens.exists(token =>
                token.contains(requirement)
            )
        )

        if (valid) count += 1
    }

    println(count)
}
