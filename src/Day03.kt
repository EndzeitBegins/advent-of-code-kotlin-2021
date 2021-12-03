fun main() {
    fun countTruthyBits(input: List<String>) = input
        .fold(IntArray(input.first().length)) { truthyBits, line ->
            line.forEachIndexed { index, bit ->
                if (bit == '1') truthyBits[index] += 1
            }

            truthyBits
        }

    fun findMostSignificantBits(input: List<String>): String {
        val truthyBits = countTruthyBits(input)

        val mostSignificantBits = truthyBits.joinToString(separator = "") {
            if (it * 2 >= input.size) {
                "1"
            } else "0"
        }

        return mostSignificantBits
    }

    fun part1(input: List<String>): Int {
        val mostSignificantBits = findMostSignificantBits(input)

        val fullRate = "1".repeat(input.first().length).toInt(2)
        val gammaRate = mostSignificantBits.toInt(2)
        val epsilonRate = fullRate - gammaRate

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val bitLength = input.first().length

        val oxygenGeneratorRating = (0 until bitLength).fold(input) { lines, index ->
            if (lines.size == 1) return@fold lines

            val mostSignificantBits = findMostSignificantBits(lines)
            val mostSignificantBit = mostSignificantBits[index]

            lines.filter { it[index] == mostSignificantBit }
        }.single().toInt(2)

        val c02ScrubberRating = (0 until bitLength).fold(input) { lines, index ->
            if (lines.size == 1) return@fold lines

            val mostSignificantBits = findMostSignificantBits(lines)
            val leastSignificantBit = if (mostSignificantBits[index] == '1') '0' else '1'

            lines.filter { it[index] == leastSignificantBit }
        }.single().toInt(2)

        return oxygenGeneratorRating * c02ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
