import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

fun main() {

    data class Tile(val row: Int, val column: Int)
    fun getStart(input: List<String>): Tile {
        input.forEachIndexed { index, line ->
            if (line.contains('S')) return Tile(index, line.indexOf('S'))
        }
        return Tile(-1, -1)
    }

    fun part1(input: List<String>): Int {
        val start = getStart(input)
        var row = start.row
        var column = start.column
        val loopStarts = MutableList(0) { Tile(0, 0) }
        if (start.row != 0) loopStarts.add(Tile(start.row - 1, start.column))
        if (start.row < input.size - 1) loopStarts.add(Tile(start.row + 1, start.column))
        if (start.column != 0) loopStarts.add(Tile(start.row, start.column - 1))
        if (start.column < input[0].length - 1) loopStarts.add(Tile(start.row, start.column + 1))


        val answer = runBlocking(Dispatchers.Default) {
            val loopLengths: List<Int> = loopStarts.map { loop ->
                async {
                    var length = 0
                    var prev = start
                    var curr = loop
                    val walkedPath = MutableList(0){'a'}
                    while (true) {
                        walkedPath.add(input[curr.row][curr.column])
                        length++
                        when (input[curr.row][curr.column]) {
                            '.' -> return@async -1
                            'S' -> {
                                kotlin.io.println(walkedPath)
                                return@async length
                            }

                            '|' -> if (prev.row - 1 == curr.row) {
                                prev = curr
                                curr = Tile(curr.row - 1, curr.column)
                            } else if (prev.row + 1 == curr.row) {
                                prev = curr
                                curr = Tile(curr.row + 1, curr.column)
                            } else return@async -1

                            'F' -> if (prev.column - 1 == curr.column) {
                                prev = curr
                                curr = Tile(curr.row + 1, curr.column)
                            } else if (prev.row - 1 == curr.row) {
                                prev = curr
                                curr = Tile(curr.row, curr.column + 1)
                            } else return@async -1

                            '7' -> if (prev.column + 1 == curr.column) {
                                prev = curr
                                curr = Tile(curr.row + 1, curr.column)
                            } else if (prev.row - 1 == curr.row) {
                                prev = curr
                                curr = Tile(curr.row, curr.column - 1)
                            } else return@async -1

                            'J' -> if (prev.column + 1 == curr.column) {
                                prev = curr
                                curr = Tile(curr.row - 1, curr.column)
                            } else if (prev.row + 1 == curr.row) {
                                prev = curr
                                curr = Tile(curr.row, curr.column - 1)
                            } else return@async -1

                            '-' -> if (prev.column - 1 == curr.column) {
                                prev = curr
                                curr = Tile(curr.row, curr.column - 1)
                            } else if (prev.column + 1 == curr.column) {
                                prev = curr
                                curr = Tile(curr.row, curr.column + 1)
                            } else return@async -1

                            'L' -> if (prev.row + 1 == curr.row) {
                                prev = curr
                                curr = Tile(curr.row, curr.column + 1)
                            } else if (prev.column - 1 == curr.column) {
                                prev = curr
                                curr = Tile(curr.row - 1, curr.column)
                            } else return@async -1
                        }
                        // out of bounds check
                        if (curr.row < 0 || curr.row >= input.size) return@async - 1
                        if (curr.column < 0 || curr.column >= input[0].length) return@async - 1
                    }
                }
            }.awaitAll() as List<Int>
            return@runBlocking loopLengths.max() / 2
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 8)
//    check(part2(testInput) == 10)

    val input = readInput("Day10")
    part1(input).println()
//    part2(input).println()
}
