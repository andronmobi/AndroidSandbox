Samples: [`page/`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/), shown by [`SkippingScreen.kt`](../../lib/common/src/main/java/fr/dappli/common/SkippingScreen.kt)

### What is strong skipping?

[Strong skipping](https://developer.android.com/develop/ui/compose/performance/stability/strongskipping) is a Compose compiler mode, enabled by default since Kotlin 2.0.20.
It changes two things:

1. Composables with **unstable** parameters become skippable. Unstable parameters
   are compared by instance (`===`), stable ones with `equals()`.
2. Lambdas with **unstable** captures are remembered (memoized).

This module, `:lib:nostrongskipping`, turns it off to show how Compose behaved
before:

```kotlin
composeCompiler {
    @Suppress("DEPRECATION_ERROR")
    enableStrongSkippingMode = false
}
```

> The flag is deprecated as an error and will be removed in Kotlin 2.5.0.

### The rules without strong skipping

- A composable is **skippable** unless one of its parameters is **known unstable**.
- Parameters of a skippable composable are compared with `equals()`. If they are
  all equal to the previous ones, the composable is skipped.
- A lambda is **memoized** only if everything it captures is stable.

### Stability verdicts

| Verdict | Examples | Effect |
|---|---|---|
| Stable | primitives, `String`, enums, lambdas, `ImmutableList`, `@Stable` / `@Immutable` classes, classes with only `val`s of stable types | compared with `equals()` |
| Unknown | interfaces: `List`, `MutableList`, `Map` | compared with `equals()`, doesn't prevent skipping |
| Unstable | classes with a `var`, classes with a `val` of an unstable type, Java classes such as `ArrayList` | makes the composable non-skippable |

### How to read the pages

Each page has a **Recompose parent** button. Tapping it recomposes the page's
content, which calls the child composables again with the same arguments.
Each child shows how many times it has been composed:

- `Compositions: 1` stays at 1: the child is **skipped**.
- `Compositions` goes up on every tap: the child **recomposes**.

| Tab | Shows |
|---|---|
| Var property | a `var` makes a class unstable, `@Stable` overrides it |
| ArrayList parameter | an `ArrayList` parameter makes a composable non-skippable |
| List parameter | a `List` parameter is unknown, so the composable stays skippable |
| ArrayList property | a `val` of an unstable type makes a class unstable |
| Memoization | lambdas with unstable captures are not memoized |

### Checking stability yourself

The module writes Compose compiler reports to
`lib/nostrongskipping/build/compose_compiler/`:

- `*-classes.txt`: the stability of each class.
- `*-composables.txt`: whether each composable is `restartable` and `skippable`,
  and the stability of each parameter (`stable`, `unstable`, or nothing for unknown).
