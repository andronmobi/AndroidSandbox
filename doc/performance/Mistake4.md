Sample: [`PerformanceMistake4Page.kt`](../../app/src/main/java/fr/dappli/androidsandbox/compose/performance/mistake4/PerformanceMistake4Page.kt)

### The mistake: reading fast-changing state too high

```kotlin
// ❌ reads scroll position in composition → Screen recomposes whenever the index changes
@Composable
fun Screen() {
    val listState = rememberLazyListState()
    val showFab = listState.firstVisibleItemIndex > 0
    // ...
}
```

A composable that reads a state during composition subscribes to it: every change
of that state recomposes it. `Screen` only needs a boolean, but it reads the scroll
index, so it recomposes each time the index changes.

Strong skipping can't help here: skipping decides whether a composable reruns when
its **caller** recomposes with the same arguments. `Screen` itself reads the
changing state, so it is invalidated directly.

`firstVisibleItemIndex` changes when an item scrolls past the top, not on every
pixel. Reading `firstVisibleItemScrollOffset` would recompose on every pixel
scrolled.

The same happens with an animated value read in composition:

```kotlin
// ❌ reads the offset in composition → recomposition every frame
Modifier.offset(x = offsetState.value.dp)
```

### The sample

- Scroll the list: **Screen compositions** counts the recompositions of the
  composable that reads `listState`. The **↑** button shows once the first item
  has scrolled out.
- Tap **Animate**: the box slides to 200 dp and back in 2 seconds.
  **MovingBox compositions** counts the recompositions of the composable that reads
  the offset.

### What you see

After scrolling the list twice, then tapping **Animate**:

- `Screen compositions: 8`: one per item that scrolled past the top
- `MovingBox compositions: 121`: about one per frame for 2 seconds at 60 fps
