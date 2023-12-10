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

    fun cardValue(cardChar: Char, withJoker: Boolean): Int {
        if (withJoker) return when (cardChar) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> cardChar.digitToInt()
        }
        return when (cardChar) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> cardChar.digitToInt()
        }
    }

    data class Hand(val cards: List<Int>, val bid: Int, var typeOfHand: TypeOfHand)

    fun fullHouseOrFourOfAKind(hand: List<Int>): TypeOfHand {
        hand.toSet().forEach { card ->
            if (hand.count { it == card } == 3) return TypeOfHand.FULL_HOUSE
        }
        return TypeOfHand.FOUR_OF_A_KIND
    }

    fun threeOrTwoOfAKind(hand: List<Int>): TypeOfHand {
        hand.toSet().forEach { card ->
            if (hand.count { it == card } == 3) return TypeOfHand.THREE_OF_A_KIND
        }
        return TypeOfHand.TWO_PAIR
    }

    fun sortHands(hands: List<Hand>): List<Hand> {
        return hands.sortedWith(compareByDescending<Hand> { it.typeOfHand }
            .thenBy { it.cards[0] }
            .thenBy { it.cards[1] }
            .thenBy { it.cards[2] }
            .thenBy { it.cards[3] }
            .thenBy { it.cards[4] }
        )
    }

    fun winningsPerHand(hands: List<Hand>) = hands.mapIndexed { index, hand -> hand.bid * (index + 1) }

    fun bestJokerUsage(hand: List<Int>): List<Int> {
        val jokers = hand.count { it == 1 }
        val replacedWithHighest = hand.map { if (it == 1) hand.toSet().sortedDescending()[0] else it }
        if (jokers == 1 || jokers == 2) {
            when (hand.toSet().size) {
                5 -> return replacedWithHighest
                4, 3, 2 -> {
                    if (hand.toSet().size == 4 && jokers == 2) return replacedWithHighest
                    val xOfAKindCards  = hand.sortedDescending()
                        .groupingBy { it }
                        .eachCount()
                        .filter { it.value > 1 }
                        .toList()[0].first
                    return hand.map { card -> if (card == 1) xOfAKindCards else card }
                }
            }
        }

        if (jokers == 4 || jokers == 3) return replacedWithHighest
        return hand
    }

    fun inputToHand(hand: List<String>, withJoker: Boolean): Hand {
        val bid = hand[1].toInt()
        val cardValue = hand[0].map { cardValue(it, withJoker) }
        val cards = if (withJoker) bestJokerUsage(cardValue) else cardValue
        return when (cards.toSet().size) {
            1 -> Hand(cardValue, bid, TypeOfHand.FIVE_OF_A_KIND)
            2 -> Hand(cardValue, bid, fullHouseOrFourOfAKind(cards))
            3 -> Hand(cardValue, bid, threeOrTwoOfAKind(cards))
            4 -> Hand(cardValue, bid, TypeOfHand.ONE_PAIR)
            5 -> Hand(cardValue, bid, TypeOfHand.HIGH_CARD)
            else -> Hand(cardValue, bid, TypeOfHand.FULL_HOUSE)
        }
    }

    fun parsedInput(input: List<String>, withJoker: Boolean): List<Hand> {
        return input.map { hand -> hand.split(""" +""".toRegex()) }
            .map { hand -> inputToHand(hand, withJoker) }
    }

    fun solver(input: List<String>, withJoker: Boolean): Int {
        val parsedInput = parsedInput(input, withJoker)
        return winningsPerHand(sortHands(parsedInput)).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(solver(testInput, false) == 6440)
    check(solver(testInput, true) == 5905)

    val input = readInput("Day07")
    solver(input, false).println()
    solver(input, true).println()
}