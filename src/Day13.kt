sealed interface FoldInstruction {
    data class Horizontal(val y: Int) : FoldInstruction
    data class Vertical(val x: Int) : FoldInstruction
}

val instructionRegex = """fold along ([xy])=(\d+)""".toRegex()

fun Set<Point>.foldHorizontally(foldY: Int): Set<Point> = map { (x, y) -> x to y.foldAt(foldY) }.toSet()
fun Set<Point>.foldVertically(foldX: Int): Set<Point> = map { (x, y) -> x.foldAt(foldX) to y }.toSet()
fun Int.foldAt(n: Int): Int = if (this <= n) this else 2 * n - this

fun main() {
    fun parsePoints(input: List<String>) = input
        .takeWhile(String::isNotBlank)
        .map { line ->
            val (x, y) = line.split(",")

            x.toInt() to y.toInt()
        }
        .toSet()

    fun parseInstructions(input: List<String>) = input
        .takeLastWhile(String::isNotBlank)
        .map { line ->
            val result = checkNotNull(instructionRegex.matchEntire(line)).groupValues
            val axis = result[1]
            val value = result[2].toInt()

            when (axis) {
                "x" -> FoldInstruction.Vertical(value)
                else -> FoldInstruction.Horizontal(value)
            }
        }

    fun foldPaper(
        initialPoints: Set<Point>,
        instructions: List<FoldInstruction>
    ) = instructions.fold(initialPoints) { points, instruction ->
        when (instruction) {
            is FoldInstruction.Horizontal -> points.foldHorizontally(instruction.y)
            is FoldInstruction.Vertical -> points.foldVertically(instruction.x)
        }
    }

    fun part1(input: List<String>): Int {
        val initialPoints: Set<Point> = parsePoints(input)
        val instructions: List<FoldInstruction> = parseInstructions(input).take(1)

        val foldedPoints = foldPaper(initialPoints, instructions)

        return foldedPoints.size
    }

    fun part2(input: List<String>): String {
        val initialPoints: Set<Point> = parsePoints(input)
        val instructions: List<FoldInstruction> = parseInstructions(input)

        val foldedPoints = foldPaper(initialPoints, instructions)

        val maxX = foldedPoints.maxOf { (x, y) -> x }
        val maxY = foldedPoints.maxOf { (x, y) -> y }

        return buildString {
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    append(if (x to y in foldedPoints) '#' else '.')
                }
                appendLine()
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
