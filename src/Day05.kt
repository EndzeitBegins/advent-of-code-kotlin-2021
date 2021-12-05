typealias Point = Pair<Int, Int>

val Point.x: Int
    get() = first
val Point.y: Int
    get() = second

fun Point(str: String): Point {
    val (x, y) = str.split(",")

    return x.toInt() to y.toInt()
}

infix fun Point.to(point: Point): List<Point> {
    var (x, y) = this
    val points = mutableListOf<Point>()

    points += x to y

    while (x != point.x || y != point.y) {
        if (x < point.x) x++ else if (x > point.x) x--
        if (y < point.y) y++ else if (y > point.y) y--

        points += x to y
    }

    return points
}

fun main() {
    fun calculatePoints(input: List<String>): List<List<Point>> = input.map { line ->
        val (start, end) = line.split(" -> ")

        Point(start) to Point(end)
    }

    fun List<List<Point>>.findStrongVents() = this
        .flatten()
        .groupingBy { it }
        .eachCount()
        .filterValues { it >= 2 }
        .keys

    fun part1(input: List<String>): Int {
        val points = calculatePoints(input).filter { line ->
            val start = line.first()
            val end = line.last()

            start.x == end.x || start.y == end.y
        }

        return points
            .findStrongVents()
            .count()
    }

    fun part2(input: List<String>): Int {
        val points = calculatePoints(input)

        return points
            .findStrongVents()
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
