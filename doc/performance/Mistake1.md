Sample: [`PerformanceMistake1Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake1/PerformanceMistake1Page.kt)

### The mistake: filtering a list in composition

```kotlin
@Composable
fun Screen(viewModel: MyViewModel) {
    val state by viewModel.state.collectAsState()
    // ❌ new list on every recomposition of Screen
    ItemList(items = state.items.filter { it.visible })
}
```

`filter` runs during composition, so every recomposition of `Screen` filters the
items again and allocates a new list, even when `state.items` hasn't changed.

### The sample

**Emit new state** makes `MyViewModel` emit a new `UiState` that keeps the same
`items` list and only changes an `updates` counter. `Screen` reads the state, so it
recomposes, and the filter runs again.

### What you see

After tapping **Emit new state** twice:

- `Screen compositions: 3`
- `ItemList compositions: 1`
- `filter runs: 3`

`ItemList` still **skips**. Its parameter is a `List`, an interface, so its
stability is unknown. With strong skipping, an argument of unknown stability is
compared with `equals()`: only an argument the caller knows is unstable is compared
by instance (`===`). The new filtered list holds the same items, so it is
`equals()` to the previous one (see [List parameter](../strongskipping/ListParameter.md)).

### The cost

Skipping doesn't make the mistake free. On every recomposition of `Screen`:

- the filter walks all the items and allocates a new list,
- `equals()` walks the new list and the previous one to decide whether to skip.

Both are O(n) and add garbage on every frame `Screen` recomposes, for example
when another field of the state changes often (a timer, a scroll position, a
text field).

And when the argument is compared by instance, the new list makes `ItemList`
recompose every time. That is the case when its type is known to be unstable,
like an `ArrayList` from `filterTo(ArrayList())` (see
[ArrayList parameter](../strongskipping/ArrayListParameter.md)).
