fun main() {

    fun solver(input: List<String>, part1: Boolean): Long {
        val reports = input.map { line -> line.split(" ").map { it.toLong() } }
        var lastDigit: Long = 0
        for (report in reports) {
            var reducedReport = if (part1) report else report.reversed()
            lastDigit += reducedReport.last()
            while (reducedReport.filter { it == 0.toLong() }.size != reducedReport.size) {
                reducedReport = reducedReport.windowed(2).map { it[1] - it[0]  }
                lastDigit += reducedReport.last()
            }
        }
        return lastDigit
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(solver(testInput, true) == 114.toLong())
    check(solver(testInput, false) == 2.toLong())

    val input = readInput("Day09")
    solver(input, true).println()
    solver(input, false).println()
}
