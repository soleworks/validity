package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.FormatMessages
import io.github.soleworks.validity.ValidationNode
import java.net.URI

private const val IPV4_PREFIX_LENGTH = 32
private const val IPV6_PREFIX_LENGTH = 128
private const val IPV6_GROUPS = 8

private val WEB_SCHEMES = setOf("http", "https")

private const val DOMAIN_LABEL = "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"

private val EMAIL_FORMAT = Regex("[A-Za-z0-9.!#\$%&'*+/=?^_`{|}~-]+@$DOMAIN_LABEL(?:\\.$DOMAIN_LABEL)+")
private val HOSTNAME_FORMAT = Regex("(?=.{1,253}$)$DOMAIN_LABEL(?:\\.$DOMAIN_LABEL)*")
private const val IPV4_OCTET = "(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"

private val IPV4_FORMAT = Regex("$IPV4_OCTET(?:\\.$IPV4_OCTET){3}")
private val IPV6_GROUP_FORMAT = Regex("[0-9A-Fa-f]{1,4}")
private val MAC_ADDRESS_FORMAT = Regex("[0-9A-Fa-f]{2}([:-])(?:[0-9A-Fa-f]{2}\\1){4}[0-9A-Fa-f]{2}")
private val SLUG_FORMAT = Regex("[a-z0-9]+(?:-[a-z0-9]+)*")
private val JWT_FORMAT = Regex("[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]*")
private val DATA_URI_FORMAT = Regex(
    "data:(?:[a-z]+/[a-z0-9.+-]+(?:;[a-z0-9-]+=[a-z0-9.-]+)*)?(?:;base64)?,\\S*",
    RegexOption.IGNORE_CASE
)
private val MIME_TYPE_FORMAT = Regex(
    "(?:application|audio|font|image|message|model|multipart|text|video)/[A-Za-z0-9!#\$&^_.+-]+"
)

public fun ValidationNode<String>.email(
    message: String = FormatMessages.EMAIL
): Unit = constraint(
    message = message,
    predicate = { EMAIL_FORMAT.matches(it) }
)

public fun ValidationNode<String>.url(
    schemes: Set<String> = WEB_SCHEMES,
    message: String = FormatMessages.URL
): Unit = url(
    schemes = schemes,
    message = { message.replace("{schemes}", schemes.joinToString()) }
)

public fun ValidationNode<String>.url(
    schemes: Set<String> = WEB_SCHEMES,
    message: String.(Set<String>) -> String
): Unit = constraint(
    message = { it.message(schemes) },
    predicate = { it.isUrl(schemes) }
)

public fun ValidationNode<String>.hostname(
    message: String = FormatMessages.HOSTNAME
): Unit = constraint(
    message = message,
    predicate = { HOSTNAME_FORMAT.matches(it) }
)

public fun ValidationNode<String>.ipv4(
    message: String = FormatMessages.IPV4
): Unit = constraint(
    message = message,
    predicate = { IPV4_FORMAT.matches(it) }
)

public fun ValidationNode<String>.ipv6(
    message: String = FormatMessages.IPV6
): Unit = constraint(
    message = message,
    predicate = { it.isIpv6() }
)

public fun ValidationNode<String>.ip(
    message: String = FormatMessages.IP
): Unit = constraint(
    message = message,
    predicate = { IPV4_FORMAT.matches(it) || it.isIpv6() }
)

public fun ValidationNode<String>.cidr(
    message: String = FormatMessages.CIDR
): Unit = constraint(
    message = message,
    predicate = { it.isCidr() }
)

public fun ValidationNode<String>.macAddress(
    message: String = FormatMessages.MAC_ADDRESS
): Unit = constraint(
    message = message,
    predicate = { MAC_ADDRESS_FORMAT.matches(it) }
)

public fun ValidationNode<String>.slug(
    message: String = FormatMessages.SLUG
): Unit = constraint(
    message = message,
    predicate = { SLUG_FORMAT.matches(it) }
)

public fun ValidationNode<String>.jwt(
    message: String = FormatMessages.JWT
): Unit = constraint(
    message = message,
    predicate = { JWT_FORMAT.matches(it) }
)

public fun ValidationNode<String>.dataUri(
    message: String = FormatMessages.DATA_URI
): Unit = constraint(
    message = message,
    predicate = { DATA_URI_FORMAT.matches(it) }
)

public fun ValidationNode<String>.mimeType(
    message: String = FormatMessages.MIME_TYPE
): Unit = constraint(
    message = message,
    predicate = { MIME_TYPE_FORMAT.matches(it) }
)

private fun String.isUrl(schemes: Set<String>): Boolean {
    val uri = runCatching { URI(this) }.getOrNull()

    return uri != null && uri.scheme?.lowercase() in schemes && !uri.host.isNullOrBlank()
}

private fun String.isIpv6(): Boolean {
    val halves = split("::")
    val groups = halves.map { half -> if (half.isEmpty()) emptyList() else half.split(':') }
    val embeddedIpv4 = groups.last().lastOrNull()?.takeIf { '.' in it }
    val hexGroups = groups.flatten().let { all -> if (embeddedIpv4 == null) all else all.dropLast(1) }
    val size = hexGroups.size + if (embeddedIpv4 == null) 0 else 2

    val validIpv4 = embeddedIpv4 == null || IPV4_FORMAT.matches(embeddedIpv4)
    val validGroups = hexGroups.all(IPV6_GROUP_FORMAT::matches) && validIpv4
    val validSize = if (halves.size == 2) size < IPV6_GROUPS else size == IPV6_GROUPS

    return halves.size <= 2 && validGroups && validSize
}

private fun String.isCidr(): Boolean {
    val address = substringBefore('/')
    val prefix = substringAfter('/', "").takeIf { it.all(Char::isDigit) }?.toIntOrNull()

    return when {
        prefix == null -> false
        IPV4_FORMAT.matches(address) -> prefix <= IPV4_PREFIX_LENGTH
        address.isIpv6() -> prefix <= IPV6_PREFIX_LENGTH
        else -> false
    }
}
