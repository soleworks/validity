package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

public fun ValidationNode<out Collection<*>>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<out Collection<*>>.minSize(
    min: Int,
    message: suspend Collection<*>.(Int) -> String
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
    message = { message.filled("min" to min) }
)

@JvmName("minSizeArray")
public fun ValidationNode<out Array<*>>.minSize(
    min: Int,
    message: suspend Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

@JvmName("minSizeByteArray")
public fun ValidationNode<ByteArray>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

@JvmName("minSizeByteArray")
public fun ValidationNode<ByteArray>.minSize(
    min: Int,
    message: suspend ByteArray.(Int) -> String
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
    message = { message.filled("max" to max) }
)

public fun ValidationNode<out Collection<*>>.maxSize(
    max: Int,
    message: suspend Collection<*>.(Int) -> String
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
    message = { message.filled("max" to max) }
)

@JvmName("maxSizeArray")
public fun ValidationNode<out Array<*>>.maxSize(
    max: Int,
    message: suspend Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

@JvmName("maxSizeByteArray")
public fun ValidationNode<ByteArray>.maxSize(
    max: Int,
    message: String = messages.maxSize
): Unit = maxSize(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxSizeByteArray")
public fun ValidationNode<ByteArray>.maxSize(
    max: Int,
    message: suspend ByteArray.(Int) -> String
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
    message = { message.filled("size" to size) }
)

public fun ValidationNode<out Collection<*>>.size(
    size: Int,
    message: suspend Collection<*>.(Int) -> String
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
    message = { message.filled("size" to size) }
)

@JvmName("sizeArray")
public fun ValidationNode<out Array<*>>.size(
    size: Int,
    message: suspend Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

@JvmName("sizeByteArray")
public fun ValidationNode<ByteArray>.size(
    size: Int,
    message: String = messages.size
): Unit = size(
    size = size,
    message = { message.filled("size" to size) }
)

@JvmName("sizeByteArray")
public fun ValidationNode<ByteArray>.size(
    size: Int,
    message: suspend ByteArray.(Int) -> String
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
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<out Collection<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: suspend Collection<*>.(Int, Int) -> String
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
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

@JvmName("sizeBetweenArray")
public fun ValidationNode<out Array<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: suspend Array<*>.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

@JvmName("sizeBetweenByteArray")
public fun ValidationNode<ByteArray>.sizeBetween(
    min: Int,
    max: Int,
    message: String = messages.sizeBetween
): Unit = sizeBetween(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

@JvmName("sizeBetweenByteArray")
public fun ValidationNode<ByteArray>.sizeBetween(
    min: Int,
    max: Int,
    message: suspend ByteArray.(Int, Int) -> String
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

@JvmName("notEmptyByteArray")
public fun ValidationNode<ByteArray>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

public fun ValidationNode<out Collection<*>>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

@JvmName("emptyArray")
public fun ValidationNode<out Array<*>>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

@JvmName("emptyByteArray")
public fun ValidationNode<ByteArray>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
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
    selector: suspend (E) -> K
): Unit = distinctBy(
    message = messages.distinct,
    selector = selector
)

public fun <E, K> ValidationNode<List<E>>.distinctBy(
    message: String,
    selector: suspend (E) -> K
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy { element -> selector(element) }.size == it.size }
)

@JvmName("distinctBySet")
public fun <E, K> ValidationNode<Set<E>>.distinctBy(
    selector: suspend (E) -> K
): Unit = distinctBy(
    message = messages.distinct,
    selector = selector
)

@JvmName("distinctBySet")
public fun <E, K> ValidationNode<Set<E>>.distinctBy(
    message: String,
    selector: suspend (E) -> K
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy { element -> selector(element) }.size == it.size }
)

@JvmName("distinctByArray")
public fun <E, K> ValidationNode<Array<E>>.distinctBy(
    selector: suspend (E) -> K
): Unit = distinctBy(
    message = messages.distinct,
    selector = selector
)

@JvmName("distinctByArray")
public fun <E, K> ValidationNode<Array<E>>.distinctBy(
    message: String,
    selector: suspend (E) -> K
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy { element -> selector(element) }.size == it.size }
)

public fun <E> ValidationNode<List<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.filled("element" to element) }
)

public fun <E> ValidationNode<List<E>>.contains(
    element: E?,
    message: suspend List<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "contains",
        predicate = { element in it }
    )
}

@JvmName("containsSet")
public fun <E> ValidationNode<Set<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.filled("element" to element) }
)

@JvmName("containsSet")
public fun <E> ValidationNode<Set<E>>.contains(
    element: E?,
    message: suspend Set<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "contains",
        predicate = { element in it }
    )
}

@JvmName("containsArray")
public fun <E> ValidationNode<Array<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.filled("element" to element) }
)

@JvmName("containsArray")
public fun <E> ValidationNode<Array<E>>.contains(
    element: E?,
    message: suspend Array<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "contains",
        predicate = { element in it }
    )
}

public fun <E> ValidationNode<List<E>>.notContains(
    element: E?,
    message: String = messages.notContainsElement
): Unit = notContains(
    element = element,
    message = { message.filled("element" to element) }
)

public fun <E> ValidationNode<List<E>>.notContains(
    element: E?,
    message: suspend List<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "notContains",
        predicate = { element !in it }
    )
}

@JvmName("notContainsSet")
public fun <E> ValidationNode<Set<E>>.notContains(
    element: E?,
    message: String = messages.notContainsElement
): Unit = notContains(
    element = element,
    message = { message.filled("element" to element) }
)

@JvmName("notContainsSet")
public fun <E> ValidationNode<Set<E>>.notContains(
    element: E?,
    message: suspend Set<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "notContains",
        predicate = { element !in it }
    )
}

@JvmName("notContainsArray")
public fun <E> ValidationNode<Array<E>>.notContains(
    element: E?,
    message: String = messages.notContainsElement
): Unit = notContains(
    element = element,
    message = { message.filled("element" to element) }
)

@JvmName("notContainsArray")
public fun <E> ValidationNode<Array<E>>.notContains(
    element: E?,
    message: suspend Array<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "notContains",
        predicate = { element !in it }
    )
}

public fun <E> ValidationNode<List<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

public fun <E> ValidationNode<List<E>>.containsAll(
    elements: Iterable<E>?,
    message: suspend List<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAll",
        predicate = { elements.all { element -> element in it } }
    )
}

@JvmName("containsAllSet")
public fun <E> ValidationNode<Set<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

@JvmName("containsAllSet")
public fun <E> ValidationNode<Set<E>>.containsAll(
    elements: Iterable<E>?,
    message: suspend Set<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAll",
        predicate = { elements.all { element -> element in it } }
    )
}

@JvmName("containsAllArray")
public fun <E> ValidationNode<Array<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

@JvmName("containsAllArray")
public fun <E> ValidationNode<Array<E>>.containsAll(
    elements: Iterable<E>?,
    message: suspend Array<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAll",
        predicate = { elements.all { element -> element in it } }
    )
}

public fun <E> ValidationNode<List<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

public fun <E> ValidationNode<List<E>>.containsAny(
    elements: Iterable<E>?,
    message: suspend List<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAny",
        predicate = { elements.any { element -> element in it } }
    )
}

@JvmName("containsAnySet")
public fun <E> ValidationNode<Set<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

@JvmName("containsAnySet")
public fun <E> ValidationNode<Set<E>>.containsAny(
    elements: Iterable<E>?,
    message: suspend Set<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAny",
        predicate = { elements.any { element -> element in it } }
    )
}

@JvmName("containsAnyArray")
public fun <E> ValidationNode<Array<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

@JvmName("containsAnyArray")
public fun <E> ValidationNode<Array<E>>.containsAny(
    elements: Iterable<E>?,
    message: suspend Array<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAny",
        predicate = { elements.any { element -> element in it } }
    )
}

public fun <E> ValidationNode<List<E>>.containsNone(
    elements: Iterable<E>?,
    message: String = messages.containsNone
): Unit = containsNone(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

public fun <E> ValidationNode<List<E>>.containsNone(
    elements: Iterable<E>?,
    message: suspend List<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsNone",
        predicate = { elements.none { element -> element in it } }
    )
}

@JvmName("containsNoneSet")
public fun <E> ValidationNode<Set<E>>.containsNone(
    elements: Iterable<E>?,
    message: String = messages.containsNone
): Unit = containsNone(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

@JvmName("containsNoneSet")
public fun <E> ValidationNode<Set<E>>.containsNone(
    elements: Iterable<E>?,
    message: suspend Set<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsNone",
        predicate = { elements.none { element -> element in it } }
    )
}

@JvmName("containsNoneArray")
public fun <E> ValidationNode<Array<E>>.containsNone(
    elements: Iterable<E>?,
    message: String = messages.containsNone
): Unit = containsNone(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

@JvmName("containsNoneArray")
public fun <E> ValidationNode<Array<E>>.containsNone(
    elements: Iterable<E>?,
    message: suspend Array<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsNone",
        predicate = { elements.none { element -> element in it } }
    )
}
