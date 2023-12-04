fun main() {

    data class Symbol(val column: Int, val row: Int)
    fun Int.coerceArray(maxLength: Int) =
        this.coerceAtLeast(0).coerceAtMost(maxLength - 1)

//    fun findNumber(symbols: List<Symbol>, schematic: List<String>) {
//        val adjacentNumbers: MutableList<Int> = MutableList(0){0}
//        for (symbol in symbols) {
//            val rowMaxLength = schematic[0].length
//            val columnMaxLength = schematic.size
//            val rowLowerRange = (symbol.row - 3).coerceArray(rowMaxLength)
//            val rowUpperRange = (symbol.row + 3).coerceArray(rowMaxLength)
//            schematic[symbol.column-1].substring(rowLowerRange, rowUpperRange).windowed(3)
//
//        }
//    }

    fun part1(input: List<String>): Int {
        val maxLength = input[0].length
        val schematic = input.flatMap { it.toList() }
        val symbols = input.flatMap { it.toList() }.mapIndexed{index, symbol ->
            if(!symbol.isDigit() && symbol != '.') Symbol(index % maxLength, index / maxLength) else null
        }.filterNotNull()

//        for (symbol in symbols) {
//            val row = symbol.row
//            val rowMaxLength = input.size
//            val column = symbol.column
//            val columnMaxLength = input[0].length

            // sind alle min 3 von links und rechts entfernt und min 1 von oben und unten

//            if(input[(row - 1).coerceAtLeast(0)][(column)].isDigit()) { // over
//                input[]
//            } else {
//                if (input[(row - 1).coerceAtLeast(0)][(column - 1).coerceAtLeast(0)].isDigit()) { // over left
//                    TODO()
//                }
//                if (input[(row - 1).coerceAtLeast(0)][(column + 1).coerceAtMost(columnMaxLength)].isDigit()) { // over right
//                    TODO()
//                }
//            }
//            if(input[(row + 1).coerceAtMost(rowMaxLength)][(column + 1).coerceArray(maxLength)].isDigit()) { // under
//                TODO()
//            } else {
//                if (input[(row - 1).coerceArray(maxLength)][(column + 1).coerceArray(maxLength)].isDigit()) { // under left
//                    TODO()
//                }
//                if (input[(row + 1).coerceArray(maxLength)][(column + 1).coerceArray(maxLength)].isDigit()) { // under right
//                    TODO()
//                }
//            }
//            if(input[(row + 1).coerceArray(maxLength)][column].isDigit()) { // left
//                TODO()
//            }
//            if(input[(row + 1).coerceArray(maxLength)][column].isDigit()) { // right
//                TODO()
//            }

//            input[row][column]
//        }

//        Symbol(index % maxLength, index / maxLength)


        println(19/10)
        println(20/10)
        println(1 % maxLength)
        println(8 % maxLength)
        println(9 % maxLength)
        println(10 % maxLength)
        println(11 % maxLength)
        println(13 % maxLength)

        val test = 0
        return input.size
    }

    fun part2(input: List<String>): Int {

        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 142)
//    check(part2(testInput2) == 281)

    val input = readInput("Day03")
//    part1(input).println()
//    part2(input).println()
}
