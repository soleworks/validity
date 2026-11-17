# Changelog

## [0.2.0](https://github.com/soleworks/validity/compare/v0.1.0...v0.2.0) (2026-11-16)


### ⚠ BREAKING CHANGES

* **core:** the <Country>Messages classes moved from io.github.soleworks.validity to io.github.soleworks.validity.constraints.<country>, beside their constraints. Code that names one, such as an extension on BrazilMessages, imports it from the new package; messages { brazil { ... } } is unchanged.

### Code Refactoring

* **core:** move country messages next to constraints ([#64](https://github.com/soleworks/validity/issues/64)) ([9e422a8](https://github.com/soleworks/validity/commit/9e422a80539a8db66e3436a3f7d7483dc69724c6))

## 0.1.0 (2026-11-03)


### Features

* **core:** validate tax documents of 36 countries, one by one or with taxId ([3d826fa](https://github.com/soleworks/validity/commit/3d826fac316896dfd524c3c7a50aa1353746c4f8))
* first release of Validity ([4923d8e](https://github.com/soleworks/validity/commit/4923d8e15c43e9e32390d5f19137e423ba928ed0))
