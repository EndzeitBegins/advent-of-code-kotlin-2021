fun main() {
    fun findIllegalCharacter(line: String): Char? {
        val expectedClosingCharacters = mutableListOf<Char>()
        var index = 0

        while (index <= line.lastIndex) {
            when (val char = line[index]) {
                '(' -> expectedClosingCharacters.add(0, ')')
                '[' -> expectedClosingCharacters.add(0, ']')
                '{' -> expectedClosingCharacters.add(0, '}')
                '<' -> expectedClosingCharacters.add(0, '>')

                expectedClosingCharacters.firstOrNull() -> {
                    expectedClosingCharacters.removeFirst()
                }

                else -> {
                    return char
                }
            }

            index += 1
        }

        return null
    }

    fun findMissingCharacters(line: String): List<Char> {
        return line.fold(mutableListOf()) { expectedClosingCharacters, char ->
            when (char) {
                '(' -> expectedClosingCharacters.add(0, ')')
                '[' -> expectedClosingCharacters.add(0, ']')
                '{' -> expectedClosingCharacters.add(0, '}')
                '<' -> expectedClosingCharacters.add(0, '>')

                expectedClosingCharacters.firstOrNull() -> {
                    expectedClosingCharacters.removeFirst()
                }
            }

            expectedClosingCharacters
        }
    }

    fun isCorrupted(line: String): Boolean = findIllegalCharacter(line) != null

    fun part1(input: List<String>): Int {
        val points = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )

        val illegalCharacters = input
            .mapNotNull { line -> findIllegalCharacter(line) }

        return illegalCharacters
            .sumOf { char -> points.getValue(char) }
    }

    fun part2(input: List<String>): Long {
        val points = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )

        val nonCorruptedLines = input
            .filterNot(::isCorrupted)
        val missingCharacters = nonCorruptedLines
            .map { line -> findMissingCharacters(line) }
        val scores = missingCharacters
            .map { missingCharactersOfLine ->
                missingCharactersOfLine.fold(0L) { score, char -> score * 5 + points.getValue(char) }
            }
            .sorted()

        return scores[scores.lastIndex / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
