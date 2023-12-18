
fun main() {
    fun part1(input: List<String>): Int {
        val parsed = input.map { line ->
            line.split(',').map  hash@{ hashString ->
                hashString.fold(0) {acc, char ->
                    ((acc + char.code) * 17) % 256
                }
            }
        }.map { line -> line.reduce{sum, element -> sum + element} }.first()
        return parsed
    }

    fun part2(input: List<String>): Int {
        val boxes: MutableList<MutableList<String>> = MutableList(256){MutableList(0){""} }
        val focalLength: HashMap<String, Int> = HashMap()
        input.map { line ->
            line.split(',')
                .forEach { s: String ->
                    val copy = s.split('-', '=')
                    val hash = copy.first()
                        .fold(0) { acc, char -> ((acc + char.code) * 17) % 256 }
                    if (s.contains('-')) {
                        val label = boxes[hash].indexOf(copy[0])
                        if (label != -1) focalLength.remove(boxes[hash][label])
                        boxes[hash].remove(copy[0])
                    } else {
                        if (!boxes[hash].contains(copy[0])) boxes[hash].add(copy[0])
                        focalLength[copy[0]] = copy[1].toInt()
                    }
                }
        }
        val answer = boxes
            .mapIndexed { boxIndex, box -> box.mapIndexed { slot, label ->
                (boxIndex + 1) * (slot + 1) * focalLength[label]!!
            }.sum()
            }.sum()



        return answer
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
