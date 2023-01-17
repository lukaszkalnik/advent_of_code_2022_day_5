import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

fun main(args: Array<String>) {
    val path = args[0].toPath()

    FileSystem.SYSTEM.read(path) {
       val stacks = readStacks()
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
    var stacks = emptyList<ArrayDeque<Char>>()

    return stacks
}
