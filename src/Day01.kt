fun main() {
    fun List<Int>.countIncreasingMeasurements() = this
        .zipWithNext { previousDepth, currentDepth -> currentDepth > previousDepth }
        .count { it }

    fun part1(input: List<String>): Int {
        return input
            .map(String::toInt)
            .countIncreasingMeasurements()
    }

    fun part2(input: List<String>): Int {
        return input
            .map(String::toInt)
            .windowed(3) { it.sum() }
            .countIncreasingMeasurements()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
