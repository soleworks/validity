package io.github.soleworks.validity

/**
 * Thrown by [validateOrThrow] and [ValidationResult.orThrow] when a rule is broken.
 *
 * Its message lists each violation as `path: message`.
 *
 * @property violations every broken rule, in the order the rules are declared.
 */
public class ValidationException(
    public val violations: List<Violation>
) : RuntimeException(violations.joinToString { "${it.path}: ${it.message}" })
