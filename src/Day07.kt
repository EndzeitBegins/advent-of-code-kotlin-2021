import kotlin.math.abs

fun main() {
    fun calculateSensiblePositionRange(crabs: List<Int>): IntRange {
        val leftMostPosition = crabs.minOf { it }
        val rightMostPosition = crabs.maxOf { it }

        return leftMostPosition..rightMostPosition
    }

    fun parseCrabPositions(input: List<String>) = input
        .single()
        .split(",")
        .map { it.toInt() }

    fun groupCrabs(crabs: List<Int>) = crabs.groupingBy { it }.eachCount()

    fun calculateRequiredFuel(
        input: List<String>,
        fuelCalculationFn: (position: Int, crabAmount: Int, targetPosition: Int) -> Int
    ): Int {
        val crabs = parseCrabPositions(input)
        val groupedCrabs = groupCrabs(crabs)
        val sensiblePositions = calculateSensiblePositionRange(crabs)

        val movements = sensiblePositions.associateWith { targetPosition ->
            val requiredFuel = groupedCrabs.entries.fold(0) { fuel, (position, amount) ->
                fuel + fuelCalculationFn.invoke(position, amount, targetPosition)
            }

            requiredFuel
        }

        return movements.minOf { (_, fuel) -> fuel }
    }

    fun part1(input: List<String>): Int = calculateRequiredFuel(input) { position, crabAmount, targetPosition ->
        val distance = abs(targetPosition - position)

        distance * crabAmount
    }

    fun part2(input: List<String>): Int = calculateRequiredFuel(input) { position, crabAmount, targetPosition ->
        tailrec fun fuelCost(distance: Int, runningFuelCost: Int = 1): Int =
            if (distance <= 1) runningFuelCost else fuelCost(distance - 1, runningFuelCost + distance)

        val distance = abs(targetPosition - position)
        val fuelCost = fuelCost(distance)

        fuelCost * crabAmount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

