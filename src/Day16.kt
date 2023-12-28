import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

enum class Direction { LEFT, RIGHT, UP, DOWN }

fun main() {
    data class Move(val direction: Direction, val row: Int, val col: Int)

    fun printEnergized(energized: List<List<MutableSet<Direction>>>) {
        for (row in energized.indices) {
            for (col in energized[row].indices) {
                if (energized[row][col].isNotEmpty()) print('#') else print('.')
            }
            print('\n')
        }
        println()
    }

    fun solver(input: List<String>, starter: List<Move>): Int {
        val max = runBlocking(Dispatchers.Default) {
            starter.map {
                async {
                    val energized: List<List<MutableSet<Direction>>> =
                        List(input.size) { List(input[0].length) { mutableSetOf() } }
                    val mirrorArray = input.map { s -> s.toCharArray().toList() }
                    val toCheck = ArrayDeque<Move>()
                    toCheck.add(it)

                    var row: Int
                    var col: Int
                    var direction: Direction

                    while (toCheck.isNotEmpty()) {
                        direction = toCheck.first().direction
                        row = toCheck.first().row
                        col = toCheck.first().col
                        toCheck.removeFirstOrNull()

                        try {
                            if (energized[row][col].contains(direction)) continue
                        } catch (e: IndexOutOfBoundsException) {
                            continue
                        }

                        energized[row][col].add(direction)
                        when (direction) {
                            Direction.LEFT -> {
                                when (mirrorArray[row][col]) {
                                    '|' -> {
                                        toCheck.add(Move(Direction.UP, row - 1, col))
                                        toCheck.add(Move(Direction.DOWN, row + 1, col))
                                    }

                                    '\\' -> toCheck.add(Move(Direction.UP, row - 1, col))
                                    '/' -> toCheck.add(Move(Direction.DOWN, row + 1, col))
                                    else -> toCheck.add(Move(direction, row, col - 1))
                                }
                            }

                            Direction.RIGHT -> {
                                when (mirrorArray[row][col]) {
                                    '|' -> {
                                        toCheck.add(Move(Direction.UP, row - 1, col))
                                        toCheck.add(Move(Direction.DOWN, row + 1, col))
                                    }

                                    '\\' -> toCheck.add(Move(Direction.DOWN, row + 1, col))
                                    '/' -> toCheck.add(Move(Direction.UP, row - 1, col))
                                    else -> toCheck.add(Move(direction, row, col + 1))
                                }
                            }

                            Direction.UP -> {
                                when (mirrorArray[row][col]) {
                                    '-' -> {
                                        toCheck.add(Move(Direction.LEFT, row, col - 1))
                                        toCheck.add(Move(Direction.RIGHT, row, col + 1))
                                    }

                                    '\\' -> toCheck.add(Move(Direction.LEFT, row, col - 1))
                                    '/' -> toCheck.add(Move(Direction.RIGHT, row, col + 1))
                                    else -> toCheck.add(Move(direction, row - 1, col))
                                }
                            }

                            Direction.DOWN -> {
                                when (mirrorArray[row][col]) {
                                    '-' -> {
                                        toCheck.add(Move(Direction.LEFT, row, col - 1))
                                        toCheck.add(Move(Direction.RIGHT, row, col + 1))
                                    }

                                    '\\' -> toCheck.add(Move(Direction.RIGHT, row, col + 1))
                                    '/' -> toCheck.add(Move(Direction.LEFT, row, col - 1))
                                    else -> toCheck.add(Move(direction, row + 1, col))
                                }
                            }
                        }
                    }
                    return@async energized.sumOf { line ->
                        line.map { if (it.isNotEmpty()) 1 else 0 }
                            .sum()
                    }
                }
            }.awaitAll()
        }.max()
        return max
    }

    fun part1(input: List<String>): Int {
        val answer = solver(input, listOf(Move(Direction.RIGHT, 0, 0)))
        return answer
    }
    fun part2(input: List<String>): Int {
        val starter = MutableList(0){Move(Direction.RIGHT, 0, 0)}
        input.forEachIndexed { row, _ ->
            starter.add(Move(Direction.LEFT, row, input[0].length - 1))
            starter.add(Move(Direction.RIGHT, row, 0))
        }
        input.forEachIndexed { col, _ ->
            starter.add(Move(Direction.DOWN, 0, col))
            starter.add(Move(Direction.UP, input.size - 1, col))
        }
        val answer = solver(input, starter.toList())
        return answer
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46)
    check(part2(testInput) == 51)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
