typealias InsertionPattern = Pair<String, String>

fun main() {
    fun parsePolymerTemplate(input: List<String>) = input.first()

    fun parseInsertionRules(input: List<String>) = input
        .drop(2)
        .map { line ->
            val (pattern, insertion) = line.split(" -> ")

            pattern to insertion
        }
        .toList()

    fun applyInsertions(polymer: String, insertionRules: List<InsertionPattern>): String = buildString {
        polymer.windowed(2, partialWindows = true) { polymerWindow ->
            append(polymerWindow.first())

            insertionRules.forEach { (pattern, insertion) ->
                if (polymerWindow == pattern) append(insertion)
            }
        }
    }

    fun String.applyInsertions(insertionRules: List<InsertionPattern>, rounds: Int): String {
        var polymer = this

        repeat(rounds) {
            polymer = applyInsertions(polymer, insertionRules)
        }
        return polymer
    }

    fun findLeastAndMostFrequentCharacterCount(polymer: String): Pair<Long, Long> {
        val characterFrequency: Map<Char, Long> = polymer
            .groupingBy { char -> char }
            .aggregate { _, count, _, _ -> (count ?: 0L) + 1 }

        val leastFrequentCharacterOccurrenceCount = characterFrequency.minOf { (_, count) -> count }
        val mostFrequentCharacterOccurrenceCount = characterFrequency.maxOf { (_, count) -> count }

        return Pair(leastFrequentCharacterOccurrenceCount, mostFrequentCharacterOccurrenceCount)
    }


    fun part1(input: List<String>): Long {
        val polymerTemplate = parsePolymerTemplate(input)
        val insertionRules: List<InsertionPattern> = parseInsertionRules(input)

        val polymer = polymerTemplate.applyInsertions(insertionRules, rounds = 10)

        val (leastFrequentCount, mostFrequentCount) = findLeastAndMostFrequentCharacterCount(polymer)

        return mostFrequentCount - leastFrequentCount
    }

    fun part2(input: List<String>): Long {
        TODO()
//        val polymerTemplate = parsePolymerTemplate(input)
//        val insertionRules: List<InsertionPattern> = parseInsertionRules(input)
//
//        val polymer = polymerTemplate.applyInsertions(insertionRules, rounds = 40)
//
//        val (leastFrequentCount, mostFrequentCount) = findLeastAndMostFrequentCharacterCount(polymer)
//
//        return mostFrequentCount - leastFrequentCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
