package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

/**
 * Checks that the map has at least [min] entries.
 *
 * Fails with code `minSize` and, by default, the message `must have at least {min} entries`
 * ([Messages.minEntries][io.github.soleworks.validity.Messages.minEntries]).
 *
 * @param min the fewest entries accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Map<*, *>>.minSize(
    min: Int,
    message: String = messages.minEntries
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the map has at least [min] entries.
 *
 * Fails with code `minSize` and the message that [message] builds.
 *
 * @param min the fewest entries accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<out Map<*, *>>.minSize(
    min: Int,
    message: suspend Map<*, *>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

/**
 * Checks that the map has at most [max] entries.
 *
 * Fails with code `maxSize` and, by default, the message `must have at most {max} entries`
 * ([Messages.maxEntries][io.github.soleworks.validity.Messages.maxEntries]).
 *
 * @param max the most entries accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Map<*, *>>.maxSize(
    max: Int,
    message: String = messages.maxEntries
): Unit = maxSize(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the map has at most [max] entries.
 *
 * Fails with code `maxSize` and the message that [message] builds.
 *
 * @param max the most entries accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<out Map<*, *>>.maxSize(
    max: Int,
    message: suspend Map<*, *>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

/**
 * Checks that the map has exactly [size] entries.
 *
 * Fails with code `size` and, by default, the message `must have exactly {size} entries`
 * ([Messages.entries][io.github.soleworks.validity.Messages.entries]).
 *
 * @param size the number of entries required.
 * @param message the message of the violation. `{size}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Map<*, *>>.size(
    size: Int,
    message: String = messages.entries
): Unit = size(
    size = size,
    message = { message.filled("size" to size) }
)

/**
 * Checks that the map has exactly [size] entries.
 *
 * Fails with code `size` and the message that [message] builds.
 *
 * @param size the number of entries required.
 * @param message builds the message from the value (the receiver) and [size].
 */
public fun ValidationNode<out Map<*, *>>.size(
    size: Int,
    message: suspend Map<*, *>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

/**
 * Checks that the map has between [min] and [max] entries, both included.
 *
 * Fails with code `sizeBetween` and, by default, the message `must have between {min} and {max} entries`
 * ([Messages.entriesBetween][io.github.soleworks.validity.Messages.entriesBetween]).
 *
 * @param min the fewest entries accepted.
 * @param max the most entries accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Map<*, *>>.sizeBetween(
    min: Int,
    max: Int,
    message: String = messages.entriesBetween
): Unit = sizeBetween(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the map has between [min] and [max] entries, both included.
 *
 * Fails with code `sizeBetween` and the message that [message] builds.
 *
 * @param min the fewest entries accepted.
 * @param max the most entries accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<out Map<*, *>>.sizeBetween(
    min: Int,
    max: Int,
    message: suspend Map<*, *>.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

/**
 * Checks that the map has at least one entry.
 *
 * Fails with code `notEmpty` and, by default, the message `must not be empty`
 * ([Messages.notEmpty][io.github.soleworks.validity.Messages.notEmpty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Map<*, *>>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

/**
 * Checks that the map has no entries.
 *
 * Fails with code `empty` and, by default, the message `must be empty`
 * ([Messages.empty][io.github.soleworks.validity.Messages.empty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Map<*, *>>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

/**
 * Checks that the map has the key [key].
 *
 * When [key] is `null`, nothing is checked.
 *
 * Fails with code `containsKey` and, by default, the message `must contain the key {key}`
 * ([Messages.containsKey][io.github.soleworks.validity.Messages.containsKey]).
 *
 * @param K the type of the keys.
 * @param key the key it must have.
 * @param message the message of the violation. `{key}`, `{path}` and `{value}` are filled in.
 */
public fun <K> ValidationNode<out Map<K, *>>.containsKey(
    key: K?,
    message: String = messages.containsKey
): Unit = containsKey(
    key = key,
    message = { message.filled("key" to key) }
)

/**
 * Checks that the map has the key [key].
 *
 * When [key] is `null`, nothing is checked.
 *
 * Fails with code `containsKey` and the message that [message] builds.
 *
 * @param K the type of the keys.
 * @param key the key it must have.
 * @param message builds the message from the value (the receiver) and [key].
 */
public fun <K> ValidationNode<out Map<K, *>>.containsKey(
    key: K?,
    message: suspend Map<K, *>.(K) -> String
): Unit = constraint(
    argument = key,
    message = message,
    code = "containsKey",
    predicate = { value, argument -> argument in value }
)

/**
 * Checks that the map has every one of [keys].
 *
 * When [keys] is `null`, nothing is checked.
 *
 * Fails with code `containsKeys` and, by default, the message `must contain the keys {keys}`
 * ([Messages.containsKeys][io.github.soleworks.validity.Messages.containsKeys]).
 *
 * ```kotlin
 * ::headers required { containsKeys(listOf("Authorization", "Content-Type")) }
 * ```
 *
 * @param K the type of the keys.
 * @param keys the keys it must all have.
 * @param message the message of the violation. `{keys}`, `{path}` and `{value}` are filled in.
 */
public fun <K> ValidationNode<out Map<K, *>>.containsKeys(
    keys: Iterable<K>?,
    message: String = messages.containsKeys
): Unit = containsKeys(
    keys = keys,
    message = { message.filled("keys" to keys) }
)

/**
 * Checks that the map has every one of [keys].
 *
 * When [keys] is `null`, nothing is checked.
 *
 * Fails with code `containsKeys` and the message that [message] builds.
 *
 * @param K the type of the keys.
 * @param keys the keys it must all have.
 * @param message builds the message from the value (the receiver) and [keys].
 */
public fun <K> ValidationNode<out Map<K, *>>.containsKeys(
    keys: Iterable<K>?,
    message: suspend Map<K, *>.(Iterable<K>) -> String
): Unit = constraint(
    argument = keys,
    message = message,
    code = "containsKeys",
    predicate = { value, argument -> argument.all { key -> key in value } }
)

/**
 * Checks that the map does not have the key [key].
 *
 * When [key] is `null`, nothing is checked.
 *
 * Fails with code `notContainsKey` and, by default, the message `must not contain the key {key}`
 * ([Messages.notContainsKey][io.github.soleworks.validity.Messages.notContainsKey]).
 *
 * @param K the type of the keys.
 * @param key the key it must not have.
 * @param message the message of the violation. `{key}`, `{path}` and `{value}` are filled in.
 */
public fun <K> ValidationNode<out Map<K, *>>.notContainsKey(
    key: K?,
    message: String = messages.notContainsKey
): Unit = notContainsKey(
    key = key,
    message = { message.filled("key" to key) }
)

/**
 * Checks that the map does not have the key [key].
 *
 * When [key] is `null`, nothing is checked.
 *
 * Fails with code `notContainsKey` and the message that [message] builds.
 *
 * @param K the type of the keys.
 * @param key the key it must not have.
 * @param message builds the message from the value (the receiver) and [key].
 */
public fun <K> ValidationNode<out Map<K, *>>.notContainsKey(
    key: K?,
    message: suspend Map<K, *>.(K) -> String
): Unit = constraint(
    argument = key,
    message = message,
    code = "notContainsKey",
    predicate = { value, argument -> argument !in value }
)

/**
 * Checks that every key of the map is one of [keys].
 *
 * Not every key of [keys] has to be there. Combine it with [containsKeys] for that.
 *
 * When [keys] is `null`, nothing is checked.
 *
 * Fails with code `onlyKeys` and, by default, the message `must contain only the keys {keys}`
 * ([Messages.onlyKeys][io.github.soleworks.validity.Messages.onlyKeys]).
 *
 * @param K the type of the keys.
 * @param keys the keys accepted.
 * @param message the message of the violation. `{keys}`, `{path}` and `{value}` are filled in.
 */
public fun <K> ValidationNode<out Map<K, *>>.onlyKeys(
    keys: Iterable<K>?,
    message: String = messages.onlyKeys
): Unit = onlyKeys(
    keys = keys,
    message = { message.filled("keys" to keys) }
)

/**
 * Checks that every key of the map is one of [keys].
 *
 * Not every key of [keys] has to be there. Combine it with [containsKeys] for that.
 *
 * When [keys] is `null`, nothing is checked.
 *
 * Fails with code `onlyKeys` and the message that [message] builds.
 *
 * @param K the type of the keys.
 * @param keys the keys accepted.
 * @param message builds the message from the value (the receiver) and [keys].
 */
public fun <K> ValidationNode<out Map<K, *>>.onlyKeys(
    keys: Iterable<K>?,
    message: suspend Map<K, *>.(Iterable<K>) -> String
): Unit = constraint(
    argument = keys,
    message = message,
    code = "onlyKeys",
    predicate = { value, argument -> value.keys.all { key -> key in argument } }
)

/**
 * Checks that the map has [value] as the value of some entry.
 *
 * When [value] is `null`, nothing is checked.
 *
 * Fails with code `containsValue` and, by default, the message `must contain the value {other}`
 * ([Messages.containsValue][io.github.soleworks.validity.Messages.containsValue]).
 *
 * @param V the type of the values.
 * @param value the value it must have.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun <V> ValidationNode<out Map<*, V>>.containsValue(
    value: V?,
    message: String = messages.containsValue
): Unit = containsValue(
    value = value,
    message = { message.filled("other" to value) }
)

/**
 * Checks that the map has [value] as the value of some entry.
 *
 * When [value] is `null`, nothing is checked.
 *
 * Fails with code `containsValue` and the message that [message] builds.
 *
 * @param V the type of the values.
 * @param value the value it must have.
 * @param message builds the message from the value (the receiver) and [value].
 */
public fun <V> ValidationNode<out Map<*, V>>.containsValue(
    value: V?,
    message: suspend Map<*, V>.(V) -> String
): Unit = constraint(
    argument = value,
    message = message,
    code = "containsValue",
    predicate = { map, argument -> argument in map.values }
)
