Sample: [`PerformanceFix1Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake1/PerformanceFix1Page.kt)

### The fix: remember the filtered list

```kotlin
val visibleItems = remember(state.items) {
    state.items.filter { it.visible }
}
ItemList(items = visibleItems) // ✅ same reference → skip
```

`remember(state.items)` filters once and returns the cached list as long as
`state.items` is `equals()` to the key of the previous composition. The filter
only runs again when the items change.

### What you see

After tapping **Emit new state** twice:

- `Screen compositions: 3`
- `ItemList compositions: 1`
- `filter runs: 1`

`Screen` recomposes as before, but the filter doesn't run again, and `ItemList`
receives the same list instance. The skip check is then cheap: `equals()` returns
early when both lists are the same instance, and `===` is true as well, so
`ItemList` skips however its parameter is compared.

### Alternatives

- Filter in the `ViewModel` and expose the visible items in the state, so the
  composable does no work.
- Use `derivedStateOf` when the filter reads state that changes more often than
  its result, for example a list filtered by a scroll position.
