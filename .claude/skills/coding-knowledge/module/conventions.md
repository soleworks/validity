# Module conventions

## What a module holds

- The plugin `validity.library`, which sets explicit API, the ABI dump and publishing.
- A `description`, which becomes the published POM's description.
- `api(project(":validity-core"))`: its constraints extend core types, so a user of the module sees the
  core too. Its own library is `implementation`, unless a public signature exposes its types.
- Its code in `io.github.soleworks.validity.<name>`.
- Its messages through a `MessagesKey` ([message/conventions.md](../message/conventions.md#the-messages-of-a-module)).

```kotlin
plugins {
    id("validity.library")
}

description = "Phone number constraints for Validity, with Google's libphonenumber."

dependencies {
    api(project(":validity-core"))
    implementation(libs.libphonenumber)
}
```
