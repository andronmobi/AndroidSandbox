Sample: [`MemoizationPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/MemoizationPage.kt)

### Lambda memoization

A lambda written inside a composable is a new object every time that code runs.
To avoid this, the compiler can **memoize** it: it wraps it in
`remember(captures) { ... }` and reuses the same instance as long as the captured
values are equal.

Without strong skipping, a lambda is memoized **only if all its captures are
stable**. A capture is a variable from the surrounding code that the lambda uses.

### The sample

Two lambdas, passed to the same composable. Each one captures a list:

```kotlin
val unstableItems = remember { arrayListOf("Bob", "François", "Ivan") }
val immutableItems = remember { listOf("John", "Olivier", "Karl").toImmutableList() }
...
LambdaComponent { println("items: $unstableItems") }  // captures an ArrayList: unstable
LambdaComponent { println("items: $immutableItems") } // captures an ImmutableList: stable
```

`LambdaComponent` is skippable: function types are stable, so `lambda` is
compared with `equals()`, which for lambdas means the same instance.

```
restartable skippable fun LambdaComponent(stable lambda: Function0<Unit>)
```

### What you see

After tapping **Recompose parent** twice:

- First `LambdaComponent`: `Compositions: 3`. The lambda captures an unstable
  `ArrayList`, so it isn't memoized. It is a new instance each time, not equal to
  the previous one, so the composable recomposes, and Logcat prints the items
  again on every tap.
- Second `LambdaComponent`: `Compositions: 1`. The lambda only captures a stable
  `ImmutableList`, so it is memoized. The same instance is passed each time, so
  the composable is skipped.

### With strong skipping

Lambdas are memoized whatever they capture: unstable captures are compared by
instance (`===`). Both composables would be skipped.

To prevent memoization explicitly, in either mode, annotate the lambda with
`@DontMemoize`:

```kotlin
LambdaComponent(@DontMemoize { println("items: $immutableItems") })
```
