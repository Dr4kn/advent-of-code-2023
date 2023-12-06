fun main() {

    fun parsePart1(input: List<String>): List<Pair<Int, Int>> {
        val parsedInput = input.map { line -> line.split("(: +)|( +)".toRegex())
            .drop(1)
            .map { it.toInt() }
        }
        return parsedInput[0] zip parsedInput [1]
    }

    fun part1(input: List<String>): Int {
        val parsedInput = parsePart1(input)
        var score = 1
        var waysToBeat: Int
        for (pair in parsedInput) {
            waysToBeat = 0
            for (timeHeld in 1..< pair.first) {
                val distance = timeHeld * (pair.first - timeHeld)
                if (distance > pair.second) waysToBeat++
            }
            score *= waysToBeat
        }
        return score
    }

    fun parsePart2(input: List<String>): Pair<Long, Long> {
        val parsedInput = input.map { line ->
            line.split("(: +)|( +)".toRegex())
                .drop(1)
                .fold(StringBuilder()) { sB, number -> sB.append(number) }.toString()
                .toLong()
        }
        return Pair(parsedInput[0], parsedInput[1])
    }

    // there is 100% some math formula that does this which I might come up with later or don't
    fun part2(input: List<String>): Long {
        val parsedInput = parsePart2(input)
        var waysToBeat: Long = 0
        var distance: Long
        for (timeHeld in 1 ..< parsedInput.first) {
            distance = timeHeld * (parsedInput.first - timeHeld)
            if (distance > parsedInput.second) waysToBeat++
        }
        return waysToBeat
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503.toLong())

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
