fun main() {
    fun inputToMap(input: List<String>): List<List<Map<String, List<Int>>>> = input.map { game ->
        game.split(": ", "; ").drop(1).map {round ->
            round.split(", ")
        }.map { colorCombo ->
            colorCombo.groupBy(
                keySelector = { it.substringAfter(" ") },
                valueTransform = { it.substringBefore(" ").toInt() }
            )
        }
    }

    fun cubeNumberPossible(colors: Map<String, List<Int>>): Boolean {
        var possible: Boolean
        for (color in colors) {
            possible = when (color.key) {
                "blue" -> color.value.sum() <= 14
                "green" -> color.value.sum() <= 13
                "red" -> color.value.sum() <= 12
                else -> false
            }
            if (!possible) return false
        }
        return true
    }

    fun cubeNumberValues(colors: Map<String, List<Int>>): List<Int> {
        val cubeNumbers = List(3){0}.toMutableList()
        for (color in colors) {
            when (color.key) {
                "red" -> cubeNumbers[0] = color.value.sum()
                "green" -> cubeNumbers[1] = color.value.sum()
                "blue" -> cubeNumbers[2] = color.value.sum()
            }
        }
        return cubeNumbers.toList()
    }

    fun part1(input: List<String>): Int {
        var possibleGames = 0
        for ((gameNumber, game) in inputToMap(input).withIndex()) {
            for (i in game.indices) {
                if(!cubeNumberPossible(game[i])) break
                if(i == game.size - 1) possibleGames += gameNumber + 1
            }
        }
        return possibleGames
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        for (game in inputToMap(input)) {
            val lowestColorNumbers = MutableList(3) { 0 }
            for (round in game) {
                val colorNumbers = cubeNumberValues(round)
                for (i in colorNumbers.indices) {
                    if (colorNumbers[i] > lowestColorNumbers[i]) {
                        lowestColorNumbers[i] = colorNumbers[i]
                    }
                }
            }
            answer += lowestColorNumbers.reduce { acc, n -> acc * n }
        }
        return answer
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
