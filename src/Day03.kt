fun main() {
    fun part1(input: List<String>): Int {
        val numbersNextToSymbols: MutableSet<List<Int>> = mutableSetOf()
        val parsedInput = input.filter { it.isNotEmpty() || it.isNotBlank() }
        for (rowIndex in parsedInput.indices) {
            for ((colIndex, symbol) in parsedInput[rowIndex].withIndex()) {
                if (symbol == '.' || symbol.isDigit()) continue
                // all symbols are not on the outer border
                for (symbolRow in rowIndex - 1 ..  rowIndex + 1) {
                    for (symbolCol in colIndex - 1 .. colIndex + 1) {
                        if (!parsedInput[symbolRow][symbolCol].isDigit()) continue
                        var colOffset: Int = symbolCol
                        while (true) {
                            colOffset--
                            if (!parsedInput[symbolRow][colOffset].isDigit()) {
                                colOffset++
                                break
                            }
                            if (colOffset == 0) break
                        }
                        numbersNextToSymbols.add(listOf(symbolRow, colOffset))
                    }
                }
            }
        }
        var sum = 0
        for (coordinates in numbersNextToSymbols) {
            val row = coordinates[0]
            var col = coordinates[1]
            var sB = StringBuilder()
            while (parsedInput[row][col].isDigit()) {
                sB.append(parsedInput[row][col])
                if (col == parsedInput[row].length - 1) break
                col++
            }
            sum += sB.toString().toInt()
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val parsedInput = input.filter { it.isNotEmpty() || it.isNotBlank() }
        for (rowIndex in parsedInput.indices) {
            for ((colIndex, symbol) in parsedInput[rowIndex].withIndex()) {
                if (symbol != '*') continue
                val numbersNextToSymbols: MutableSet<List<Int>> = mutableSetOf()
                // all symbols are not on the outer border
                for (symbolRow in rowIndex - 1 ..  rowIndex + 1) {
                    for (symbolCol in colIndex - 1 .. colIndex + 1) {
                        if (!parsedInput[symbolRow][symbolCol].isDigit()) continue
                        var colOffset: Int = symbolCol
                        while (true) {
                            colOffset--
                            if (!parsedInput[symbolRow][colOffset].isDigit()) {
                                colOffset++
                                break
                            }
                            if (colOffset == 0) break
                        }
                        numbersNextToSymbols.add(listOf(symbolRow, colOffset))
                    }
                }
                var acc = 1
                if (numbersNextToSymbols.size == 2) {
                    for (coordinates in numbersNextToSymbols) {
                        val row = coordinates[0]
                        var col = coordinates[1]
                        var sB = StringBuilder()
                        while (parsedInput[row][col].isDigit()) {
                            sB.append(parsedInput[row][col])
                            if (col == parsedInput[row].length - 1) break
                            col++
                        }
                        acc *= sB.toString().toInt()
                    }
                }
                if (acc != 1) sum += acc
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
