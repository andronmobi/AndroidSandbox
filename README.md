# AndroidSandbox

AndroidSandbox is where I experiment with small, focused examples and keep notes on what I learn along the way. It’s also a personal record I can come back to later, and I’m sharing it here in case it helps other developers too.

## Compose

- [compositionLocalOf vs staticCompositionLocalOf](doc/CompositionLocal.md)
- [Skippability without strong skipping](doc/nostrongskipping/README.md)
  - [Var property](doc/nostrongskipping/VarProperty.md)
  - [ArrayList parameter](doc/nostrongskipping/ArrayListParameter.md)
  - [List parameter](doc/nostrongskipping/ListParameter.md)
  - [ArrayList property](doc/nostrongskipping/ArrayListProperty.md)
  - [Memoization](doc/nostrongskipping/Memoization.md) 
- [Skippability with strong skipping](doc/strongskipping/README.md)
  - [Var property](doc/strongskipping/VarProperty.md)
  - [ArrayList parameter](doc/strongskipping/ArrayListParameter.md)
  - [List parameter](doc/strongskipping/ListParameter.md)
  - [ArrayList property](doc/strongskipping/ArrayListProperty.md)
  - [Memoization](doc/strongskipping/Memoization.md)
- Performance mistakes
  - [Mistake 1: filtering a list in composition](doc/performance/Mistake1.md)
  - [Fix 1: remember the filtered list](doc/performance/Fix1.md)
  - [Mistake 2: rebuilding lists on unrelated changes](doc/performance/Mistake2.md)
  - [Fix 2: map the items in their own flow](doc/performance/Fix2.md)
