Sample: [`MemoizationPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/MemoizationPage.kt)

### Every lambda is memoized

With strong skipping, the compiler memoizes **every** lambda written in a
composable, whatever it captures. It wraps the lambda in `remember(captures) { ... }`:

- stable captures are compared with `equals()`,
- unstable captures by instance (`===`).

### The sample

Two lambdas, passed to the same composable. Each one captures a list:

```kotlin
val unstableItems = remember { arrayListOf("Bob", "François", "Ivan") }
val immutableItems = remember { listOf("John", "Olivier", "Karl").toImmutableList() }
...
LambdaComponent { println("items: $unstableItems") }  // captures an ArrayList: unstable
LambdaComponent { println("items: $immutableItems") } // captures an ImmutableList: stable
```

```
restartable skippable fun LambdaComponent(stable lambda: Function0<Unit>)
```

### What you see

After tapping **Recompose parent** twice, both show `Compositions: 1`.

- First `LambdaComponent`: the captured `ArrayList` is remembered, so it is the
  same instance on every recomposition. The lambda is memoized, the same lambda
  instance is passed, and the composable is skipped. Without strong skipping, it
  showed `Compositions: 3` and Logcat printed the items on every tap.
- Second `LambdaComponent`: skipped, as without strong skipping.

### Opting out

To get a new lambda on every recomposition, annotate it with `@DontMemoize`:

```kotlin
LambdaComponent(@DontMemoize { println("items: $unstableItems") })
```
