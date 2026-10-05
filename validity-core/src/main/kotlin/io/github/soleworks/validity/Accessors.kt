package io.github.soleworks.validity

public fun <T> ValidationNode<out Iterable<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = value.forEachIndexed { index, item -> access("$path[$index]", item, block) }

@JvmName("eachArray")
public fun <T> ValidationNode<Array<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = value.forEachIndexed { index, item -> access("$path[$index]", item, block) }

@JvmName("eachMap")
public fun <K, V> ValidationNode<out Map<K, V>>.each(
    block: ValidationNode<Map.Entry<K, V>>.() -> Unit
): Unit = value.entries.forEach { entry -> access("$path[${entry.key}]", entry, block) }

public fun <K> ValidationNode<out Map<K, *>>.eachKey(
    block: ValidationNode<K>.() -> Unit
): Unit = value.keys.forEach { key -> access("$path[$key]", key, block) }

public fun <V> ValidationNode<out Map<*, V>>.eachValue(
    block: ValidationNode<V>.() -> Unit
): Unit = value.forEach { (key, item) -> access("$path[$key]", item, block) }

public fun <V : Validatable> ValidationNode<V>.valid(): Unit = value
    .validation()
    .nodes
    .forEach { node -> add(node.prefixed(path)) }

private fun <T> ValidationNode<*>.access(
    path: String,
    value: T,
    block: ValidationNode<T>.() -> Unit
) {
    val node = ValidationNode(path, value)

    add(node)

    node.apply(block)
}
