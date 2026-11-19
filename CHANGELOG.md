# Changelog

## [0.3.0](https://github.com/soleworks/validity/compare/v0.2.0...v0.3.0) (2026-11-19)


### ⚠ BREAKING CHANGES

* **core:** add(node) and the ValidationNode constructor are internal. Put the rules of a value that is not a property with required(path, value) { }.

### Bug Fixes

* **build:** ktlint checks the files of a worktree ([#68](https://github.com/soleworks/validity/issues/68)) ([6c3a478](https://github.com/soleworks/validity/commit/6c3a4784566d80f61fc178970ab3f48186729871))


### Code Refactoring

* **core:** hide building a ValidationNode by hand ([#71](https://github.com/soleworks/validity/issues/71)) ([d3b0774](https://github.com/soleworks/validity/commit/d3b07744cb6457d3856298c4c62433d383202db6))

## [0.2.0](https://github.com/soleworks/validity/compare/v0.1.0...v0.2.0) (2026-11-16)


### ⚠ BREAKING CHANGES

* **core:** the <Country>Messages classes moved from io.github.soleworks.validity to io.github.soleworks.validity.constraints.<country>, beside their constraints. Code that names one, such as an extension on BrazilMessages, imports it from the new package; messages { brazil { ... } } is unchanged.

### Code Refactoring

* **core:** move country messages next to constraints ([#64](https://github.com/soleworks/validity/issues/64)) ([9e422a8](https://github.com/soleworks/validity/commit/9e422a80539a8db66e3436a3f7d7483dc69724c6))

## 0.1.0 (2026-11-03)


### Features

* **core:** validate tax documents of 36 countries, one by one or with taxId ([3d826fa](https://github.com/soleworks/validity/commit/3d826fac316896dfd524c3c7a50aa1353746c4f8))
* first release of Validity ([4923d8e](https://github.com/soleworks/validity/commit/4923d8e15c43e9e32390d5f19137e423ba928ed0))
