Sample: [`PerformanceFix6Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake6/PerformanceFix6Page.kt)

### The fix: give each item a stable key

```kotlin
// ✅ items are tracked by identity
LazyColumn { items(list, key = { it.id }) { ItemRow(it) } }
```

With a **key**, `LazyColumn` identifies each item by its **identity** instead of
its index. When an item is inserted at the top, the existing items keep their
composition and only **move**: they receive the same `item` argument, so they
skip. Only the new item is composed.

### What you see

After tapping **Insert at top** twice, every row shows `compositions 1`:

```
Item 6: compositions 1
Item 5: compositions 1
Item 0: compositions 1
...
```

The new items start with their own counter, and the existing ones keep theirs:
the remembered state follows the item.

### Choosing the key

- It must be **unique** in the list: a duplicate key crashes the `LazyColumn`.
- It must be **stable**: the same item keeps the same key across updates. Use an
  id from your data, not the index or a random value.
- It must be saveable in a `Bundle` (`Int`, `Long`, `String`…), since the scroll
  position is restored by key.

### Vocabulary

- **Item key**: the identity of an item in a lazy list (`key = { it.id }`). The
  composition and remembered state of an item are attached to its key.
- **Positional identity**: the default without keys, where an item is identified
  by its index, so a change of position looks like a change of item.
- **Item animations**: with keys, `Modifier.animateItem()` can animate insertions,
  removals and moves, since Compose knows which item went where.
