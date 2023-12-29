import java.util.PriorityQueue

fun main() {
    data class Node(val row: Int, val col: Int, val heatloss: Int, var movedRow: Int, var movedCol: Int, val consecutiveMoves: Int)

    fun parseInput(input: List<String>) = input.map { line -> line.map { it.digitToInt() } }

    fun solver(heatMap: List<List<Int>>, part1: Boolean): Int {
        val seen = mutableSetOf<List<Int>>()
        val compareByHeatloss = compareBy<Node> { it.heatloss }
        val priorityQueue = PriorityQueue(compareByHeatloss)
        priorityQueue.add(Node(0, 0, 0, 0, 0, 0))
        val moveDirections = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))

        while (priorityQueue.isNotEmpty()) {
            val node = priorityQueue.remove()
            if (node.row == heatMap.size - 1 && node.col == heatMap[0].size -1) {
                if (part1) return node.heatloss
                if (node.consecutiveMoves >= 4) return node.heatloss
            }

            if (node.row < 0 || node.row >= heatMap.size || node.col < 0 || node.col >= heatMap[0].size) continue

            val seenList = listOf(node.row, node.col, node.movedRow, node.movedCol, node.consecutiveMoves)
            if (seen.contains(seenList)) continue // does not contain heatloss to prevent looping
            seen.add(seenList)

            if (Pair(node.movedRow, node.movedCol) != Pair(0, 0) && node.consecutiveMoves < 10) {
                val newRow = node.row + node.movedRow
                val newCol = node.col + node.movedCol
                if (0 <= newRow && newRow < heatMap.size && 0 <= newCol && newCol < heatMap[0].size) {
                    if ((part1 && node.consecutiveMoves < 3) || !part1) {
                        priorityQueue.add(
                            Node(
                                newRow, newCol, node.heatloss + heatMap[newRow][newCol],
                                node.movedRow, node.movedCol, node.consecutiveMoves + 1
                            )
                        )
                    }
                }
            }


            if ((!part1 && (Pair(node.movedRow, node.movedCol) == Pair(0, 0) || node.consecutiveMoves >= 4)) || part1) {
                for (direction in moveDirections) {
                    if (direction != Pair(node.movedRow, node.movedCol) && direction != Pair(
                            -node.movedRow,
                            -node.movedCol
                        )
                    ) {
                        val newRow = node.row + direction.first
                        val newCol = node.col + direction.second
                        if (0 <= newRow && newRow < heatMap.size && 0 <= newCol && newCol < heatMap[0].size) {
                            priorityQueue.add(
                                Node(
                                    newRow, newCol, node.heatloss + heatMap[newRow][newCol],
                                    direction.first, direction.second, 1
                                )
                            )
                        }
                    }
                }
            }
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        val answer = solver(parseInput(input), true)
        return answer
    }
    fun part2(input: List<String>): Int {
        val answer = solver(parseInput(input), false)
        return answer
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 102)
    check(part2(testInput) == 94)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
