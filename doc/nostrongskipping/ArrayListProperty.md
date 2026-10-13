Sample: [`ArrayListPropertyPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/ArrayListPropertyPage.kt)

### A `val` of an unstable type makes a class unstable

A class is stable only if all its properties are `val`s of stable types.
`names` is a `val`, but its type, `ArrayList`, is unstable, so the class is
unstable too:

```kotlin
data class UnstablePersons(val names: ArrayList<String>)

@Stable
data class StablePersons(val names: ArrayList<String>)
```

From the compiler report:

```
unstable class UnstablePersons { unstable val names: ArrayList<String> }
stable class StablePersons { unstable val names: ArrayList<String> }

restartable fun LambdaComponent(unstable persons: UnstablePersons)
restartable skippable fun StableComponent(stable persons: StablePersons)
```

`@Stable` overrides the inference: `StablePersons` is stable even though its
property isn't.

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

After tapping **Recompose parent** twice:

- `LambdaComponent`: `Compositions: 3`. It is not skippable, so it recomposes
  even though it gets the same instance.
- `StableComponent`: `Compositions: 1`. The instance is `equals()` to itself, so it
  is skipped.

### Be careful with `@Stable`

If `stablePersons.names` were mutated in place, `StableComponent` would still be
skipped and show the old list. `@Stable` only holds if every change is visible to
Compose, for example by using `mutableStateListOf()` or an `ImmutableList`.
