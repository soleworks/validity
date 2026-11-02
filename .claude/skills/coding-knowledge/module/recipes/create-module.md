# Create a module

1. Create `validity-<name>/build.gradle.kts` ([conventions.md](../conventions.md)), with its library in
   `gradle/libs.versions.toml`.
2. Include it in `settings.gradle.kts`.
3. Add it to the modules the harness reads (`MODULES` in `harness/.../Scopes.kt`) and to the `kover`
   dependencies of the root `build.gradle.kts`.
4. Add its messages and constraints ([constraint/recipes/add-constraint.md](../../constraint/recipes/add-constraint.md)).
5. Run `./gradlew updateKotlinAbi` to create `validity-<name>/api/validity-<name>.api`.
6. Add it to the installation snippet of the README, inside the release-please markers, and to the
   modules `RELEASING.md` says the workflow publishes.
7. Run `./gradlew build`.
