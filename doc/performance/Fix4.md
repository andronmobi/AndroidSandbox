Sample: [`PerformanceFix4Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake4/PerformanceFix4Page.kt)

### Fix 1: derive the value you need

```kotlin
// ✅ recomposes only when the boolean flips
val showFab by remember {
    derivedStateOf { listState.firstVisibleItemIndex > 0 }
}
```

`derivedStateOf` creates a **derived state**: it reads the scroll index itself and
only notifies its readers when its **result** changes. `Screen` reads `showFab`,
not the index, so it recomposes when the boolean flips, not on every index change.

Use it when the input changes more often than the result: a threshold on a scroll
position, a "has errors" flag over a form. When the result changes as often as the
input, it only adds overhead.

### Fix 2: defer the read to the layout or draw phase

```kotlin
// ✅ reads the offset in the layout phase only
Modifier.offset { IntOffset(offsetState.value.dp.roundToPx(), 0) }
```

A frame goes through three **phases**: **composition** (what to show), **layout**
(where to place it) and **drawing** (how to render it). A state read only
invalidates the phase it happens in, and the phases after it.

`offset { }` is a **lambda-based modifier**: the lambda is called during layout,
not composition, so the state is read there. This is a **deferred read**: a change
of the offset only reruns the layout of the box, and composition is skipped.

Other **lambda-based modifiers** defer the read to the drawing phase:
`Modifier.drawBehind { }`, or `Modifier.graphicsLayer { }` for alpha, scale,
translation and rotation.

Note that the lambda returns pixels (`IntOffset`), while `offset(x = …)` takes dp.

### What you see

After scrolling the list twice, then tapping **Animate**:

- `Screen compositions: 2`: the first composition, then once when `showFab` became
  `true`
- `MovingBox compositions: 1`: the box moves, but only its layout reruns

### Vocabulary

- **Derived state** (`derivedStateOf`): a state computed from other states, which
  notifies its readers only when its result changes.
- **Phases**: composition → layout → drawing. A state read invalidates its own
  phase and the following ones.
- **Deferred read**: reading a state in a later phase (layout or drawing) instead
  of composition, so a change skips recomposition.
- **Lambda-based modifiers**: modifiers taking a lambda (`offset { }`,
  `graphicsLayer { }`, `drawBehind { }`), whose lambda runs in the layout or
  drawing phase. They are how you make a **deferred read**.
