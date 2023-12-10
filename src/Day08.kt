fun main() {

    data class Node(val start: String, val left: String, val right: String)

    fun part1(input: List<String>): Int {
        val directions = input[0]
        val nodeNetwork = input.drop(2)
            .map { it.replace(")", "").split(" = (", ", ") }
            .map { Node(it[0], it[1], it[2]) }
            .sortedBy { it.start }
        var steps = 0
        var currentPosition = 0
        var nextElement = nodeNetwork[0].start //AAA
        while (nextElement != "ZZZ") {
            val index = nodeNetwork.binarySearch { String.CASE_INSENSITIVE_ORDER.compare(it.start, nextElement) }
            nextElement = if (directions[currentPosition] == 'L') nodeNetwork[index].left else nodeNetwork[index].right
            currentPosition++
            if (currentPosition >= directions.length) currentPosition = 0
            steps++
        }
        return steps
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 6)
//    check(part2(testInput) == 1)

    val input = readInput("Day08")
    part1(input).println()
//    part2(input).println()
}
