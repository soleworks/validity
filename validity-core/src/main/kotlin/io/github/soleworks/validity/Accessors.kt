package io.github.soleworks.validity

import java.math.BigDecimal
import java.math.BigInteger
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.YearMonth
import java.util.UUID
import kotlin.enums.enumEntries

private const val NULL_KEY = "null"

/**
 * Runs [block] on every element. The path of an element is the path of the collection and its index: `items[1]`.
 *
 * ```kotlin
 * ::items required {
 *     notEmpty()
 *     each { valid() }
 * }
 * ```
 *
 * @param T the type of the elements.
 * @param block the constraints of each element.
 */
public inline fun <T> ValidationNode<out Iterable<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> child(path.indexed(index), item).apply(block) }
)

/**
 * Runs [block] on every element. The path of an element is the path of the array and its index: `tags[1]`.
 *
 * @param T the type of the elements.
 * @param block the constraints of each element.
 */
@JvmName("eachArray")
public inline fun <T> ValidationNode<Array<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> child(path.indexed(index), item).apply(block) }
)

/**
 * Runs [block] on every entry. The path of an entry is the path of the map and its key: `prices[USD]`.
 *
 * Use [eachKey] or [eachValue] when the rules are only about the keys or only about the values.
 *
 * @param K the type of the keys.
 * @param V the type of the values.
 * @param block the constraints of each entry.
 */
@JvmName("eachMap")
public inline fun <K, V> ValidationNode<out Map<K, V>>.each(
    block: ValidationNode<Map.Entry<K, V>>.() -> Unit
): Unit = add(
    value.entries.map { entry -> child(path.indexed(entry.key), entry).apply(block) }
)

/**
 * Runs [block] on every key. The path of a key is the path of the map and the key: `prices[USD]`.
 *
 * ```kotlin
 * ::prices required { eachKey { currencyCode() } }
 * ```
 *
 * @param K the type of the keys.
 * @param block the constraints of each key.
 */
public inline fun <K> ValidationNode<out Map<K, *>>.eachKey(
    block: ValidationNode<K>.() -> Unit
): Unit = add(
    value.keys.map { key -> child(path.indexed(key), key).apply(block) }
)

/**
 * Runs [block] on every value. The path of a value is the path of the map and its key: `prices[USD]`.
 *
 * ```kotlin
 * ::prices required { eachValue { positive() } }
 * ```
 *
 * @param V the type of the values.
 * @param block the constraints of each value.
 */
public inline fun <V> ValidationNode<out Map<*, V>>.eachValue(
    block: ValidationNode<V>.() -> Unit
): Unit = add(
    value.map { (key, item) -> child(path.indexed(key), item).apply(block) }
)

/**
 * Checks the rules the value declares in [Validatable.validation]. Their paths are nested under the path of the value:
 * `shipping.zipCode`.
 *
 * ```kotlin
 * ::shipping required { valid() }
 * ```
 *
 * @param V the type of the value.
 */
public fun <V : Validatable> ValidationNode<V>.valid(): Unit = valid(
    validation = { it.validation() }
)

/**
 * Checks the rules that the value gives for [context]. Their paths are nested under the path of the value:
 * `items[0].quantity`.
 *
 * ```kotlin
 * ::items required { each { valid(stock) } }
 * ```
 *
 * @param C the type of the context.
 * @param V the type of the value.
 * @param context what the rules of the value read, such as data loaded by the caller.
 */
public fun <C, V : ContextualValidatable<C>> ValidationNode<V>.valid(
    context: C
): Unit = valid(
    validation = { it.validation(context) }
)

/**
 * Checks the rules that [validation] gives for the value. Their paths are nested under the path of the value:
 * `shipping.zipCode`.
 *
 * Use it for a type that is not [Validatable], such as a class of a library, or to pick other rules for it.
 *
 * @param V the type of the value.
 * @param validation gives the rules of the value, usually with [validation][io.github.soleworks.validity.validation].
 */
public fun <V> ValidationNode<V>.valid(
    validation: suspend (V) -> Validation
) {
    val metadata = metadata

    add {
        validation(value).built().nodes.map { node -> node.relocated(path.nested(node.path), metadata) }
    }
}

/**
 * Checks that [converter] can convert the value, and runs [block] on what it returns.
 *
 * When [converter] returns `null`, there is one violation and [block] does not run. The violations of [block] keep
 * the path of the value.
 *
 * ```kotlin
 * ::color required { convert({ Color.fromHex(it) }) { notEqualTo(Color.BLACK) } }
 * ```
 *
 * Fails with code `convert` and, by default, the message `has an invalid format`
 * ([Messages.convert][io.github.soleworks.validity.Messages.convert]).
 *
 * @param V the type of the value.
 * @param T the type it is converted to.
 * @param converter converts the value, or returns `null` when it cannot.
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun <V, T : Any> ValidationNode<V>.convert(
    converter: (V) -> T?,
    message: String = messages.convert,
    block: ValidationNode<T>.() -> Unit
): Unit = convert(
    converter = converter,
    message = message,
    code = "convert",
    block = block
)

/**
 * Checks that the string is a decimal number, such as `-12.5` or `1e3`, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToBigDecimal` and, by default, the message `must be a valid number`
 * ([Messages.convertToBigDecimal][io.github.soleworks.validity.Messages.convertToBigDecimal]).
 *
 * ```kotlin
 * ::amount required { convertToBigDecimal { positive() } }
 * ```
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToBigDecimal(
    message: String = messages.convertToBigDecimal,
    block: ValidationNode<BigDecimal>.() -> Unit
): Unit = convert(
    converter = String::toBigDecimalOrNull,
    message = message,
    code = "convertToBigDecimal",
    block = block
)

/**
 * Checks that the string is a whole number that fits in an [Int], and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToInt` and, by default, the message `must be a valid integer`
 * ([Messages.convertToInt][io.github.soleworks.validity.Messages.convertToInt]).
 *
 * ```kotlin
 * ::quantity required { convertToInt { between(1, 99) } }
 * ```
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToInt(
    message: String = messages.convertToInt,
    block: ValidationNode<Int>.() -> Unit
): Unit = convert(
    converter = String::toIntOrNull,
    message = message,
    code = "convertToInt",
    block = block
)

/**
 * Checks that the string is a whole number that fits in a [Long], and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToLong` and, by default, the message `must be a valid integer`
 * ([Messages.convertToLong][io.github.soleworks.validity.Messages.convertToLong]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToLong(
    message: String = messages.convertToLong,
    block: ValidationNode<Long>.() -> Unit
): Unit = convert(
    converter = String::toLongOrNull,
    message = message,
    code = "convertToLong",
    block = block
)

/**
 * Checks that the string is a whole number, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToBigInteger` and, by default, the message `must be a valid integer`
 * ([Messages.convertToBigInteger][io.github.soleworks.validity.Messages.convertToBigInteger]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToBigInteger(
    message: String = messages.convertToBigInteger,
    block: ValidationNode<BigInteger>.() -> Unit
): Unit = convert(
    converter = String::toBigIntegerOrNull,
    message = message,
    code = "convertToBigInteger",
    block = block
)

/**
 * Checks that the string is a number that [String.toDouble] reads, such as `3.14`, and runs [block] on the converted
 * value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToDouble` and, by default, the message `must be a valid number`
 * ([Messages.convertToDouble][io.github.soleworks.validity.Messages.convertToDouble]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToDouble(
    message: String = messages.convertToDouble,
    block: ValidationNode<Double>.() -> Unit
): Unit = convert(
    converter = String::toDoubleOrNull,
    message = message,
    code = "convertToDouble",
    block = block
)

/**
 * Checks that the string is `true` or `false`, in lowercase, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToBoolean` and, by default, the message `must be true or false`
 * ([Messages.convertToBoolean][io.github.soleworks.validity.Messages.convertToBoolean]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToBoolean(
    message: String = messages.convertToBoolean,
    block: ValidationNode<Boolean>.() -> Unit
): Unit = convert(
    converter = String::toBooleanStrictOrNull,
    message = message,
    code = "convertToBoolean",
    block = block
)

/**
 * Checks that the string is a UUID in its 36-character form, in either case, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToUuid` and, by default, the message `must be a valid UUID`
 * ([Messages.convertToUuid][io.github.soleworks.validity.Messages.convertToUuid]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToUuid(
    message: String = messages.convertToUuid,
    block: ValidationNode<UUID>.() -> Unit
): Unit = convert(
    converter = String::toUuidOrNull,
    message = message,
    code = "convertToUuid",
    block = block
)

/**
 * Checks that the string is the name of an entry of the enum [E], and runs [block] on that entry.
 *
 * When no entry has that name, there is one violation and [block] does not run.
 *
 * Fails with code `convertToEnum` and, by default, the message `must be one of {values}`
 * ([Messages.convertToEnum][io.github.soleworks.validity.Messages.convertToEnum]).
 *
 * ```kotlin
 * ::method required { convertToEnum<PaymentMethod> { oneOf(listOf(PaymentMethod.CARD)) } }
 * ```
 *
 * @param E the enum to convert to.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{values}` (the entry names), `{path}` and `{value}` are filled in.
 * @param block the constraints of the entry.
 */
public inline fun <reified E : Enum<E>> ValidationNode<String>.convertToEnum(
    ignoreCase: Boolean = false,
    message: String = messages.convertToEnum,
    block: ValidationNode<E>.() -> Unit
): Unit = convert(
    converter = { text -> enumEntries<E>().firstOrNull { it.name.equals(text, ignoreCase) } },
    message = message.filled("values" to enumEntries<E>().map { it.name }),
    code = "convertToEnum",
    block = block
)

/**
 * Checks that the string is an ISO 8601 date, such as `2024-02-29`, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToLocalDate` and, by default, the message `must be a valid ISO 8601 date`
 * ([Messages.convertToLocalDate][io.github.soleworks.validity.Messages.convertToLocalDate]).
 *
 * ```kotlin
 * ::birthDate required { convertToLocalDate { past() } }
 * ```
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToLocalDate(
    message: String = messages.convertToLocalDate,
    block: ValidationNode<LocalDate>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalDate.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalDate",
    block = block
)

/**
 * Checks that the string is an ISO 8601 date and time with no offset, such as `2024-02-29T13:45`, and runs [block] on
 * the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToLocalDateTime` and, by default, the message `must be a valid ISO 8601 date and time`
 * ([Messages.convertToLocalDateTime][io.github.soleworks.validity.Messages.convertToLocalDateTime]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToLocalDateTime(
    message: String = messages.convertToLocalDateTime,
    block: ValidationNode<LocalDateTime>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalDateTime.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalDateTime",
    block = block
)

/**
 * Checks that the string is an ISO 8601 time, such as `13:45`, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToLocalTime` and, by default, the message `must be a valid ISO 8601 time`
 * ([Messages.convertToLocalTime][io.github.soleworks.validity.Messages.convertToLocalTime]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToLocalTime(
    message: String = messages.convertToLocalTime,
    block: ValidationNode<LocalTime>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalTime.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalTime",
    block = block
)

/**
 * Checks that the string is an ISO 8601 date and time with an offset, such as `2024-02-29T13:45Z`, and runs [block] on
 * the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToOffsetDateTime` and, by default, the message `must be a valid ISO 8601 date and time with
 * offset` ([Messages.convertToOffsetDateTime][io.github.soleworks.validity.Messages.convertToOffsetDateTime]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToOffsetDateTime(
    message: String = messages.convertToOffsetDateTime,
    block: ValidationNode<OffsetDateTime>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { OffsetDateTime.parse(text) }.getOrNull() },
    message = message,
    code = "convertToOffsetDateTime",
    block = block
)

/**
 * Checks that the string is an ISO 8601 instant, such as `2024-02-29T13:45:00Z`, and runs [block] on the converted
 * value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToInstant` and, by default, the message `must be a valid ISO 8601 instant`
 * ([Messages.convertToInstant][io.github.soleworks.validity.Messages.convertToInstant]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToInstant(
    message: String = messages.convertToInstant,
    block: ValidationNode<Instant>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { Instant.parse(text) }.getOrNull() },
    message = message,
    code = "convertToInstant",
    block = block
)

/**
 * Checks that the string is an ISO 8601 year and month, such as `2024-02`, and runs [block] on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToYearMonth` and, by default, the message `must be a valid ISO 8601 year and month`
 * ([Messages.convertToYearMonth][io.github.soleworks.validity.Messages.convertToYearMonth]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToYearMonth(
    message: String = messages.convertToYearMonth,
    block: ValidationNode<YearMonth>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { YearMonth.parse(text) }.getOrNull() },
    message = message,
    code = "convertToYearMonth",
    block = block
)

/**
 * Checks that the string is an ISO 8601 duration that [java.time.Duration] reads, such as `PT1H30M`, and runs [block]
 * on the converted value.
 *
 * When the conversion fails, there is one violation and [block] does not run. The violations of [block] keep the path
 * of the string.
 *
 * Fails with code `convertToDuration` and, by default, the message `must be a valid ISO 8601 duration`
 * ([Messages.convertToDuration][io.github.soleworks.validity.Messages.convertToDuration]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param block the constraints of the converted value.
 */
public inline fun ValidationNode<String>.convertToDuration(
    message: String = messages.convertToDuration,
    block: ValidationNode<Duration>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { Duration.parse(text) }.getOrNull() },
    message = message,
    code = "convertToDuration",
    block = block
)

@PublishedApi
internal inline fun <V, T : Any> ValidationNode<V>.convert(
    converter: (V) -> T?,
    message: String,
    code: String,
    block: ValidationNode<T>.() -> Unit
) {
    val converted = converter(value)

    constraint(
        message = message,
        code = code,
        predicate = { converted != null }
    )

    if (converted != null)
        add(listOf(child(path, converted).apply(block)))
}

@PublishedApi
internal fun String.toUuidOrNull(): UUID? = runCatching { UUID.fromString(this) }
    .getOrNull()
    ?.takeIf { uuid -> uuid.toString().equals(this, ignoreCase = true) }

@PublishedApi
internal fun <T> ValidationNode<*>.child(
    path: String,
    value: T
): ValidationNode<T> {
    val child = ValidationNode(path, value)

    child.operator = operator
    child.metadata = metadata

    return child
}

private fun String.nested(
    path: String
): String = if (path.isEmpty())
    this
else
    "$this.$path"

@PublishedApi
internal fun String.indexed(
    key: Any?
): String = "$this[${key ?: NULL_KEY}]"
