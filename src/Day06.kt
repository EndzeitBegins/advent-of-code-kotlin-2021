fun main() {
    fun countFish(input: List<String>) = input
        .single()
        .splitToSequence(',')
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, entry) -> entry.toLong() }

    fun Map<Int, Long>.nextGeneration() = mapOf(
        0 to getOrDefault(1, 0),
        1 to getOrDefault(2, 0),
        2 to getOrDefault(3, 0),
        3 to getOrDefault(4, 0),
        4 to getOrDefault(5, 0),
        5 to getOrDefault(6, 0),
        6 to getOrDefault(7, 0) + getOrDefault(0, 0),
        7 to getOrDefault(8, 0),
        8 to getOrDefault(0, 0)
    )

    fun part1(input: List<String>): Long {
        var fish = countFish(input)

        repeat(80) {
            fish = fish.nextGeneration()
        }

        return fish.values.sum()
    }

    fun part2(input: List<String>): Long {
        var fishes = countFish(input)

        repeat(256) {
            fishes = fishes.nextGeneration()
        }

        return fishes.values.sum()
    }
1
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
