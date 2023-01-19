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
        if (line.isEmpty()) return parseStacks(lines)

        lines += line
    }
    throw IllegalStateException("EOF while reading crates")
}

private fun parseStacks(lines: List<String>): List<List<Char>> {
    val reversedTable = parseTable(lines).reversed()
    val stackHeight = reversedTable.size
    val stacksWidth = reversedTable.first().size

    val stacks: List<List<Char>> = List(stacksWidth) { stackNumber ->
        List(stackHeight) { cratePosition ->
            reversedTable[cratePosition][stackNumber]
        }.dropLastWhile { it == ' ' }
    }
    return stacks
}

private const val CELL_WIDTH = 4
private const val CHAR_POSITION = 1
private fun parseTable(lines: List<String>): List<List<Char>> = lines.map { line ->
    val cells = line.chunked(CELL_WIDTH)
    cells.map { it.elementAt(CHAR_POSITION) }
}.dropLast(1)
