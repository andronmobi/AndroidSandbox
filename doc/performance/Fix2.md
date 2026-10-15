Sample: [`PerformanceFix2Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake2/PerformanceFix2Page.kt)

### The fix: map the items in their own flow

```kotlin
// ✅ map items only when the source items change
private val uiItems = itemsFlow.map { list -> list.map { it.toUi() } }

val uiState = combine(uiItems, queryFlow, loadingFlow) { items, query, loading ->
    UiState(items, query, loading)  // same `items` reference when only query changes
}
```

The mapping moves upstream of `combine`, on `itemsFlow` alone. `combine` keeps
the latest value of each flow, so when the query or the loading flag emits, it
reuses the list `uiItems` mapped last time.

### What you see

After typing `abc` and tapping **Toggle loading** once:

- `Item mappings: 1`: the items were mapped once, when `itemsFlow` first emitted
- `Screen compositions` goes up as before
- `ItemList compositions: 1`

`ItemList` receives the same list instance on every emission. `equals()` returns
early when both lists are the same instance, and `===` is true as well, so
`ItemList` skips however its parameter is compared, without walking the list.

### Going further

- When the items come from a `Flow` that can emit the same content again (a
  database query re-running, for example), `distinctUntilChanged()` before the
  mapping skips it. A `StateFlow` already drops values `equals()` to the current one.
- When the query filters the items, filter in its own step too, for example
  `combine(uiItems, queryFlow) { items, query -> items.filter { ... } }`, so the
  loading flag doesn't refilter them.
