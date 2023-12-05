import kotlin.math.min

fun main() {

    fun parseInput(input: List<String>): List<List<List<Long>>> {
        val xToYMap: MutableList<List<List<Long>>> = MutableList(0){List(0){List(0){0} } }
        val currentMap: MutableList<List<Long>> = MutableList(0){List(0){0} }

        val seeds = input[0].split(": ", " ").drop(1).map { it.toLong() }
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

    fun findLowestLocation(ranges: List<List<MapRanges>>, seeds: List<Long>): Long {
        val lowestLocation: MutableList<Long> = MutableList(0){0}

        for (seed in seeds) {
            val possibleOutcomes = List(8){ mutableSetOf<Long>() }
            possibleOutcomes[0].add(seed)
            for ((mapIndex, xyMap) in ranges.withIndex()) {
                var foundMatch = false
                for (number in possibleOutcomes[mapIndex]) {
                    for (mapRange in xyMap) {
                        if (mapRange.srcFrom <= number && mapRange.srcTo >= number) {
                            possibleOutcomes[mapIndex + 1].add(mapRange.destTo - (mapRange.srcTo - number))
                            foundMatch = true
                        }
                    }
                    if (!foundMatch) possibleOutcomes[mapIndex + 1].add(number)
                }
            }
            lowestLocation.add(possibleOutcomes[7].min())
        }
        return lowestLocation.min()
    }
    fun findLowestLocation2(ranges: List<List<MapRanges>>, seeds: List<Long>): Long {
        var lowestLocation = Long.MAX_VALUE
        val startTime = System.currentTimeMillis()
        val alreadyChecked: List<MutableSet<Long>> = List(7){ mutableSetOf() }
        var foundMatch: Boolean
        var pOut: MutableSet<Long>
        val lowest = ranges.map { line -> line.minOf { it.srcFrom }}
        val highest = ranges.map { line -> line.minOf { it.srcTo }}

        for (i in seeds.indices step 2) {
            for (seed in seeds[i]..< (seeds[i] + seeds[i + 1])) {
                val possibleOutcomes = List(7) { mutableSetOf<Long>() }
                for ((mapIndex, xyMap) in ranges.withIndex()) {
                    foundMatch = false
                    pOut = if (mapIndex == 0) {
                        mutableSetOf(seed)
                    } else {
                        possibleOutcomes[mapIndex]
                    }
                    for (number in pOut) {
                        if (number < lowest[mapIndex] && number > highest[mapIndex]) continue
                        if (alreadyChecked[mapIndex].contains(number)) continue
                        alreadyChecked[mapIndex].add(number)
                        for (mapRange in xyMap) {
                            if (mapRange.srcFrom <= number && mapRange.srcTo >= number) {
                                foundMatch = true
                                if (mapIndex == 6) {
                                    lowestLocation = min(lowestLocation, mapRange.destTo - (mapRange.srcTo - number))
                                }
                                else possibleOutcomes[mapIndex + 1].add(mapRange.destTo - (mapRange.srcTo - number))
                            }
                        }
                        if (foundMatch) continue
                        if (mapIndex == 6) {
                            lowestLocation = min(lowestLocation, number)
                            continue
                        }
                        possibleOutcomes[mapIndex + 1].add(number)
                    }
                }
            }
            println("${i+2} seeds from ${seeds.size} done")
            println("${((i+2)*100)/(seeds.size)}% done")
            println("elapsed time: ${(System.currentTimeMillis() - startTime)/1000}s")
        }
        return lowestLocation
    }

    fun part1(input: List<String>): Long {
        val parsedInput = parseInput(input)
        val ranges = getRanges(parsedInput.subList(1, parsedInput.size))
        return findLowestLocation(ranges, parsedInput[0][0])
    }

    fun part2(input: List<String>): Long {
        val parsedInput = parseInput(input)
        val ranges = getRanges(parsedInput.subList(1, parsedInput.size))
        return findLowestLocation2(ranges, parsedInput[0][0])
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toLong())
    check(part2(testInput) == 46.toLong())

    val input = readInput("Day05")
//    part1(input).println()
    part2(input).println()
}
