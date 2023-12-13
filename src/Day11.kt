import kotlin.math.abs

fun main() {

    data class Galaxies(val row: Long, val column: Long)

    fun parseInput(input: List<String>, increase: Long): List<Galaxies> {
        val emptyRows: MutableList<Long> = MutableList(input.size){-1}
        var rowOffset: Long
        for (row in input.indices) {
            rowOffset = emptyRows[(row-1).coerceAtLeast(0)]
            emptyRows[row] = if (input[row].contains('#')) ++rowOffset else rowOffset + increase
        }

        val emptyColumns: MutableList<Long> = MutableList(input[0].length){-1}
        var hasGalaxy: Boolean
        var columnOffset: Long
        for (column in input[0].indices) {
            hasGalaxy = false
            for (row in input.indices) {
                if (input[row][column] == '#') {
                    hasGalaxy = true
                    break
                }
            }
            columnOffset = emptyColumns[(column-1).coerceAtLeast(0)]
            emptyColumns[column] = if (hasGalaxy) ++columnOffset else columnOffset + increase
        }

        val galaxies = input.mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, c ->
                if (c == '#') Galaxies(emptyRows[rowIndex], emptyColumns[colIndex]) else Galaxies(-1, -1)
            }.filter { it.row != (-1).toLong() }
        }.flatten()
        return galaxies
    }

    fun solver(input: List<String>, increase: Long): Long {
        var galaxies = parseInput(input, increase)
        var row: Long
        var column: Long
        // could just be a Long, but is nicer to debug as list
        val distances: MutableList<Long> = MutableList(0){0}
        while (galaxies.size > 1) {
            for (index in 1..< galaxies.size) {
                row = abs(galaxies[index].row - galaxies[0].row)
                column = abs(galaxies[index].column - galaxies[0].column)
                distances.add(row+column)
            }
            galaxies = galaxies.drop(1)
        }
        return distances.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(solver(testInput, 2) == 374.toLong())
    check(solver(testInput, 10) == 1030.toLong())
    check(solver(testInput, 100) == 8410.toLong())

    val input = readInput("Day11")
    solver(input, 2).println()
    solver(input, 1_000_000).println()
}
