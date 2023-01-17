import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath

fun main(args: Array<String>) {
    val path = args[0].toPath()

    FileSystem.SYSTEM.read(path) {
    }
}

private fun BufferedSource.parseCrates(): List<ArrayDeque<Char>> {
    var stacks: List<ArrayDeque<Char>> = emptyList()
    while (true) {
        val line = readUtf8Line() ?: break
        if (line.isEmpty()) return stacks
    }
    throw IllegalStateException("EOF while reading crates")
}