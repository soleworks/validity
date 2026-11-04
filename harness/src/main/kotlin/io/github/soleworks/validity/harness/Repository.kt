package io.github.soleworks.validity.harness

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.container.KoScope
import java.io.File

/** The library modules, whose sources the harness reads. */
internal val MODULES: List<String> = listOf("validity-core", "validity-phone")

/** The root of the repository, which the harness build passes in. */
internal val repository: File = File(System.getProperty("validity.root"))

/** The content directory of the docs site, where `docs.json` is. */
internal val docs: File = repository.resolve("docs")

/** The production sources of the library modules. */
internal val production: KoScope = MODULES
    .map { module -> Konsist.scopeFromProduction(moduleName = module) }
    .reduce(KoScope::plus)
