fun main() {

    fun parseInput(input: List<String>): List<List<List<Long>>> {
        val xToYMap: MutableList<List<List<Long>>> = MutableList(0){List(0){List(0){0} } }
        val currentMap: MutableList<List<Long>> = MutableList(0){List(0){0} }

        val seeds = input[0].split(": ", " ").drop(1).map { it.toLong()}
        xToYMap.add(listOf(seeds))

        val parsedInput = input.drop(3).filter { it != ""}

        for (line in parsedInput) {
            if (line.contains(":")) {
                xToYMap.add(currentMap.toList())
                currentMap.removeAll{it.isNotEmpty()}
                continue
            }
            currentMap.add(line.split(" ").map { it.toLong() })
        }
        xToYMap.add(currentMap.toList())

        return xToYMap.toList()
    }


    data class MapRanges(val destFrom: Long, val destTo: Long, val srcFrom: Long, val srcTo: Long)
    fun getRanges(parsedInput: List<List<List<Long>>>): List<List<MapRanges>> {
        return parsedInput.map { xyMaps ->
            xyMaps.map { MapRanges(it[0], it[0] + it[2] - 1, it[1],  it[1] + it[2] - 1)}
        }
    }

    fun part1(input: List<String>): Long {
        val parsedInput = parseInput(input)
        val ranges = getRanges(parsedInput.subList(1, parsedInput.size))
        val checkedNumbers: MutableList<Set<Long>> = MutableList(7){ setOf(-1) }

        val locationForSeed: MutableList<Long> = MutableList(0){ 0 }
        for (seed in parsedInput[0][0]) {
            var currentNumberToFind = seed
            for ((mapIndex, xyMap) in ranges.withIndex()) {
                var possibleNumbersToContinue: MutableList<Long>
                for (mapRange in xyMap) {

                    if (mapRange.srcFrom <= currentNumberToFind && mapRange.srcTo >= currentNumberToFind) {
                        currentNumberToFind = mapRange.destTo - (mapRange.srcTo - currentNumberToFind)
                    }
                    if (mapIndex == 6) locationForSeed.add(currentNumberToFind)
                }
            }
        }
        val test = 0
        return 35
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toLong())
//    check(part2(testInput) == 30)

    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}
