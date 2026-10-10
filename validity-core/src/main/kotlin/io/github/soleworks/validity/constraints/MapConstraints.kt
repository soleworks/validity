package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

public fun ValidationNode<out Map<*, *>>.minSize(
    min: Int,
    message: String = messages.minEntries
): Unit = minSize(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<out Map<*, *>>.minSize(
    min: Int,
    message: Map<*, *>.(Int) -> String
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
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<out Map<*, *>>.maxSize(
    max: Int,
    message: Map<*, *>.(Int) -> String
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
    message = { message.replace("{size}", "$size") }
)

public fun ValidationNode<out Map<*, *>>.size(
    size: Int,
    message: Map<*, *>.(Int) -> String
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
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<out Map<*, *>>.sizeBetween(
    min: Int,
    max: Int,
    message: Map<*, *>.(Int, Int) -> String
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

public fun <K> ValidationNode<out Map<K, *>>.containsKey(
    key: K?,
    message: String = messages.containsKey
): Unit = containsKey(
    key = key,
    message = { message.replace("{key}", "$key") }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKey(
    key: K?,
    message: Map<K, *>.(K?) -> String
): Unit = constraint(
    message = { it.message(key) },
    code = "containsKey",
    predicate = { key == null || key in it }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKeys(
    keys: Iterable<K>?,
    message: String = messages.containsKeys
): Unit = containsKeys(
    keys = keys,
    message = { message.replace("{keys}", keys?.joinToString().orEmpty()) }
)

public fun <K> ValidationNode<out Map<K, *>>.containsKeys(
    keys: Iterable<K>?,
    message: Map<K, *>.(Iterable<K>?) -> String
): Unit = constraint(
    message = { it.message(keys) },
    code = "containsKeys",
    predicate = { keys == null || keys.all { key -> key in it } }
)
