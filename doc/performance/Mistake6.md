Sample: [`PerformanceMistake6Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake6/PerformanceMistake6Page.kt)

### The mistake: LazyColumn without keys

```kotlin
// ❌ insert at top → every item shifts index → all visible items recompose
LazyColumn { items(list) { ItemRow(it) } }
```

Without a key, `LazyColumn` identifies each item by its **index** (its position).
When an item is inserted at the top, every position now holds a different item:
position 0 gets the new item, position 1 gets the former first item, and so on.
Each visible `ItemRow` receives a new `item` argument, so it recomposes.

### The sample

The list starts with `Item 0` to `Item 4`. **Insert at top** adds a new item
before the others. Each row shows its own composition counter, kept with
`remember`.

### What you see

After tapping **Insert at top** twice:

```
Item 6: compositions 3
Item 5: compositions 3
Item 0: compositions 3
Item 1: compositions 3
Item 2: compositions 3
Item 3: compositions 2
Item 4: compositions 1
```

- The rows that changed position recomposed on each insert.
- `Item 6` was just created, yet it shows `3`: it got the counter remembered by
  **position 0**. Anything a row remembers (an expanded flag, a text field, an
  animation) belongs to the position, not to the item, so it **jumps to another
  item** when the list changes.
- The counts are lower at the bottom because those positions are newer: position
  5 was first composed at the first insert (`Item 3: compositions 2`), position 6
  at the second (`Item 4: compositions 1`).

### The cost

On each insert, every visible row recomposes instead of only the new one, and the
remembered state of each row is attached to the wrong item. Item animations
(`Modifier.animateItem()`) can't work either: without identity, Compose can't
tell that an item moved.
