Sample: [`PerformanceMistake5Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake5/PerformanceMistake5Page.kt)

### The mistake: a lambda capturing a value recomputed in composition

```kotlin
// ❌ `selected` is a new list every recomposition → onClick lambda is new → GarbageRow recomposes
val selected = state.items.filter { it.checked }
GarbageRow(onClick = { viewModel.delete(selected) })
```

With strong skipping, **every lambda is memoized**: the compiler wraps it in a
`remember` keyed on its **captures** (here `viewModel` and `selected`). The same
lambda instance is reused as long as the captures don't change, so the composable
receiving it can skip.

`selected` is recomputed by `filter` on every recomposition of `Screen`, so the
lambda is recreated each time, and `GarbageRow` recomposes.

### The sample

- **Emit new state** emits a new `UiState` that keeps the same items and only
  changes an `updates` counter: `Screen` recomposes, the checked items don't
  change.
- The checkboxes check the items, and the trash icon deletes them.

### What you see

After tapping **Emit new state** twice, then checking an item:

- `Screen compositions: 4`
- `GarbageRow compositions: 4`

`GarbageRow` recomposes on every recomposition of `Screen`, even when no item is
checked: `selected` is then an empty list, equal to the previous one.

### Why: captures are compared by instance

The compiled `Screen` memoizes the lambda with:

```kotlin
composer.changedInstance(selected)   // ===, not equals()
```

A **capture** whose type isn't known to be stable is compared by **instance**
(`===`). `List` is an interface, so `selected` is compared by instance, and a new
list always fails the check, even with equal content.

This differs from a `List` **parameter**, which is compared with `equals()` (see
[Mistake 1](Mistake1.md)): the same filtered list passed as a parameter would let
the composable skip, but captured in a lambda it makes the lambda new.
