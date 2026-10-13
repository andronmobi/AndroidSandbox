Sample: [`VarPropertyPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/VarPropertyPage.kt)

### A `var` still makes a class unstable

Strong skipping doesn't change stability inference:

```kotlin
data class UnstablePerson(var name: String)

@Stable
data class StablePerson(var name: String)
```

It changes what an unstable parameter means. From the compiler report:

```
restartable skippable fun UnstableComponent(unstable unstablePerson: UnstablePerson)
restartable skippable fun StableComponent(stable stablePerson: StablePerson)
```

`UnstableComponent` is now `skippable` too. Its unstable parameter is compared by
instance (`===`), the stable one keeps using `equals()`.

### The sample

Both objects are created again on every recomposition, with the same name:

```kotlin
UnstableComponent(UnstablePerson("Bob"))
StableComponent(StablePerson("Frank"))
```

### What you see

After tapping **Recompose parent** twice:

```
Compositions: 3     ← UnstableComponent: a new UnstablePerson, not === the old one
 - Bob
───────────────
Compositions: 1     ← StableComponent: the new StablePerson equals() the old one, skipped
 - Frank
```

The same result as without strong skipping, for a different reason:
`UnstableComponent` is skippable, but it receives a **new instance** each time, and
instance comparison ignores `equals()`. Remember the object
(`remember { UnstablePerson("Bob") }`) and it would be skipped.

### Be careful with `@Stable`

The trap is the same in both modes: `@Stable` with a plain `var` hides changes from
Compose. Back the property with `mutableStateOf`.
