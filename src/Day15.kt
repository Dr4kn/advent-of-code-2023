import java.lang.Exception

fun main() {
    fun part1(input: List<String>): Int {
        val parsed = input.map { line ->
            line.split(',').map  hash@{ hashString ->
                hashString.fold(0) {acc, char ->
                    ((acc + char.code) * 17) % 256
                }
            }
        }.map { line -> line.reduce{sum, element -> sum + element} }.first()
        return parsed
    }
    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
//    check(part2(testInput) == 281)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
