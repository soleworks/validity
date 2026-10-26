package io.github.soleworks.validity

/**
 * Marks the receivers of the Validity DSL, so a block only sees the functions of its own receiver and not those of the
 * blocks around it.
 */
@DslMarker
public annotation class ValidityDsl
