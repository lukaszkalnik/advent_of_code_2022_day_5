# Advent of Code 2022 - day 5

## Today's task involved
- parsing a table with fixed-width cells to obtain a list of stacks of characters
- parsing a list of commands to move a number of characters between individual stacks
- moving the characters according to parsed commands by popping/pushing them between stacks

## Part 2 plot twist
- instead of popping/pushing individual characters, pop/push all required characters at once (as a group)

## What I learned
- if you need to read from a file in `okio` multi-line fragments one after another, just create extension functions on `BufferedSource` and call them in required order. The current line "cursor" position will be retained between them and you can resume reading in the next function where the previous one ended.
- parsing a table of single characters with fixed cell width in Kotlin can be done by using the `String.chunked(cellWidth)` and `elementAt(characterPosition)`
- `List.reversed()` function can be used to reverse a list which is in wrong order
- If your `List<List<T>>` is "inside-out" (i.e. you need actually a list of zeroth elements of the external list, then the list of first elements etc.), you can convert it by transposing a matrix:

```
val height = originalMatrix.size
val width = originalMatrix.first().size
List(width) { column ->
    List(height) { row ->
        originalMatrix[row][column]
    }
}
```

- TODO
