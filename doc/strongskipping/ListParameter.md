Sample: [`ListParameterPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/ListParameterPage.kt)

### A `List` parameter: nothing changes here

`List` is an interface, so its stability is still **unknown**, and the composable
is skippable in both modes:

```
restartable skippable fun UnknownStabilityComponent(items: List<String>)
```

The difference is how the parameter is compared. Without strong skipping, it is
always compared with `equals()`. With strong skipping, the generated code picks
at runtime: `equals()` if the caller knows the argument is stable, instance
comparison (`===`) otherwise.

### The sample

The same composable receives a mutable `ArrayList` and a read-only `listOf`, both
remembered:

```kotlin
val unstableItems = remember { arrayListOf("Bob", "François", "Ivan") }
val immutableItems = remember { listOf("John", "Olivier", "Karl") }
...
UnknownStabilityComponent(unstableItems)
UnknownStabilityComponent(immutableItems)
```

### What you see

After tapping **Recompose parent** twice, both show `Compositions: 1`, as without
strong skipping. Both lists are remembered, so they are the same instance and
`equals()` to the previous one: either comparison skips.

The difference would show with a **new, equal** list on every recomposition, like
`listOf("John")` written inline: `equals()` skips it, `===` doesn't.

### The risk

As before, a list mutated in place is skipped and the UI goes stale. Prefer
`mutableStateListOf()` or an `ImmutableList`.
