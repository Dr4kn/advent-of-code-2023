fun main() {

    data class Node(val start: String, val left: String, val right: String)

    fun parseInput(input: List<String>): Pair<String, List<Node>> {
        val directions = input[0]
        val nodeNetwork = input.drop(2)
            .map { it.replace(")", "").split(" = (", ", ") }
            .map { Node(it[0], it[1], it[2]) }
            .sortedBy { it.start }
        return Pair(directions, nodeNetwork)
    }

    fun part1(input: List<String>): Int {
        val (directions, nodeNetwork) = parseInput(input)
        var steps = 0
        var currentPosition = 0
        var nextElement = "AAA"
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
        val (directions, nodeNetwork) = parseInput(input)
        var steps = 1
        var currentPosition = 1
        val endsWithA = nodeNetwork.filter { it.start.last() == 'A' }
        var nextElement = if (directions[0] == 'L') {
            endsWithA.map { it.left }.toSet()
        } else {
            endsWithA.map { it.right }.toSet()
        }

        var index: Int
        while (true) {
            val next: MutableSet<String> = mutableSetOf()
            for (element in nextElement) {
                index = nodeNetwork.binarySearch { String.CASE_INSENSITIVE_ORDER.compare(it.start, element) }
                if (directions[currentPosition] == 'L') {
                    next.add(nodeNetwork[index].left)
                } else {
                    next.add(nodeNetwork[index].right)
                }
            }
            steps++
            if (next.count { it.last() == 'Z' } == next.size) return steps
            currentPosition++
            if (currentPosition >= directions.length) currentPosition = 0
            nextElement = next
            if (steps%1000000 == 0) {
                println(steps/1000000)
                nextElement.forEach { println(it) }
                println()
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 6)
    check(part2(testInput) == 6)

    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}
