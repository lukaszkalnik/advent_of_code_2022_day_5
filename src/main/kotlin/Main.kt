import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

fun main(args: Array<String>) {
    val path = args[0].toPath()

    FileSystem.SYSTEM.read(path) {
        val stacks = readStacks()

        while (true) {
            val line = readUtf8Line() ?: break
            if (line.isEmpty()) break

            val move = parseMove(line)
            println(line)
            println(move)
        }
    }
}

private fun BufferedSource.readStacks(): List<ArrayDeque<Char>> {
    val lines = mutableListOf<String>()

    while (true) {
        val line = readUtf8Line() ?: break
        if (line.isEmpty()) return parseStacks(lines)

        lines += line
    }
    throw IllegalStateException("EOF while reading crates")
}

private fun parseStacks(lines: List<String>): List<ArrayDeque<Char>> {
    val table = parseTable(lines)
    val stackHeight = table.size
    val stacksWidth = table.first().size

    return List(stacksWidth) { stackNumber ->
        List(stackHeight) { cratePosition ->
            table[cratePosition][stackNumber] // transpose the matrix to get list of vertical stacks
        }.dropLastWhile { it == ' ' } // drop empty crate positions
            .let { ArrayDeque(it) } // Kotlin equivalent of a stack
    }
}

// Table has constant cell width
private const val CELL_WIDTH = 4
private const val CHAR_POSITION = 1
private fun parseTable(lines: List<String>): List<List<Char>> = lines.map { line ->
    line.chunked(CELL_WIDTH)
        .map { it.elementAt(CHAR_POSITION) }
}.dropLast(1) // in the input data the last line is just stack indexes
    .reversed() // in the input data the start of the stack is at the bottom

data class Move(
    val number: Int,
    val from: Int,
    val to: Int,
)

private val MOVE_REGEX = "^move (\\d+) from (\\d) to (\\d)\$".toRegex()
fun parseMove(line: String): Move {
    val matchResult = MOVE_REGEX.find(line)!!
    val (number, from, to) = matchResult.destructured
    return Move(
        number = number.toInt(),
        from = from.toInt() - 1, // in the input data stacks are indexed starting from 1
        to = to.toInt() - 1,
    )
}
