enum class TypeOfHand(number: Int) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5), // hack to make initial assignment easier
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0)
}

fun main() {

    fun cardValue(cardChar: Char, part1: Boolean): Int {
        if (part1) return when (cardChar) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> cardChar.digitToInt()
        }
        return when (cardChar) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> cardChar.digitToInt()
        }
    }

    data class Hand(val cards: List<Int>, val bid: Int, var typeOfHand: TypeOfHand)

    fun fullHouseOrFourOfAKind(hand: String): TypeOfHand {
        hand.toSet().forEach { card ->
            if (hand.count { it == card } == 3) return TypeOfHand.FULL_HOUSE
        }
        return TypeOfHand.FOUR_OF_A_KIND
    }

    fun threeOrTwoOfAKind(hand: String): TypeOfHand {
        hand.toSet().forEach { card ->
            if (hand.count { it == card } == 3) return TypeOfHand.THREE_OF_A_KIND
        }
        return TypeOfHand.TWO_PAIR
    }

    fun sortHands(parsedInput: List<Hand>): List<Hand> {
        return parsedInput.sortedWith(compareByDescending<Hand> { it.typeOfHand }
            .thenBy { it.cards[0] }
            .thenBy { it.cards[1] }
            .thenBy { it.cards[2] }
            .thenBy { it.cards[3] }
            .thenBy { it.cards[4] }
        )
    }

    fun winningsPerHand(hands: List<Hand>) = hands.mapIndexed { index, hand -> hand.bid * (index + 1) }

    fun part1(input: List<String>): Int {
        val parsedInput = input.map { hand -> hand.split(""" +""".toRegex()) }
            .map { hand ->
                val bid = hand[1].toInt()
                val cards = hand[0].map { cardValue(it, true) }
                when (hand[0].toSet().size) {
                    1 -> Hand(cards, bid, TypeOfHand.FIVE_OF_A_KIND)
                    2 -> Hand(cards, bid, fullHouseOrFourOfAKind(hand[0]))
                    3 -> Hand(cards, bid, threeOrTwoOfAKind(hand[0]))
                    4 -> Hand(cards, bid, TypeOfHand.ONE_PAIR)
                    5 -> Hand(cards, bid, TypeOfHand.HIGH_CARD)
                    else -> Hand(cards, hand[1].toInt(), TypeOfHand.FULL_HOUSE)
                }
            }

        return winningsPerHand(sortHands(parsedInput)).sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
//    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
//    part1(input).println()
//    part2(input).println()
}