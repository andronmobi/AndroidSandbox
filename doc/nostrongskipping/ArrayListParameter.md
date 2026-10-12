Sample: [`ArrayListParameterPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/ArrayListParameterPage.kt)

### An `ArrayList` parameter is unstable

`ArrayList` is a concrete Java class. Compose has no stability information for
it, and its content can change in place, so the compiler marks it as **unstable**.
Without strong skipping, one unstable parameter makes the whole composable
non-skippable:

```
restartable fun LambdaComponent(unstable items: ArrayList<String>)
```

(`restartable` without `skippable`.)

### The sample

The list is remembered, so `LambdaComponent` receives **the same instance** on
every recomposition:

```kotlin
val items = remember { arrayListOf("Bob", "François", "Ivan") }
...
LambdaComponent(items)
```

### What you see

After tapping **Recompose parent** twice, `Compositions: 3`: `LambdaComponent`
recomposes every time, even though nothing changed.

### With strong skipping

The same code is skippable: unstable parameters are compared by instance
(`===`), and `items` is the same remembered instance, so it is skipped.

What matters is the **declared type** of the parameter, not the object passed in.
See the **List parameter** tab: the same `ArrayList` passed as a `List` is skipped.
