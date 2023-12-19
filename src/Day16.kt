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

    fun solver(input: List<String>): List<List<Set<Direction>>> {
        val energized: List<List<MutableSet<Direction>>> = List(input.size){List(input[0].length){ mutableSetOf() } }
        val mirrorArray = input.map { it.toCharArray().toList() }
        val toCheck = ArrayDeque<Move>()

        if (mirrorArray[0][0] == '\\') {
            toCheck.add((Move(Direction.DOWN, 0, 0)))
        } else {
            toCheck.add((Move(Direction.RIGHT, 0, 0)))
        }

        var row: Int
        var col: Int
        var direction: Direction

        while (toCheck.isNotEmpty()) {
            direction = toCheck.first().direction
            row = toCheck.first().row
            col = toCheck.first().col
            toCheck.removeFirstOrNull()

            if (energized[row][col].contains(direction)) continue
            energized[row][col].add(direction)
//            printEnergized(energized)
            try {
                when (direction) {
                    Direction.LEFT -> {
                        when (mirrorArray[row][--col]) {
                            '|' -> {
                                toCheck.add(Move(Direction.UP, row, col))
                                toCheck.add(Move(Direction.DOWN, row, col))
                            }
                            '\\' -> toCheck.add(Move(Direction.UP, row, col))
                            '/' -> toCheck.add(Move(Direction.DOWN, row, col))
                            else -> toCheck.add(Move(direction, row, col))
                        }
                    }
                    Direction.RIGHT -> {
                        when (mirrorArray[row][++col]) {
                            '|' -> {
                                toCheck.add(Move(Direction.UP, row, col))
                                toCheck.add(Move(Direction.DOWN, row, col))
                            }
                            '\\' -> toCheck.add(Move(Direction.DOWN, row, col))
                            '/' -> toCheck.add(Move(Direction.UP, row, col))
                            else -> toCheck.add(Move(direction, row, col))
                        }
                    }
                    Direction.UP -> {
                        when (mirrorArray[--row][col]) {
                            '-' -> {
                                toCheck.add(Move(Direction.LEFT, row, col))
                                toCheck.add(Move(Direction.RIGHT, row, col))
                            }
                            '\\' -> toCheck.add(Move(Direction.LEFT, row, col))
                            '/' -> toCheck.add(Move(Direction.RIGHT, row, col))
                            else -> toCheck.add(Move(direction, row, col))
                        }
                    }
                    Direction.DOWN -> {
                        when (mirrorArray[++row][col]) {
                            '-' -> {
                                toCheck.add(Move(Direction.LEFT, row, col))
                                toCheck.add(Move(Direction.RIGHT, row, col))
                            }
                            '\\' -> toCheck.add(Move(Direction.RIGHT, row, col))
                            '/' -> toCheck.add(Move(Direction.LEFT, row, col))
                            else -> toCheck.add(Move(direction, row, col))
                        }
                    }
                }
            } catch (e: IndexOutOfBoundsException) {}
        }
        return energized.map { list -> list.map { it.toSet() } }
    }

    fun part1(input: List<String>): Int {
        val answer = solver(input).sumOf { line -> line.map { if (it.isNotEmpty()) 1 else 0 }.sum() }
        return answer
    }
    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46)
//    check(part2(testInput) == 281)

    val input = readInput("Day16")
    part1(input).println()
//    part2(input).println()
}
