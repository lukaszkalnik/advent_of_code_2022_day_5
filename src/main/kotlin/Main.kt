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

private fun BufferedSource.readStacks(): List<List<Char>> {
    val lines = mutableListOf<String>()

    while (true) {
        val line = readUtf8Line() ?: break
        if (line.isEmpty()) return parseTable(lines)

        lines += line
    }
    throw IllegalStateException("EOF while reading crates")
}

private const val CELL_WIDTH = 4
private const val CHAR_POSITION = 1
private fun parseTable(lines: List<String>): List<List<Char>> = lines.map { line ->
    val cells = line.chunked(CELL_WIDTH)
    cells.map { it.elementAt(CHAR_POSITION) }
}
