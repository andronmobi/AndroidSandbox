Sample: [`PerformanceFix3Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake3/PerformanceFix3Page.kt)

### The fix: pass only the fields the composable uses

```kotlin
// ✅ only depends on what it uses
ItemList(items = uiState.items)
```

With `state.copy(isLoading = true)`, `uiState` is a new object, but
`uiState.items` keeps the same reference. Passing small parameters lets `===` do
its job.

### What you see

After tapping **Toggle loading** twice:

- `Screen compositions: 3`
- `ItemList compositions: 1`

`Screen` still recomposes, since it reads the new state, but `ItemList` gets the
same list instance and skips. However the parameter is compared, `===` or
`equals()` (which returns early for the same instance), it is cheap and it skips.

### Going further

- Split the state for the leaves in the same way: a header that shows
  `isLoading` takes a `Boolean`, not the `UiState`.
- Keep `copy()` from creating new lists: copying the state keeps the references
  of the fields that don't change, but `items = items.toList()` or a `map` in the
  `ViewModel` would give a new list again (see [Mistake 2](Mistake2.md)).
