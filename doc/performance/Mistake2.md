Sample: [`PerformanceMistake2Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake2/PerformanceMistake2Page.kt)

### The mistake: rebuilding lists on unrelated changes

```kotlin
// ❌ items are re-mapped every time `query` OR `isLoading` emits
val uiState = combine(itemsFlow, queryFlow, loadingFlow) { items, query, loading ->
    UiState(
        items = items.map { it.toUi() },   // new list every emission
        query = query,
        isLoading = loading
    )
}
```

`combine` runs its lambda whenever **any** of its flows emits. The items only
change when `itemsFlow` emits, but they are mapped to UI items again on every
keystroke in the search field and every change of the loading flag.

### The sample

`Mistake2ViewModel` combines three flows: the items, the search query and the
loading flag. **Item mappings** counts how many times the lambda mapped the items.
Type in **Search** or tap **Toggle loading** to make the query or the loading flag
emit, while the items stay the same.

### What you see

After typing `abc` and tapping **Toggle loading** once:

- `Item mappings: 5`: the first emission, then one per letter and one for the
  loading flag
- `Screen compositions` goes up, since `Screen` reads the new state
- `ItemList compositions: 1`

`ItemList` still **skips**. Its parameter is a `List`, whose stability is
unknown, so with strong skipping it is compared with `equals()`. `UiItem` is a
data class, so the new list is `equals()` to the previous one (see
[List parameter](../strongskipping/ListParameter.md) and
[Mistake 1](Mistake1.md)).

### The cost

On every emission, even an unrelated one:

- `toUi()` runs for every item and a new list is allocated,
- `equals()` walks the new list and the previous one to decide whether
  `ItemList` can skip.

That is O(n) work and garbage per keystroke, with `toUi()` often doing more than
this sample (formatting dates, prices, building strings).

The list **does** recompose when that comparison fails: when `UiItem` is not a
data class (its `equals()` compares instances), when it holds a field that isn't
`equals()` from one mapping to the next (a lambda created in `toUi()`), or when
the argument's type is known to be unstable and compared with `===`.
