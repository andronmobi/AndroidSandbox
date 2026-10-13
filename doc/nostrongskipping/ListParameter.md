Sample: [`ListParameterPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/ListParameterPage.kt)

### A `List` parameter is unknown, not unstable

`List` is an interface. At compile time, the compiler can't know which
implementation will be passed: an `ArrayList`, a `listOf(...)`, a
`persistentListOf(...)`... So its stability is **unknown**.

An unknown parameter doesn't prevent skipping. It is compared with `equals()`:

```
restartable skippable fun UnknownStabilityComponent(items: List<String>)
```

(No `stable` or `unstable` label on the parameter means unknown.)

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

After tapping **Recompose parent** twice, both show `Compositions: 1`: both are
skipped, including the one that receives an `ArrayList`. Stability is decided
from the declared type (`List`), not from the object passed in.

Compare with the **ArrayList parameter** tab, where the same list declared as
`ArrayList<String>` is never skipped.

### The risk

Compose bets that a `List` won't change behind its back. If the `ArrayList` is
mutated in place (`unstableItems.add("Paul")`), it is still `equals()` to itself,
so the composable is skipped and the UI goes stale. To be safe:

- replace the list instead of mutating it: `items = items + "Paul"` with `mutableStateOf`,
- or use `mutableStateListOf()`, whose changes Compose observes,
- or use `ImmutableList` from `kotlinx.collections.immutable`.
