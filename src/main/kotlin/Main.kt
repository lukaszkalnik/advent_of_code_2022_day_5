import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

fun main(args: Array<String>) {
    val path = args[0].toPath()

    FileSystem.SYSTEM.read(path) {
        val stacks = readStacks()
        stacks.forEach { println(it) }
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
            table[cratePosition][stackNumber]
        }.dropLastWhile { it == ' ' }
            .let { ArrayDeque(it) }
    }
}

private const val CELL_WIDTH = 4
private const val CHAR_POSITION = 1
private fun parseTable(lines: List<String>): List<List<Char>> = lines.map { line ->
    line.chunked(CELL_WIDTH)
        .map { it.elementAt(CHAR_POSITION) }
}.dropLast(1)
    .reversed()
