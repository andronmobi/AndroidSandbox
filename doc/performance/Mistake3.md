Sample: [`PerformanceMistake3Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake3/PerformanceMistake3Page.kt)

### The mistake: passing the whole state object

```kotlin
// ❌ any copy() of UiState is a new instance → ItemList recomposes
ItemList(state = uiState)
```

`ItemList` only shows the items, but it receives the whole `UiState`. So it
depends on every field of the state, including the ones it never reads.

### The sample

`MyViewModel` holds a `UiState(items, isLoading)`. **Toggle loading** emits
`state.copy(isLoading = !isLoading)`: a new `UiState` that keeps the same `items`
list.

### What you see

After tapping **Toggle loading** twice:

- `Screen compositions: 3`
- `ItemList compositions: 3`

`ItemList` recomposes on every toggle, although its items never change.

### Why

To skip `ItemList`, Compose compares the new `state` argument with the previous
one. Whichever comparison it uses, it fails:

- by instance (`===`), used for an argument that isn't known to be stable:
  `copy()` always returns a new instance. `UiState` holds a `List`, so the
  compiler can't prove it stable (the compiler report shows `runtime class UiState`).
- with `equals()`: the copy differs from the previous state, since its `isLoading`
  changed. A `StateFlow` only emits a state that isn't `equals()` to the current
  one, so every new state fails this comparison.

So whatever the stability, a composable that takes the whole state recomposes on
**every** change of the state: a loading flag, a query, an error message.
