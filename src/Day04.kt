import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        return input.asSequence().map { card ->
            card.split("(Card +\\d+: +)|( \\| +)".toRegex())
                .drop(1)
                .map {numberList ->
                    numberList.split(" +".toRegex()).map { it.toInt() }
            }
        }.map { bothSides ->
            (bothSides[0].toSet() intersect bothSides[1].toSet()).size
        }.filter { matchingNumbers
            -> matchingNumbers != 0
        }.map { matchingNumbers ->
                ((2.0).pow(matchingNumbers - 1)).toInt()
        }.sumOf { it }
    }

    data class Scratchcard(val cardNumber: Int, val matchingNumbers: Int, var copies: Int)

    fun part2(input: List<String>): Int {
        val regex = "((Card +)|(: +)|( \\| +))".toRegex()
        val scratchcards = input.map { card ->
            card.split(regex).drop(1).map { numberList ->
                numberList.split(" +".toRegex()).map { it.toInt() }
            }
        }.map {card ->
            val matchingNumber = (card[1].toSet() intersect card[2].toSet()).size
            Scratchcard(card[0][0], matchingNumber, 1)
        }

        // kinda scuffed but whatever
        for (scratchcard in scratchcards) {
            if (scratchcard.matchingNumbers != 0) {
                for (i in scratchcard.cardNumber..< scratchcard.cardNumber + scratchcard.matchingNumbers) {
                    scratchcards[i].copies += scratchcard.copies
                }
            }
        }
        return scratchcards.sumOf { it.copies }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
