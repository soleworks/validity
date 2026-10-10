package io.github.soleworks.validity

public fun <T> ValidationNode<out Iterable<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> ValidationNode("$path[$index]", item).apply(block) }
)

@JvmName("eachArray")
public fun <T> ValidationNode<Array<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> ValidationNode("$path[$index]", item).apply(block) }
)

@JvmName("eachMap")
public fun <K, V> ValidationNode<out Map<K, V>>.each(
    block: ValidationNode<Map.Entry<K, V>>.() -> Unit
): Unit = add(
    value.entries.map { entry -> ValidationNode("$path[${entry.key}]", entry).apply(block) }
)

public fun <K> ValidationNode<out Map<K, *>>.eachKey(
    block: ValidationNode<K>.() -> Unit
): Unit = add(
    value.keys.map { key -> ValidationNode("$path[$key]", key).apply(block) }
)

public fun <V> ValidationNode<out Map<*, V>>.eachValue(
    block: ValidationNode<V>.() -> Unit
): Unit = add(
    value.map { (key, item) -> ValidationNode("$path[$key]", item).apply(block) }
)

public fun <V : Validatable> ValidationNode<V>.valid(): Unit = valid(
    validation = { it.validation() }
)

public fun <V> ValidationNode<V>.valid(
    validation: (V) -> Validation
): Unit = add(
    validation(value).nodes.map { node -> node.prefixed(path) }
)
