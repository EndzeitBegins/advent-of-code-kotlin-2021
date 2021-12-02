data class Order(val command: String, val value: Int)

fun List<String>.asOrders(): List<Order> = this.map { line ->
    val (command, value) = line.split(" ")

    Order(command, value.toInt())
}

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input
            .asOrders()
            .forEach { (command, value) ->
                when (command) {
                    "forward" -> horizontal += value
                    "down" -> depth += value
                    "up" -> depth -= value
                }
            }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input
            .asOrders()
            .forEach { (command, value) ->
                when (command) {
                    "forward" -> {
                        horizontal += value
                        depth += aim * value
                    }
                    "down" -> aim += value
                    "up" -> aim -= value
                }
            }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
