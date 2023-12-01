import java.lang.Exception

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            line.toCharArray().filter { c ->
                c.isDigit()
            }
        }.sumOf { line ->
            "${line.first()}${line.last()}".toInt()
        }
    }

    fun toStringNumber(number: String): String {
        return when(number) {
            "one" -> "1"
            "two" -> "2"
            "three" -> "3"
            "four" -> "4"
            "five" -> "5"
            "six" -> "6"
            "seven" -> "7"
            "eight" -> "8"
            "nine" -> "9"
            else -> number
        }
    }

    fun part2(input: List<String>): Int {
        val toMatch = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9",
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        var sum = 0
        for (line in input) {
            val first = line.findAnyOf(toMatch, 0)?.second ?: throw Exception("must work")
            val second = line.findLastAnyOf(toMatch, line.length - 1)?.second ?: throw Exception("must work")
            sum += (toStringNumber(first) + toStringNumber(second)).toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
