package io.github.soleworks.validity

public class ValidationException(
    public val violations: List<Violation>
) : RuntimeException(violations.joinToString { "${it.path}: ${it.message}" })
