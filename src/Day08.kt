import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        fun countAppearances(line: String): Int {
            val (_, outputValues) = line.split("|")

            val outputValueSets = outputValues
                .split(" ")
                .map { it.trim().toSet() }

            return outputValueSets.count { outputValueSet -> outputValueSet.size in setOf(2, 3, 4, 7) }
        }

        return input.sumOf { countAppearances(it) }
    }

    fun part2(input: List<String>): Int {
        fun extractPatternSets(patternString: String): List<Set<Char>> = patternString
            .trim()
            .splitToSequence(" ")
            .map { it.trim() }
            .map { it.toSet() }
            .toList()

        return input.sumOf { line ->
            val (signalPatterns, outputValues) = line.split("|")
            val signalPatternSets = extractPatternSets(signalPatterns)
            val fiveSegmentPatternSets = signalPatternSets.filter { it.size == 5 }
            val sixSegmentPatternSets = signalPatternSets.filter { it.size == 6 }

            val one = signalPatternSets.single { it.size == 2 }
            val four = signalPatternSets.single { it.size == 4 }
            val seven = signalPatternSets.single { it.size == 3 }
            val eight = signalPatternSets.single { it.size == 7 }

            val six = sixSegmentPatternSets.single { (it - one).size == 5 }

            val cCharacter = (one - six).single()

            val five = fiveSegmentPatternSets.single { cCharacter !in it }
            val three = fiveSegmentPatternSets.single { (it - one).size == 3 }
            val two = fiveSegmentPatternSets.single { it != five && it != three }

            val nine = sixSegmentPatternSets.single { (it - three).size == 1 }
            val zero = sixSegmentPatternSets.single { it != six && it != nine }

            val mappedPatterns = mapOf(
                zero to 0,
                one to 1,
                two to 2,
                three to 3,
                four to 4,
                five to 5,
                six to 6,
                seven to 7,
                eight to 8,
                nine to 9,
            )

            val outputValueSets = extractPatternSets(outputValues)

            outputValueSets.foldIndexed(0) { index, number, segmentPattern ->
                val multiplier = 10.0.pow(outputValueSets.lastIndex - index).toInt()

                number + multiplier * mappedPatterns.getValue(segmentPattern)
            }.toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
