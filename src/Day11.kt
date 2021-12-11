data class DumpoOctopus(
    var energy: Int
)

fun main() {
    fun Map<Point, DumpoOctopus>.affectNeighboursOf(point: Point) {
        val map = this
        val (column, row) = point

        map[column - 1 to row - 1]?.apply { energy++ }
        map[column to row - 1]?.apply { energy++ }
        map[column + 1 to row - 1]?.apply { energy++ }
        map[column - 1 to row]?.apply { energy++ }
        map[column + 1 to row]?.apply { energy++ }
        map[column - 1 to row + 1]?.apply { energy++ }
        map[column to row + 1]?.apply { energy++ }
        map[column + 1 to row + 1]?.apply { energy++ }
    }

    fun parseDumboMap(input: List<String>) = input.flatMapIndexed { column, line ->
        line.mapIndexed { row, energyLevel ->
            (row to column) to DumpoOctopus(energyLevel.digitToInt())
        }
    }.associate { it }

    fun Map<Point, DumpoOctopus>.tick() {
        forEach { (_, octopus) -> octopus.energy++ }

        val flashedOctopusPositions = mutableSetOf<Point>()
        var flashingOctopusPositions = filterValues { octopus -> octopus.energy > 9 }.keys

        while (flashingOctopusPositions.isNotEmpty()) {
            flashedOctopusPositions += flashingOctopusPositions

            flashingOctopusPositions.forEach { position ->
                affectNeighboursOf(position)
            }

            flashingOctopusPositions = filterValues { octopus -> octopus.energy > 9 }.keys - flashedOctopusPositions
        }

        forEach { (_, octopus) ->
            if (octopus.energy > 9) {
                octopus.energy = 0
            }
        }
    }

    fun part1(input: List<String>): Int {
        val dumboMap: Map<Point, DumpoOctopus> = parseDumboMap(input)

        var flashes = 0

        repeat(100) {
            dumboMap.tick()

            flashes +=
                dumboMap.count { (_, octopus) -> octopus.energy == 0 }
        }

        return flashes
    }

    fun part2(input: List<String>): Int {
        val dumboMap: Map<Point, DumpoOctopus> = parseDumboMap(input)

        var step = 0

        do {
            dumboMap.tick()

            step += 1
        } while(dumboMap.any { (_, octopus) -> octopus.energy > 0 })

        return step
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
