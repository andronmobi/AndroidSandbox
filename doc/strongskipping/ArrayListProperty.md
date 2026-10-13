Sample: [`ArrayListPropertyPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/ArrayListPropertyPage.kt)

### A `val` of an unstable type: the class is unstable, but skippable

The class inference doesn't change: `UnstablePersons` is unstable because of its
`ArrayList` property, and `@Stable` overrides it for `StablePersons`:

```kotlin
data class UnstablePersons(val names: ArrayList<String>)

@Stable
data class StablePersons(val names: ArrayList<String>)
```

With strong skipping, both composables are skippable:

```
restartable skippable fun LambdaComponent(unstable persons: UnstablePersons)
restartable skippable fun StableComponent(stable persons: StablePersons)
```

`persons` is compared by instance (`===`) in `LambdaComponent`, with `equals()` in
`StableComponent`.

### The sample

Both objects are remembered, so each composable receives the same instance on
every recomposition:

```kotlin
val unstablePersons = remember { UnstablePersons(arrayListOf("Bob", "François", "Ivan")) }
val stablePersons = remember { StablePersons(arrayListOf("John", "Olivier", "Karl")) }
...
LambdaComponent(unstablePersons)
StableComponent(stablePersons)
```

### What you see

After tapping **Recompose parent** twice, both show `Compositions: 1`.
`LambdaComponent` is skipped because it gets the same instance. Without strong
skipping, it showed `Compositions: 3`.

Here `@Stable` makes no visible difference. It still matters when a **new, equal**
instance is passed: `equals()` skips it, `===` doesn't.

### The risk

If `names` were mutated in place, both composables would be skipped and show the
old list. Use `mutableStateListOf()` or an `ImmutableList`.
