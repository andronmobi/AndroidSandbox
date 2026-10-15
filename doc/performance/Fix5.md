Sample: [`PerformanceFix5Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake5/PerformanceFix5Page.kt)

### The fix: capture something stable, compute at click time

```kotlin
// ✅ capture something stable, compute at click time
GarbageRow(onClick = { viewModel.deleteChecked() })
```

The lambda only **captures** `viewModel`, which is `@Stable` and doesn't change.
The **memoized lambda** is reused on every recomposition, so `GarbageRow` receives
the same instance and skips.

The checked items are computed by the `ViewModel` when the row is clicked, from
the current state:

```kotlin
fun deleteChecked() {
    _state.update { state -> state.copy(items = state.items.filterNot { it.checked }) }
}
```

This also moves the filter out of composition: it runs once per click, not on
every recomposition of `Screen`.

### What you see

After tapping **Emit new state** twice, then checking an item:

- `Screen compositions: 4`
- `GarbageRow compositions: 1`

### Vocabulary

- **Lambda memoization**: with strong skipping, the compiler wraps every lambda in
  a `remember` keyed on its captures, so the same instance is reused while they
  don't change.
- **Captures**: the values a lambda reads from its enclosing scope. Stable ones are
  compared with `equals()`, the others by instance (`===`).
- **Compute at click time**: read the data in the event handler (or the
  `ViewModel`) instead of capturing a value computed in composition.
