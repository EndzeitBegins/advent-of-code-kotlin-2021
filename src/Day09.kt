fun main() {
    fun buildHeightMap(input: List<String>): Map<Point, Int> = buildMap {
        input.forEachIndexed { depth, line ->
            line.forEachIndexed { width, height ->
                put(depth to width, height.digitToInt())
            }
        }
    }

    fun searchForLowPoints(heights: Map<Point, Int>) = heights.filter { (point, height) ->
        val (depth, width) = point

        val heightPreviousDepth = heights[depth - 1 to width] ?: Int.MAX_VALUE
        val heightNextDepth = heights[depth + 1 to width] ?: Int.MAX_VALUE
        val heightPreviousWidth = heights[depth to width - 1] ?: Int.MAX_VALUE
        val heightNextWidth = heights[depth to width + 1] ?: Int.MAX_VALUE

        height < heightPreviousDepth &&
                height < heightNextDepth &&
                height < heightPreviousWidth &&
                height < heightNextWidth
    }

    fun part1(input: List<String>): Int {
        val heights = buildHeightMap(input)
        val lowPoints = searchForLowPoints(heights)

        return lowPoints.values.sumOf { it + 1 }
    }

    fun findBasinSizes(heights: Map<Point, Int>, lowPoints: Map<Point, Int>): List<Int> {
        return lowPoints.map { (point, _) ->
            val pointsInBasin = mutableSetOf<Point>()

            var searchPoints = setOf(point)

            while (searchPoints.isNotEmpty()) {
                val largerNeighbours = buildSet {
                    searchPoints.forEach { (depth, width) ->
                        val height = heights.getValue(depth to width)

                        val neighbours = listOf(
                            depth - 1 to width, depth + 1 to width, depth to width - 1, depth to width + 1,
                        )

                        neighbours
                            .filter {
                                val neighbourHeight = heights[it]

                                neighbourHeight != null && neighbourHeight > height && neighbourHeight < 9
                            }
                            .forEach { add(it) }
                    }
                }

                pointsInBasin.addAll(searchPoints)
                searchPoints = largerNeighbours
            }

                pointsInBasin.size
        }
    }

    fun part2(input: List<String>): Int {
        val heights = buildHeightMap(input)
        val lowPoints = searchForLowPoints(heights)

        val basins = findBasinSizes(heights, lowPoints)
        val (largestBasin, secondLargestBasin, thirdLargestBasin) = basins.sortedDescending()

        return largestBasin * secondLargestBasin * thirdLargestBasin
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
