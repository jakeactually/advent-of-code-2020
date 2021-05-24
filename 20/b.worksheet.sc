import scala.io.Source
import scala.collection.mutable

type Grid = List[List[Char]]

extension (block: Grid)
    def variants = List(
        block,
        block.reverse,
        block.map(_.reverse),
        block.reverse.map(_.reverse),
        block.transpose,
        block.transpose.reverse,
        block.transpose.map(_.reverse),
        block.transpose.reverse.map(_.reverse)
    )

val blocks = Source
    .fromFile("20/log.txt")
    .mkString
    .split("\n")
    .toList
    .map(_.toList)

val dragon = List(
"                  # ",
"#    ##    ##    ###",
" #  #  #  #  #  #   "
).map(_.toList)

var dragons = 0

for
    variant <- blocks.variants
    y <- 0 until 12 * 8 - 3
    x <- 0 until 12 * 8 - 20
do
    var count = 0

    for
        oy <- 0 until 3
        ox <- 0 until 20
    do
        if variant(y + oy)(x + ox) == '#' && dragon(oy)(ox) == '#' then
            count += 1
    
    if count == 15 then
        dragons += 1

blocks.flatten.filter(_ == '#').size - 15 * dragons
