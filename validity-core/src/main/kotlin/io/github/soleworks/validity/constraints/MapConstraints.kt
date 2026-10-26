package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

public fun ValidationNode<out Map<*, *>>.minSize(
    min: Int,
    message: String = messages.minEntries
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<out Map<*, *>>.minSize(
    min: Int,
    message: suspend Map<*, *>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

public fun ValidationNode<out Map<*, *>>.maxSize(
    max: Int,
    message: String = messages.maxEntries
): Unit = maxSize(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<out Map<*, *>>.maxSize(
    max: Int,
    message: suspend Map<*, *>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

public fun ValidationNode<out Map<*, *>>.size(
    size: Int,
    message: String = messages.entries
): Unit = size(
    size = size,
    message = { message.filled("size" to size) }
)

public fun ValidationNode<out Map<*, *>>.size(
    size: Int,
    message: suspend Map<*, *>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

public fun ValidationNode<out Map<*, *>>.sizeBetween(
    min: Int,
    max: Int,
    message: String = messages.entriesBetween
): Unit = sizeBetween(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<out Map<*, *>>.sizeBetween(
    min: Int,
    max: Int,
    message: suspend Map<*, *>.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

public fun ValidationNode<out Map<*, *>>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

public fun ValidationNode<out Map<*, *>>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKey(
    key: K?,
    message: String = messages.containsKey
): Unit = containsKey(
    key = key,
    message = { message.filled("key" to key) }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKey(
    key: K?,
    message: suspend Map<K, *>.(K) -> String
): Unit = constraint(
    argument = key,
    message = message,
    code = "containsKey",
    predicate = { value, argument -> argument in value }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKeys(
    keys: Iterable<K>?,
    message: String = messages.containsKeys
): Unit = containsKeys(
    keys = keys,
    message = { message.filled("keys" to keys) }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKeys(
    keys: Iterable<K>?,
    message: suspend Map<K, *>.(Iterable<K>) -> String
): Unit = constraint(
    argument = keys,
    message = message,
    code = "containsKeys",
    predicate = { value, argument -> argument.all { key -> key in value } }
)

public fun <K> ValidationNode<out Map<K, *>>.notContainsKey(
    key: K?,
    message: String = messages.notContainsKey
): Unit = notContainsKey(
    key = key,
    message = { message.filled("key" to key) }
)

public fun <K> ValidationNode<out Map<K, *>>.notContainsKey(
    key: K?,
    message: suspend Map<K, *>.(K) -> String
): Unit = constraint(
    argument = key,
    message = message,
    code = "notContainsKey",
    predicate = { value, argument -> argument !in value }
)

public fun <K> ValidationNode<out Map<K, *>>.onlyKeys(
    keys: Iterable<K>?,
    message: String = messages.onlyKeys
): Unit = onlyKeys(
    keys = keys,
    message = { message.filled("keys" to keys) }
)

public fun <K> ValidationNode<out Map<K, *>>.onlyKeys(
    keys: Iterable<K>?,
    message: suspend Map<K, *>.(Iterable<K>) -> String
): Unit = constraint(
    argument = keys,
    message = message,
    code = "onlyKeys",
    predicate = { value, argument -> value.keys.all { key -> key in argument } }
)

public fun <V> ValidationNode<out Map<*, V>>.containsValue(
    value: V?,
    message: String = messages.containsValue
): Unit = containsValue(
    value = value,
    message = { message.filled("other" to value) }
)

public fun <V> ValidationNode<out Map<*, V>>.containsValue(
    value: V?,
    message: suspend Map<*, V>.(V) -> String
): Unit = constraint(
    argument = value,
    message = message,
    code = "containsValue",
    predicate = { map, argument -> argument in map.values }
)
