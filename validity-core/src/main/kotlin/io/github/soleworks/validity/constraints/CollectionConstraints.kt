package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

public fun ValidationNode<out Collection<*>>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<out Collection<*>>.minSize(
    min: Int,
    message: Collection<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

@JvmName("minSizeArray")
public fun ValidationNode<out Array<*>>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.replace("{min}", "$min") }
)

@JvmName("minSizeArray")
public fun ValidationNode<out Array<*>>.minSize(
    min: Int,
    message: Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

public fun ValidationNode<out Collection<*>>.maxSize(
    max: Int,
    message: String = messages.maxSize
): Unit = maxSize(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<out Collection<*>>.maxSize(
    max: Int,
    message: Collection<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

@JvmName("maxSizeArray")
public fun ValidationNode<out Array<*>>.maxSize(
    max: Int,
    message: String = messages.maxSize
): Unit = maxSize(
    max = max,
    message = { message.replace("{max}", "$max") }
)

@JvmName("maxSizeArray")
public fun ValidationNode<out Array<*>>.maxSize(
    max: Int,
    message: Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

public fun ValidationNode<out Collection<*>>.size(
    size: Int,
    message: String = messages.size
): Unit = size(
    size = size,
    message = { message.replace("{size}", "$size") }
)

public fun ValidationNode<out Collection<*>>.size(
    size: Int,
    message: Collection<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

@JvmName("sizeArray")
public fun ValidationNode<out Array<*>>.size(
    size: Int,
    message: String = messages.size
): Unit = size(
    size = size,
    message = { message.replace("{size}", "$size") }
)

@JvmName("sizeArray")
public fun ValidationNode<out Array<*>>.size(
    size: Int,
    message: Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

public fun ValidationNode<out Collection<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: String = messages.sizeBetween
): Unit = sizeBetween(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<out Collection<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: Collection<*>.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

@JvmName("sizeBetweenArray")
public fun ValidationNode<out Array<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: String = messages.sizeBetween
): Unit = sizeBetween(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

@JvmName("sizeBetweenArray")
public fun ValidationNode<out Array<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: Array<*>.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

public fun ValidationNode<out Collection<*>>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

@JvmName("notEmptyArray")
public fun ValidationNode<out Array<*>>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

public fun ValidationNode<out Collection<*>>.distinct(
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinct",
    predicate = { it.distinct().size == it.size }
)

@JvmName("distinctArray")
public fun ValidationNode<out Array<*>>.distinct(
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinct",
    predicate = { it.distinct().size == it.size }
)

public fun <E, K> ValidationNode<List<E>>.distinctBy(
    selector: (E) -> K,
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy(selector).size == it.size }
)

@JvmName("distinctBySet")
public fun <E, K> ValidationNode<Set<E>>.distinctBy(
    selector: (E) -> K,
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy(selector).size == it.size }
)

@JvmName("distinctByArray")
public fun <E, K> ValidationNode<Array<E>>.distinctBy(
    selector: (E) -> K,
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy(selector).size == it.size }
)

public fun <E> ValidationNode<List<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.replace("{element}", "$element") }
)

public fun <E> ValidationNode<List<E>>.contains(
    element: E?,
    message: List<E>.(E?) -> String
): Unit = constraint(
    message = { it.message(element) },
    code = "contains",
    skipped = element == null,
    predicate = { element == null || element in it }
)

@JvmName("containsSet")
public fun <E> ValidationNode<Set<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.replace("{element}", "$element") }
)

@JvmName("containsSet")
public fun <E> ValidationNode<Set<E>>.contains(
    element: E?,
    message: Set<E>.(E?) -> String
): Unit = constraint(
    message = { it.message(element) },
    code = "contains",
    skipped = element == null,
    predicate = { element == null || element in it }
)

@JvmName("containsArray")
public fun <E> ValidationNode<Array<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.replace("{element}", "$element") }
)

@JvmName("containsArray")
public fun <E> ValidationNode<Array<E>>.contains(
    element: E?,
    message: Array<E>.(E?) -> String
): Unit = constraint(
    message = { it.message(element) },
    code = "contains",
    skipped = element == null,
    predicate = { element == null || element in it }
)

public fun <E> ValidationNode<List<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.replace("{elements}", elements?.joinToString().orEmpty()) }
)

public fun <E> ValidationNode<List<E>>.containsAll(
    elements: Iterable<E>?,
    message: List<E>.(Iterable<E>?) -> String
): Unit = constraint(
    message = { it.message(elements) },
    code = "containsAll",
    skipped = elements == null,
    predicate = { elements == null || elements.all { element -> element in it } }
)

@JvmName("containsAllSet")
public fun <E> ValidationNode<Set<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.replace("{elements}", elements?.joinToString().orEmpty()) }
)

@JvmName("containsAllSet")
public fun <E> ValidationNode<Set<E>>.containsAll(
    elements: Iterable<E>?,
    message: Set<E>.(Iterable<E>?) -> String
): Unit = constraint(
    message = { it.message(elements) },
    code = "containsAll",
    skipped = elements == null,
    predicate = { elements == null || elements.all { element -> element in it } }
)

@JvmName("containsAllArray")
public fun <E> ValidationNode<Array<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.replace("{elements}", elements?.joinToString().orEmpty()) }
)

@JvmName("containsAllArray")
public fun <E> ValidationNode<Array<E>>.containsAll(
    elements: Iterable<E>?,
    message: Array<E>.(Iterable<E>?) -> String
): Unit = constraint(
    message = { it.message(elements) },
    code = "containsAll",
    skipped = elements == null,
    predicate = { elements == null || elements.all { element -> element in it } }
)

public fun <E> ValidationNode<List<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.replace("{elements}", elements?.joinToString().orEmpty()) }
)

public fun <E> ValidationNode<List<E>>.containsAny(
    elements: Iterable<E>?,
    message: List<E>.(Iterable<E>?) -> String
): Unit = constraint(
    message = { it.message(elements) },
    code = "containsAny",
    skipped = elements == null,
    predicate = { elements == null || elements.any { element -> element in it } }
)

@JvmName("containsAnySet")
public fun <E> ValidationNode<Set<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.replace("{elements}", elements?.joinToString().orEmpty()) }
)

@JvmName("containsAnySet")
public fun <E> ValidationNode<Set<E>>.containsAny(
    elements: Iterable<E>?,
    message: Set<E>.(Iterable<E>?) -> String
): Unit = constraint(
    message = { it.message(elements) },
    code = "containsAny",
    skipped = elements == null,
    predicate = { elements == null || elements.any { element -> element in it } }
)

@JvmName("containsAnyArray")
public fun <E> ValidationNode<Array<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.replace("{elements}", elements?.joinToString().orEmpty()) }
)

@JvmName("containsAnyArray")
public fun <E> ValidationNode<Array<E>>.containsAny(
    elements: Iterable<E>?,
    message: Array<E>.(Iterable<E>?) -> String
): Unit = constraint(
    message = { it.message(elements) },
    code = "containsAny",
    skipped = elements == null,
    predicate = { elements == null || elements.any { element -> element in it } }
)
