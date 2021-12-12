private val String.isBigCave: Boolean
    get() = first().isUpperCase()

typealias Waypoints = Map<String, Set<String>>

fun main() {
    fun parseWaypoints(input: List<String>): Waypoints = buildMap {
        for (line in input) {
            val (cave, otherCave) = line.split("-")

            put(cave, get(cave)?.plus(otherCave) ?: setOf(otherCave))
            put(otherCave, get(otherCave)?.plus(cave) ?: setOf(cave))
        }
    }

    fun findPaths(
        waypoints: Waypoints,
        targetValidationFactory: (path: List<String>) -> ((String) -> Boolean)
    ): Set<List<String>> {
        val paths = buildSet {
            fun findPaths(path: List<String>) {
                val position = path.last()
                val isTargetValid = targetValidationFactory.invoke(path)

                if (position == "end") add(path)
                else {
                    val possibleTargets = waypoints.getValue(position)
                        .filterNot { it == "start" }
                        .filter { possibleTarget -> isTargetValid(possibleTarget) }

                    for (possibleTarget in possibleTargets) {
                        findPaths(path + possibleTarget)
                    }
                }
            }

            findPaths(listOf("start"))
        }
        return paths
    }

    fun part1(input: List<String>): Int {
        val waypoints = parseWaypoints(input)

        fun isValidTarget(path: List<String>): (String) -> Boolean =
            { it.isBigCave || it !in path }

        val paths = findPaths(waypoints, ::isValidTarget)

        return paths.size
    }

    fun part2(input: List<String>): Int {
        val waypoints = parseWaypoints(input)

        fun isValidTarget(path: List<String>): (String) -> Boolean = { possibleTarget ->
            val visitedSmallCaveTwice: Boolean = path
                .filterNot(String::isBigCave)
                .groupingBy { it }
                .eachCount()
                .any { (_, timesVisited) -> timesVisited > 1 }

            possibleTarget.isBigCave || possibleTarget !in path || !visitedSmallCaveTwice
        }

        val paths = findPaths(waypoints, ::isValidTarget)

        return paths.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
