package io.github.soleworks.validity.harness

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.provider.KoPackageProvider

private val MODULES = listOf("validity-core", "validity-phone")

val production: KoScope = MODULES
    .map { module -> Konsist.scopeFromProduction(moduleName = module) }
    .reduce(KoScope::plus)

val tests: KoScope = MODULES
    .map { module -> Konsist.scopeFromTest(moduleName = module) }
    .reduce(KoScope::plus)

val KoPackageProvider.packageName: String
    get() = packagee?.name.orEmpty()

val KoFileDeclaration.topLevelTypes: Int
    get() = classes(includeNested = false).size +
        interfaces(includeNested = false).size +
        objects(includeNested = false).size

fun KoScope.sourceOf(
    testFile: KoFileDeclaration
): KoFileDeclaration? = files.firstOrNull { file ->
    file.name == testFile.name.removeSuffix("Test") && file.packageName == testFile.packageName
}
