Sample: [`VarPropertyPage.kt`](../../lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page/VarPropertyPage.kt)

### A `var` makes a class unstable

```kotlin
data class UnstablePerson(var name: String)

@Stable
data class StablePerson(var name: String)
```

`name` can change after the object is built, and Compose would not be notified,
so the compiler marks `UnstablePerson` as **unstable**. `@Stable` overrides the
inference: `StablePerson` is treated as **stable**.

From the compiler report:

```
unstable class UnstablePerson { stable var name: String }
stable class StablePerson { stable var name: String }

restartable fun LambdaComponent(unstable unstablePerson: UnstablePerson)
restartable skippable fun StableComponent(stable stablePerson: StablePerson)
```

### The sample

Both objects are created again on every recomposition, with the same name:

```kotlin
LambdaComponent(UnstablePerson("Bob"))
StableComponent(StablePerson("Frank"))
```

### What you see

After tapping **Recompose parent** twice:

```
Compositions: 3     ← LambdaComponent: not skippable, recomposes every time
 - Bob
───────────────
Compositions: 1     ← StableComponent: the new StablePerson equals() the old one, skipped
 - Frank
```

### Be careful with `@Stable`

`@Stable` is a promise: whenever a public property changes, Compose is notified.
A plain `var` breaks that promise. If you wrote `stablePerson.name = "Paul"` on a
remembered instance, `StableComponent` would be skipped and keep showing the old
name. Back the property with `mutableStateOf` to keep the promise:

```kotlin
@Stable
class StablePerson(name: String) {
    var name by mutableStateOf(name)
}
```
