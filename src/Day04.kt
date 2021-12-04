data class BingoBoard(
    val rows: List<List<Int>>,
    val columns: List<List<Int>>,
)

fun BingoBoard.crossNumber(number: Int): BingoBoard {
    val rows = rows.map { row -> row.filterNot { it == number } }
    val columns = columns.map { column -> column.filterNot { it == number } }

    return BingoBoard(rows, columns)
}

fun BingoBoard.hasWon(): Boolean = rows.any { it.isEmpty() } || columns.any { it.isEmpty() }

fun BingoBoard.collectRemainder() = rows.flatten().sum()

fun findWinningBoard(calledNumbers: List<Int>, bingoBoards: List<BingoBoard>): Pair<Int, BingoBoard> {
    var boards = bingoBoards

    for (calledNumber in calledNumbers) {
        boards = boards.map { bingoBoard ->
            val updated = bingoBoard.crossNumber(calledNumber)

            if (updated.hasWon()) {
                return Pair(calledNumber, updated)
            }

            updated
        }
    }

    throw IllegalStateException("How did we end up here?")
}


fun findLosingBoard(calledNumbers: List<Int>, bingoBoards: List<BingoBoard>): Pair<Int, BingoBoard> {
    var boards = bingoBoards

    for (calledNumber in calledNumbers) {
        boards = boards.map { bingoBoard -> bingoBoard.crossNumber(calledNumber) }

        if (boards.size > 1) {
            boards = boards.filterNot { it.hasWon() }
        } else {
            return Pair(calledNumber, boards.single())
        }
    }

    throw IllegalStateException("How did we end up here?")
}

fun main() {
    fun extractCalledNumbers(input: List<String>) =
        input.first().split(",").map { it.toInt() }

    fun extractBingoBoards(input: List<String>) = input
        .drop(2)
        .windowed(5, 6)
        .map { bingoBoardRows ->
            val rows = bingoBoardRows.map { bingoBoardRow ->
                bingoBoardRow
                    .trim()
                    .split("""\s+""".toRegex())
                    .map { it.toInt() }
            }

            val columns = List(5) { mutableListOf<Int>() }
            rows.forEach { row ->
                row.forEachIndexed { columnNumber, value ->
                    columns[columnNumber] += value
                }
            }

            BingoBoard(rows, columns)
        }

    fun part1(input: List<String>): Int {
        val calledNumbers = extractCalledNumbers(input)
        val bingoBoards = extractBingoBoards(input)

        val (calledNumber, winningBoard) = findWinningBoard(calledNumbers, bingoBoards)

        return calledNumber * winningBoard.collectRemainder()
    }

    fun part2(input: List<String>): Int {
        val calledNumbers = extractCalledNumbers(input)
        val bingoBoards = extractBingoBoards(input)

        val (calledNumber, losingBoard) = findLosingBoard(calledNumbers, bingoBoards)

        return calledNumber * losingBoard.collectRemainder()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
