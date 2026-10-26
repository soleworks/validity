package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

/**
 * Checks that the collection has at least [min] elements.
 *
 * Fails with code `minSize` and, by default, the message `must have at least {min} items`
 * ([Messages.minSize][io.github.soleworks.validity.Messages.minSize]).
 *
 * @param min the fewest elements accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the collection has at least [min] elements.
 *
 * Fails with code `minSize` and the message that [message] builds.
 *
 * @param min the fewest elements accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<out Collection<*>>.minSize(
    min: Int,
    message: Collection<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

/**
 * Checks that the array has at least [min] elements.
 *
 * Fails with code `minSize` and, by default, the message `must have at least {min} items`
 * ([Messages.minSize][io.github.soleworks.validity.Messages.minSize]).
 *
 * @param min the fewest elements accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
@JvmName("minSizeArray")
public fun ValidationNode<out Array<*>>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the array has at least [min] elements.
 *
 * Fails with code `minSize` and the message that [message] builds.
 *
 * @param min the fewest elements accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
@JvmName("minSizeArray")
public fun ValidationNode<out Array<*>>.minSize(
    min: Int,
    message: Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

/**
 * Checks that the byte array has at least [min] elements.
 *
 * Fails with code `minSize` and, by default, the message `must have at least {min} items`
 * ([Messages.minSize][io.github.soleworks.validity.Messages.minSize]).
 *
 * @param min the fewest elements accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
@JvmName("minSizeByteArray")
public fun ValidationNode<ByteArray>.minSize(
    min: Int,
    message: String = messages.minSize
): Unit = minSize(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the byte array has at least [min] elements.
 *
 * Fails with code `minSize` and the message that [message] builds.
 *
 * @param min the fewest elements accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
@JvmName("minSizeByteArray")
public fun ValidationNode<ByteArray>.minSize(
    min: Int,
    message: ByteArray.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minSize",
    predicate = { it.size >= min }
)

/**
 * Checks that the collection has at most [max] elements.
 *
 * Fails with code `maxSize` and, by default, the message `must have at most {max} items`
 * ([Messages.maxSize][io.github.soleworks.validity.Messages.maxSize]).
 *
 * @param max the most elements accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.maxSize(
    max: Int,
    message: String = messages.maxSize
): Unit = maxSize(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the collection has at most [max] elements.
 *
 * Fails with code `maxSize` and the message that [message] builds.
 *
 * @param max the most elements accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<out Collection<*>>.maxSize(
    max: Int,
    message: Collection<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

/**
 * Checks that the array has at most [max] elements.
 *
 * Fails with code `maxSize` and, by default, the message `must have at most {max} items`
 * ([Messages.maxSize][io.github.soleworks.validity.Messages.maxSize]).
 *
 * @param max the most elements accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxSizeArray")
public fun ValidationNode<out Array<*>>.maxSize(
    max: Int,
    message: String = messages.maxSize
): Unit = maxSize(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the array has at most [max] elements.
 *
 * Fails with code `maxSize` and the message that [message] builds.
 *
 * @param max the most elements accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxSizeArray")
public fun ValidationNode<out Array<*>>.maxSize(
    max: Int,
    message: Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

/**
 * Checks that the byte array has at most [max] elements.
 *
 * Fails with code `maxSize` and, by default, the message `must have at most {max} items`
 * ([Messages.maxSize][io.github.soleworks.validity.Messages.maxSize]).
 *
 * @param max the most elements accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxSizeByteArray")
public fun ValidationNode<ByteArray>.maxSize(
    max: Int,
    message: String = messages.maxSize
): Unit = maxSize(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the byte array has at most [max] elements.
 *
 * Fails with code `maxSize` and the message that [message] builds.
 *
 * @param max the most elements accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxSizeByteArray")
public fun ValidationNode<ByteArray>.maxSize(
    max: Int,
    message: ByteArray.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxSize",
    predicate = { it.size <= max }
)

/**
 * Checks that the collection has exactly [size] elements.
 *
 * Fails with code `size` and, by default, the message `must have exactly {size} items`
 * ([Messages.size][io.github.soleworks.validity.Messages.size]).
 *
 * @param size the number of elements required.
 * @param message the message of the violation. `{size}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.size(
    size: Int,
    message: String = messages.size
): Unit = size(
    size = size,
    message = { message.filled("size" to size) }
)

/**
 * Checks that the collection has exactly [size] elements.
 *
 * Fails with code `size` and the message that [message] builds.
 *
 * @param size the number of elements required.
 * @param message builds the message from the value (the receiver) and [size].
 */
public fun ValidationNode<out Collection<*>>.size(
    size: Int,
    message: Collection<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

/**
 * Checks that the array has exactly [size] elements.
 *
 * Fails with code `size` and, by default, the message `must have exactly {size} items`
 * ([Messages.size][io.github.soleworks.validity.Messages.size]).
 *
 * @param size the number of elements required.
 * @param message the message of the violation. `{size}`, `{path}` and `{value}` are filled in.
 */
@JvmName("sizeArray")
public fun ValidationNode<out Array<*>>.size(
    size: Int,
    message: String = messages.size
): Unit = size(
    size = size,
    message = { message.filled("size" to size) }
)

/**
 * Checks that the array has exactly [size] elements.
 *
 * Fails with code `size` and the message that [message] builds.
 *
 * @param size the number of elements required.
 * @param message builds the message from the value (the receiver) and [size].
 */
@JvmName("sizeArray")
public fun ValidationNode<out Array<*>>.size(
    size: Int,
    message: Array<*>.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

/**
 * Checks that the byte array has exactly [size] elements.
 *
 * Fails with code `size` and, by default, the message `must have exactly {size} items`
 * ([Messages.size][io.github.soleworks.validity.Messages.size]).
 *
 * @param size the number of elements required.
 * @param message the message of the violation. `{size}`, `{path}` and `{value}` are filled in.
 */
@JvmName("sizeByteArray")
public fun ValidationNode<ByteArray>.size(
    size: Int,
    message: String = messages.size
): Unit = size(
    size = size,
    message = { message.filled("size" to size) }
)

/**
 * Checks that the byte array has exactly [size] elements.
 *
 * Fails with code `size` and the message that [message] builds.
 *
 * @param size the number of elements required.
 * @param message builds the message from the value (the receiver) and [size].
 */
@JvmName("sizeByteArray")
public fun ValidationNode<ByteArray>.size(
    size: Int,
    message: ByteArray.(Int) -> String
): Unit = constraint(
    message = { it.message(size) },
    code = "size",
    predicate = { it.size == size }
)

/**
 * Checks that the collection has between [min] and [max] elements, both included.
 *
 * Fails with code `sizeBetween` and, by default, the message `must have between {min} and {max} items`
 * ([Messages.sizeBetween][io.github.soleworks.validity.Messages.sizeBetween]).
 *
 * @param min the fewest elements accepted.
 * @param max the most elements accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: String = messages.sizeBetween
): Unit = sizeBetween(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the collection has between [min] and [max] elements, both included.
 *
 * Fails with code `sizeBetween` and the message that [message] builds.
 *
 * @param min the fewest elements accepted.
 * @param max the most elements accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<out Collection<*>>.sizeBetween(
    min: Int,
    max: Int,
    message: Collection<*>.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

/**
 * Checks that the array has between [min] and [max] elements, both included.
 *
 * Fails with code `sizeBetween` and, by default, the message `must have between {min} and {max} items`
 * ([Messages.sizeBetween][io.github.soleworks.validity.Messages.sizeBetween]).
 *
 * @param min the fewest elements accepted.
 * @param max the most elements accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
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

/**
 * Checks that the array has between [min] and [max] elements, both included.
 *
 * Fails with code `sizeBetween` and the message that [message] builds.
 *
 * @param min the fewest elements accepted.
 * @param max the most elements accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
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

/**
 * Checks that the byte array has between [min] and [max] elements, both included.
 *
 * Fails with code `sizeBetween` and, by default, the message `must have between {min} and {max} items`
 * ([Messages.sizeBetween][io.github.soleworks.validity.Messages.sizeBetween]).
 *
 * @param min the fewest elements accepted.
 * @param max the most elements accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
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

/**
 * Checks that the byte array has between [min] and [max] elements, both included.
 *
 * Fails with code `sizeBetween` and the message that [message] builds.
 *
 * @param min the fewest elements accepted.
 * @param max the most elements accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
@JvmName("sizeBetweenByteArray")
public fun ValidationNode<ByteArray>.sizeBetween(
    min: Int,
    max: Int,
    message: ByteArray.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "sizeBetween",
    predicate = { it.size in min..max }
)

/**
 * Checks that the collection has at least one element.
 *
 * Fails with code `notEmpty` and, by default, the message `must not be empty`
 * ([Messages.notEmpty][io.github.soleworks.validity.Messages.notEmpty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

/**
 * Checks that the array has at least one element.
 *
 * Fails with code `notEmpty` and, by default, the message `must not be empty`
 * ([Messages.notEmpty][io.github.soleworks.validity.Messages.notEmpty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("notEmptyArray")
public fun ValidationNode<out Array<*>>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

/**
 * Checks that the byte array has at least one element.
 *
 * Fails with code `notEmpty` and, by default, the message `must not be empty`
 * ([Messages.notEmpty][io.github.soleworks.validity.Messages.notEmpty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("notEmptyByteArray")
public fun ValidationNode<ByteArray>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

/**
 * Checks that the collection has no elements.
 *
 * Fails with code `empty` and, by default, the message `must be empty`
 * ([Messages.empty][io.github.soleworks.validity.Messages.empty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

/**
 * Checks that the array has no elements.
 *
 * Fails with code `empty` and, by default, the message `must be empty`
 * ([Messages.empty][io.github.soleworks.validity.Messages.empty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("emptyArray")
public fun ValidationNode<out Array<*>>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

/**
 * Checks that the byte array has no elements.
 *
 * Fails with code `empty` and, by default, the message `must be empty`
 * ([Messages.empty][io.github.soleworks.validity.Messages.empty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("emptyByteArray")
public fun ValidationNode<ByteArray>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

/**
 * Checks that the collection has no element twice, by [equals][Any.equals].
 *
 * Fails with code `distinct` and, by default, the message `must not contain duplicates`
 * ([Messages.distinct][io.github.soleworks.validity.Messages.distinct]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<out Collection<*>>.distinct(
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinct",
    predicate = { it.distinct().size == it.size }
)

/**
 * Checks that the array has no element twice, by [equals][Any.equals].
 *
 * Fails with code `distinct` and, by default, the message `must not contain duplicates`
 * ([Messages.distinct][io.github.soleworks.validity.Messages.distinct]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("distinctArray")
public fun ValidationNode<out Array<*>>.distinct(
    message: String = messages.distinct
): Unit = constraint(
    message = message,
    code = "distinct",
    predicate = { it.distinct().size == it.size }
)

/**
 * Checks that no two elements of the list have the same key, as [selector] picks it.
 *
 * Fails with code `distinctBy` and the message `must not contain duplicates`
 * ([Messages.distinct][io.github.soleworks.validity.Messages.distinct]).
 *
 * ```kotlin
 * ::users required { distinctBy { it.email } }
 * ```
 *
 * @param E the type of the elements.
 * @param K the type of the key.
 * @param selector picks the key that must be unique, such as an id or an email.
 */
public fun <E, K> ValidationNode<List<E>>.distinctBy(
    selector: (E) -> K
): Unit = distinctBy(
    message = messages.distinct,
    selector = selector
)

/**
 * Checks that no two elements of the list have the same key, as [selector] picks it.
 *
 * Fails with code `distinctBy` and [message].
 *
 * @param E the type of the elements.
 * @param K the type of the key.
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param selector picks the key that must be unique, such as an id or an email.
 */
public fun <E, K> ValidationNode<List<E>>.distinctBy(
    message: String,
    selector: (E) -> K
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy(selector).size == it.size }
)

/**
 * Checks that no two elements of the set have the same key, as [selector] picks it.
 *
 * Fails with code `distinctBy` and the message `must not contain duplicates`
 * ([Messages.distinct][io.github.soleworks.validity.Messages.distinct]).
 *
 * @param E the type of the elements.
 * @param K the type of the key.
 * @param selector picks the key that must be unique, such as an id or an email.
 */
@JvmName("distinctBySet")
public fun <E, K> ValidationNode<Set<E>>.distinctBy(
    selector: (E) -> K
): Unit = distinctBy(
    message = messages.distinct,
    selector = selector
)

/**
 * Checks that no two elements of the set have the same key, as [selector] picks it.
 *
 * Fails with code `distinctBy` and [message].
 *
 * @param E the type of the elements.
 * @param K the type of the key.
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param selector picks the key that must be unique, such as an id or an email.
 */
@JvmName("distinctBySet")
public fun <E, K> ValidationNode<Set<E>>.distinctBy(
    message: String,
    selector: (E) -> K
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy(selector).size == it.size }
)

/**
 * Checks that no two elements of the array have the same key, as [selector] picks it.
 *
 * Fails with code `distinctBy` and the message `must not contain duplicates`
 * ([Messages.distinct][io.github.soleworks.validity.Messages.distinct]).
 *
 * @param E the type of the elements.
 * @param K the type of the key.
 * @param selector picks the key that must be unique, such as an id or an email.
 */
@JvmName("distinctByArray")
public fun <E, K> ValidationNode<Array<E>>.distinctBy(
    selector: (E) -> K
): Unit = distinctBy(
    message = messages.distinct,
    selector = selector
)

/**
 * Checks that no two elements of the array have the same key, as [selector] picks it.
 *
 * Fails with code `distinctBy` and [message].
 *
 * @param E the type of the elements.
 * @param K the type of the key.
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 * @param selector picks the key that must be unique, such as an id or an email.
 */
@JvmName("distinctByArray")
public fun <E, K> ValidationNode<Array<E>>.distinctBy(
    message: String,
    selector: (E) -> K
): Unit = constraint(
    message = message,
    code = "distinctBy",
    predicate = { it.distinctBy(selector).size == it.size }
)

/**
 * Checks that the list contains [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `contains` and, by default, the message `must contain {element}`
 * ([Messages.containsElement][io.github.soleworks.validity.Messages.containsElement]).
 *
 * @param E the type of the elements.
 * @param element the element it must contain.
 * @param message the message of the violation. `{element}`, `{path}` and `{value}` are filled in.
 */
public fun <E> ValidationNode<List<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.filled("element" to element) }
)

/**
 * Checks that the list contains [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `contains` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param element the element it must contain.
 * @param message builds the message from the value (the receiver) and [element].
 */
public fun <E> ValidationNode<List<E>>.contains(
    element: E?,
    message: List<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "contains",
        predicate = { element in it }
    )
}

/**
 * Checks that the set contains [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `contains` and, by default, the message `must contain {element}`
 * ([Messages.containsElement][io.github.soleworks.validity.Messages.containsElement]).
 *
 * @param E the type of the elements.
 * @param element the element it must contain.
 * @param message the message of the violation. `{element}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsSet")
public fun <E> ValidationNode<Set<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.filled("element" to element) }
)

/**
 * Checks that the set contains [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `contains` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param element the element it must contain.
 * @param message builds the message from the value (the receiver) and [element].
 */
@JvmName("containsSet")
public fun <E> ValidationNode<Set<E>>.contains(
    element: E?,
    message: Set<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "contains",
        predicate = { element in it }
    )
}

/**
 * Checks that the array contains [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `contains` and, by default, the message `must contain {element}`
 * ([Messages.containsElement][io.github.soleworks.validity.Messages.containsElement]).
 *
 * @param E the type of the elements.
 * @param element the element it must contain.
 * @param message the message of the violation. `{element}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsArray")
public fun <E> ValidationNode<Array<E>>.contains(
    element: E?,
    message: String = messages.containsElement
): Unit = contains(
    element = element,
    message = { message.filled("element" to element) }
)

/**
 * Checks that the array contains [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `contains` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param element the element it must contain.
 * @param message builds the message from the value (the receiver) and [element].
 */
@JvmName("containsArray")
public fun <E> ValidationNode<Array<E>>.contains(
    element: E?,
    message: Array<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "contains",
        predicate = { element in it }
    )
}

/**
 * Checks that the list does not contain [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and, by default, the message `must not contain {element}`
 * ([Messages.notContainsElement][io.github.soleworks.validity.Messages.notContainsElement]).
 *
 * @param E the type of the elements.
 * @param element the element it must not contain.
 * @param message the message of the violation. `{element}`, `{path}` and `{value}` are filled in.
 */
public fun <E> ValidationNode<List<E>>.notContains(
    element: E?,
    message: String = messages.notContainsElement
): Unit = notContains(
    element = element,
    message = { message.filled("element" to element) }
)

/**
 * Checks that the list does not contain [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param element the element it must not contain.
 * @param message builds the message from the value (the receiver) and [element].
 */
public fun <E> ValidationNode<List<E>>.notContains(
    element: E?,
    message: List<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "notContains",
        predicate = { element !in it }
    )
}

/**
 * Checks that the set does not contain [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and, by default, the message `must not contain {element}`
 * ([Messages.notContainsElement][io.github.soleworks.validity.Messages.notContainsElement]).
 *
 * @param E the type of the elements.
 * @param element the element it must not contain.
 * @param message the message of the violation. `{element}`, `{path}` and `{value}` are filled in.
 */
@JvmName("notContainsSet")
public fun <E> ValidationNode<Set<E>>.notContains(
    element: E?,
    message: String = messages.notContainsElement
): Unit = notContains(
    element = element,
    message = { message.filled("element" to element) }
)

/**
 * Checks that the set does not contain [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param element the element it must not contain.
 * @param message builds the message from the value (the receiver) and [element].
 */
@JvmName("notContainsSet")
public fun <E> ValidationNode<Set<E>>.notContains(
    element: E?,
    message: Set<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "notContains",
        predicate = { element !in it }
    )
}

/**
 * Checks that the array does not contain [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and, by default, the message `must not contain {element}`
 * ([Messages.notContainsElement][io.github.soleworks.validity.Messages.notContainsElement]).
 *
 * @param E the type of the elements.
 * @param element the element it must not contain.
 * @param message the message of the violation. `{element}`, `{path}` and `{value}` are filled in.
 */
@JvmName("notContainsArray")
public fun <E> ValidationNode<Array<E>>.notContains(
    element: E?,
    message: String = messages.notContainsElement
): Unit = notContains(
    element = element,
    message = { message.filled("element" to element) }
)

/**
 * Checks that the array does not contain [element].
 *
 * When [element] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param element the element it must not contain.
 * @param message builds the message from the value (the receiver) and [element].
 */
@JvmName("notContainsArray")
public fun <E> ValidationNode<Array<E>>.notContains(
    element: E?,
    message: Array<E>.(E) -> String
) {
    if (element == null)
        return

    constraint(
        message = { it.message(element) },
        code = "notContains",
        predicate = { element !in it }
    )
}

/**
 * Checks that the list contains every one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAll` and, by default, the message `must contain all of {elements}`
 * ([Messages.containsAll][io.github.soleworks.validity.Messages.containsAll]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must all contain.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
public fun <E> ValidationNode<List<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the list contains every one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAll` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must all contain.
 * @param message builds the message from the value (the receiver) and [elements].
 */
public fun <E> ValidationNode<List<E>>.containsAll(
    elements: Iterable<E>?,
    message: List<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAll",
        predicate = { elements.all { element -> element in it } }
    )
}

/**
 * Checks that the set contains every one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAll` and, by default, the message `must contain all of {elements}`
 * ([Messages.containsAll][io.github.soleworks.validity.Messages.containsAll]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must all contain.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsAllSet")
public fun <E> ValidationNode<Set<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the set contains every one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAll` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must all contain.
 * @param message builds the message from the value (the receiver) and [elements].
 */
@JvmName("containsAllSet")
public fun <E> ValidationNode<Set<E>>.containsAll(
    elements: Iterable<E>?,
    message: Set<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAll",
        predicate = { elements.all { element -> element in it } }
    )
}

/**
 * Checks that the array contains every one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAll` and, by default, the message `must contain all of {elements}`
 * ([Messages.containsAll][io.github.soleworks.validity.Messages.containsAll]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must all contain.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsAllArray")
public fun <E> ValidationNode<Array<E>>.containsAll(
    elements: Iterable<E>?,
    message: String = messages.containsAll
): Unit = containsAll(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the array contains every one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAll` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must all contain.
 * @param message builds the message from the value (the receiver) and [elements].
 */
@JvmName("containsAllArray")
public fun <E> ValidationNode<Array<E>>.containsAll(
    elements: Iterable<E>?,
    message: Array<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAll",
        predicate = { elements.all { element -> element in it } }
    )
}

/**
 * Checks that the list contains at least one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAny` and, by default, the message `must contain any of {elements}`
 * ([Messages.containsAny][io.github.soleworks.validity.Messages.containsAny]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must contain one of.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
public fun <E> ValidationNode<List<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the list contains at least one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAny` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must contain one of.
 * @param message builds the message from the value (the receiver) and [elements].
 */
public fun <E> ValidationNode<List<E>>.containsAny(
    elements: Iterable<E>?,
    message: List<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAny",
        predicate = { elements.any { element -> element in it } }
    )
}

/**
 * Checks that the set contains at least one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAny` and, by default, the message `must contain any of {elements}`
 * ([Messages.containsAny][io.github.soleworks.validity.Messages.containsAny]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must contain one of.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsAnySet")
public fun <E> ValidationNode<Set<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the set contains at least one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAny` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must contain one of.
 * @param message builds the message from the value (the receiver) and [elements].
 */
@JvmName("containsAnySet")
public fun <E> ValidationNode<Set<E>>.containsAny(
    elements: Iterable<E>?,
    message: Set<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAny",
        predicate = { elements.any { element -> element in it } }
    )
}

/**
 * Checks that the array contains at least one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAny` and, by default, the message `must contain any of {elements}`
 * ([Messages.containsAny][io.github.soleworks.validity.Messages.containsAny]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must contain one of.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsAnyArray")
public fun <E> ValidationNode<Array<E>>.containsAny(
    elements: Iterable<E>?,
    message: String = messages.containsAny
): Unit = containsAny(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the array contains at least one of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsAny` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must contain one of.
 * @param message builds the message from the value (the receiver) and [elements].
 */
@JvmName("containsAnyArray")
public fun <E> ValidationNode<Array<E>>.containsAny(
    elements: Iterable<E>?,
    message: Array<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsAny",
        predicate = { elements.any { element -> element in it } }
    )
}

/**
 * Checks that the list contains none of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsNone` and, by default, the message `must not contain any of {elements}`
 * ([Messages.containsNone][io.github.soleworks.validity.Messages.containsNone]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must not contain.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
public fun <E> ValidationNode<List<E>>.containsNone(
    elements: Iterable<E>?,
    message: String = messages.containsNone
): Unit = containsNone(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the list contains none of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsNone` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must not contain.
 * @param message builds the message from the value (the receiver) and [elements].
 */
public fun <E> ValidationNode<List<E>>.containsNone(
    elements: Iterable<E>?,
    message: List<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsNone",
        predicate = { elements.none { element -> element in it } }
    )
}

/**
 * Checks that the set contains none of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsNone` and, by default, the message `must not contain any of {elements}`
 * ([Messages.containsNone][io.github.soleworks.validity.Messages.containsNone]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must not contain.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsNoneSet")
public fun <E> ValidationNode<Set<E>>.containsNone(
    elements: Iterable<E>?,
    message: String = messages.containsNone
): Unit = containsNone(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the set contains none of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsNone` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must not contain.
 * @param message builds the message from the value (the receiver) and [elements].
 */
@JvmName("containsNoneSet")
public fun <E> ValidationNode<Set<E>>.containsNone(
    elements: Iterable<E>?,
    message: Set<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsNone",
        predicate = { elements.none { element -> element in it } }
    )
}

/**
 * Checks that the array contains none of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsNone` and, by default, the message `must not contain any of {elements}`
 * ([Messages.containsNone][io.github.soleworks.validity.Messages.containsNone]).
 *
 * @param E the type of the elements.
 * @param elements the elements it must not contain.
 * @param message the message of the violation. `{elements}`, `{path}` and `{value}` are filled in.
 */
@JvmName("containsNoneArray")
public fun <E> ValidationNode<Array<E>>.containsNone(
    elements: Iterable<E>?,
    message: String = messages.containsNone
): Unit = containsNone(
    elements = elements,
    message = { message.filled("elements" to elements) }
)

/**
 * Checks that the array contains none of [elements].
 *
 * When [elements] is `null`, nothing is checked.
 *
 * Fails with code `containsNone` and the message that [message] builds.
 *
 * @param E the type of the elements.
 * @param elements the elements it must not contain.
 * @param message builds the message from the value (the receiver) and [elements].
 */
@JvmName("containsNoneArray")
public fun <E> ValidationNode<Array<E>>.containsNone(
    elements: Iterable<E>?,
    message: Array<E>.(Iterable<E>) -> String
) {
    if (elements == null)
        return

    constraint(
        message = { it.message(elements) },
        code = "containsNone",
        predicate = { elements.none { element -> element in it } }
    )
}
