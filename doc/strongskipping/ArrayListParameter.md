Sample: [`ArrayListParameterPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/ArrayListParameterPage.kt)

### An `ArrayList` parameter no longer prevents skipping

`ArrayList` is still **unstable**, but with strong skipping the composable is
skippable anyway:

```
restartable skippable fun LambdaComponent(unstable items: ArrayList<String>)
```

The unstable parameter is compared by instance (`===`).

### The sample

The list is remembered, so `LambdaComponent` receives **the same instance** on
every recomposition:

```kotlin
val items = remember { arrayListOf("Bob", "François", "Ivan") }
...
LambdaComponent(items)
```

### What you see

After tapping **Recompose parent** twice, `Compositions: 1`: `items` is the same
instance, so `LambdaComponent` is skipped. Without strong skipping it showed
`Compositions: 3`.

### The risk

Instance comparison can't see changes inside the object. If the list were mutated
in place (`items.add("Paul")`), it would still be the same instance, so
`LambdaComponent` would be skipped and show the old list. Replace the list instead
of mutating it, or use `mutableStateListOf()` or an `ImmutableList`.
