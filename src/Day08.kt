import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.math.BigInteger

fun main() {

    data class Node(val start: String, val left: String, val right: String)

    fun List<Int>.lcm(): BigInteger {
        val bigIntList = this.map { it.toBigInteger() }
        var lcm = bigIntList[0]

        for (number in bigIntList.subList(1, bigIntList.size)) {
            lcm = (lcm.multiply(number)).divide(lcm.gcd(number))
        }

        return lcm
    }

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

    fun part2(input: List<String>): Long {
        val (directions, nodeNetwork) = parseInput(input)
        val endsWithA = nodeNetwork.filter { it.start.last() == 'A' }
        val answer = runBlocking(Dispatchers.Default) {
            val allLoops = endsWithA.map { loop ->
                async {
                    var steps = 0
                    var currentPosition = 0
                    var nextElement = loop.start
                    var stepsToFirstZ = 0
                    val stepsToSameZ: Int
                    var firstZ = ""
                    val loopLength: Int
                    while (true) {
                        val index = nodeNetwork.binarySearch { String.CASE_INSENSITIVE_ORDER.compare(it.start, nextElement) }
                        nextElement = if (directions[currentPosition] == 'L') nodeNetwork[index].left else nodeNetwork[index].right
                        currentPosition++
                        if (currentPosition >= directions.length) currentPosition = 0
                        steps++
                        if (nextElement.last() != 'Z') continue
                        if (stepsToFirstZ == 0) {
                            firstZ = nextElement
                            stepsToFirstZ = steps
                            steps = 0
                            continue
                        }
                        if (nextElement != firstZ) continue
                        stepsToSameZ = steps
                        loopLength = stepsToSameZ
                        break
                    }
                    loopLength
                }
            }.awaitAll()

            return@runBlocking allLoops.lcm()
        }

        return answer.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 6)
    check(part2(testInput) == 6.toLong())

    val input = readInput("Day08")
//    part1(input).println()
    part2(input).println()
}
