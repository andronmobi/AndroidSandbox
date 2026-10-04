Sample: [`CompositionLocalScreen.kt`](../app/src/main/java/fr/dappli/androidsandbox/compose/compositionlocal/CompositionLocalScreen.kt)

### What is a CompositionLocal?

A `CompositionLocal` passes data down the composition tree implicitly, without
adding a parameter to every composable in between. You:

1. **declare** it with a default value: `compositionLocalOf { default }` or `staticCompositionLocalOf { default }`
2. **provide** a value for a subtree: `CompositionLocalProvider(LocalX provides value) { ... }`
3. **read** it anywhere inside that subtree: `LocalX.current`

If nothing provides a value, `.current` returns the default. In the sample, that's
`DEFAULT_COUNTER_IF_NOT_PROVIDED` (`0`).

Compose itself uses this pattern for `LocalContext`, `LocalDensity`,
`MaterialTheme` (`LocalColorScheme`, `LocalTypography`), and others.

### The two factories

| | `compositionLocalOf` | `staticCompositionLocalOf` |
|---|---|---|
| Tracks who reads `.current` | Yes | No |
| When the provided value changes | Only the composables that **read** `.current` recompose | The **whole content** of the `CompositionLocalProvider` recomposes |
| Cost of reading | Slightly higher (each read is tracked) | Lower (no tracking) |
| Use it for | Values that change | Values that rarely or never change (theme, configuration, injected dependencies) |

### The sample

```kotlin
private val LocalDynamicCounter = compositionLocalOf { DEFAULT_COUNTER_IF_NOT_PROVIDED }
private val LocalStaticCounter = staticCompositionLocalOf { DEFAULT_COUNTER_IF_NOT_PROVIDED }
```

The screen holds one `counter` state, starting at `1`. An **Update provider**
button increments it. The same `counter` is provided to both locals:

```kotlin
CompositionLocalProvider(LocalDynamicCounter provides counter) {
    MyComponent("compositionLocalOf")              // does NOT read the local
    Text("counter = ${LocalDynamicCounter.current}") // reads the local
}

CompositionLocalProvider(LocalStaticCounter provides counter) {
    MyComponent("staticCompositionLocalOf")        // does NOT read the local
    Text("counter = ${LocalStaticCounter.current}")  // reads the local
}
```

`MyComponent` never reads either local. Its `name` parameter never changes, so it
is skippable. It shows how many times it has been composed:

```kotlin
@Composable
private fun MyComponent(name: String) {
    val composition = rememberCompositionCount()
    Text("with $name")
    Text("composition = $composition")
}
```

#### Counting compositions

```kotlin
@Composable
private fun rememberCompositionCount(): Int {
    val count = remember { IntArray(1) }
    count[0]++
    return count[0]
}
```

`remember` keeps the same `IntArray` across recompositions, and the function
increments it every time the calling composable runs. The array is not a `State`,
so changing it doesn't trigger another recomposition.

> This writes during composition, which is fine for a debugging demo but should be
> avoided in real code. Use the Layout Inspector's recomposition counts instead.

#### What you see

After tapping **Update provider** twice:

```
with compositionLocalOf
composition = 1          ← skipped: MyComponent doesn't read the local
counter = 3              ← updated: this Text reads LocalDynamicCounter.current
───────────────
with staticCompositionLocalOf
composition = 3          ← recomposed each time, even though it doesn't read the local
counter = 3
───────────────
```

- **`compositionLocalOf`**: Compose knows exactly which composables read the
  local. Only the `Text` that reads `.current` is invalidated. `MyComponent` is
  skipped, so its count stays at `1`.
- **`staticCompositionLocalOf`**: Compose doesn't track reads. When the value
  changes, it can't know who depends on it, so it recomposes the whole provider
  content without skipping. `MyComponent` recomposes even though it never reads the
  local, so its count goes up on every tap.

### Which one to choose

- The value **changes** (a counter, user state, anything driven by `State`):
  use `compositionLocalOf`, so only the readers recompose.
- The value is **set once or almost never changes** (theme, `Context`, a
  repository or other dependency): use `staticCompositionLocalOf`. Reads are
  cheaper, and the rare full recomposition doesn't matter.
- If in doubt, use `compositionLocalOf`.

Also consider whether you need a `CompositionLocal` at all. Explicit
parameters are easier to follow and test. Keep locals for data that's truly
cross-cutting, not for passing ordinary state down a few levels.
