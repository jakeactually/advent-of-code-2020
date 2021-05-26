import scala.io.Source
import scala.collection.mutable

def game(inputDeck1: List[Int], inputDeck2: List[Int]): Either[List[Int], List[Int]] =
    var deck1 = inputDeck1
    var deck2 = inputDeck2

    val set = mutable.Set[(List[Int], List[Int])]()

    while deck1.nonEmpty && deck2.nonEmpty do    
        if set.contains((deck1, deck2)) then
            return Right(deck1)
        
        set.add((deck1, deck2))

        val top1 :: rest1 = deck1
        val top2 :: rest2 = deck2

        if top1 <= rest1.size && top2 <= rest2.size then
            game(rest1.take(top1), rest2.take(top2)) match
                case Right(_) =>
                    deck1 = rest1 ++ List(top1, top2)
                    deck2 = rest2
                case Left(_) =>  
                    deck1 = rest1
                    deck2 = rest2 ++ List(top2, top1)
        else
            if top1 > top2 then
                deck1 = rest1 ++ List(top1, top2)
                deck2 = rest2

            if top1 < top2 then
                deck1 = rest1
                deck2 = rest2 ++ List(top2, top1)
        
    if deck1.isEmpty then
        Left(deck2)
    else
        Right(deck1)

val Array(p1, p2) = Source.fromFile("22/input.txt").mkString.split("\r\n\r\n")
val deck1 = p1.split("\r\n").drop(1).map(_.toInt).toList
val deck2 = p2.split("\r\n").drop(1).map(_.toInt).toList

val winner = game(deck1, deck2) match
    case Right(deck) => deck
    case Left(deck) => deck

winner.reverse.zipWithIndex.map { case (n, i) => (i + 1) * n } .sum
