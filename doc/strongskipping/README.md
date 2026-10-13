Samples: [`page/`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/), compiled a second time by `:app`

### Same samples, strong skipping on

[Strong skipping](https://developer.android.com/develop/ui/compose/performance/stability/strongskipping)
is decided by the Compose compiler, per module, when the code is compiled. To compare
both modes with the same code, the pages of `:lib:nostrongskipping` are compiled twice:

- by `:lib:nostrongskipping`, with `enableStrongSkippingMode = false`,
- by `:app`, with strong skipping on (the default since Kotlin 2.0.20). A Gradle task,
  `strongSkippingPages` in `app/build.gradle.kts`, copies the pages into the
  `fr.dappli.androidsandbox.compose.strongskipping.page` package, so the two copies
  don't clash.

### The rules with strong skipping

- **Every** restartable composable is skippable, whatever the stability of its parameters.
- **Stable** parameters are compared with `equals()`, **unstable** ones by
  instance (`===`).
- **Every** lambda is memoized. Unstable captures are compared by instance (`===`).

To opt out, annotate a composable with `@NonSkippableComposable`, or a lambda with
`@DontMemoize`.

### How to read the pages

Same as without strong skipping: tap **Recompose parent**, then look at each
child's counter.

- `Compositions: 1` stays at 1: the child is **skipped**.
- `Compositions` goes up on every tap: the child **recomposes**.

| Tab | Without strong skipping | With strong skipping |
|---|---|---|
| Var property | unstable child recomposes | unstable child still recomposes: a new instance every time |
| ArrayList parameter | recomposes | **skipped**: same instance |
| List parameter | skipped | skipped |
| ArrayList property | unstable child recomposes | **skipped**: same instance |
| Memoization | lambda with unstable capture recomposes | **skipped**: lambda memoized |

### Checking stability yourself

`:app` writes Compose compiler reports to `app/build/compose_compiler/`. Look for
the `fr.dappli.androidsandbox.compose.strongskipping.page` functions in
`app-composables.txt`: they are all `restartable skippable`, even those with an
`unstable` parameter.
