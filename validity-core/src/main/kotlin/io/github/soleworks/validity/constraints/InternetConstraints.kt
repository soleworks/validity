package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.net.URI

private const val IPV4_PREFIX_LENGTH = 32
private const val IPV6_PREFIX_LENGTH = 128
private const val IPV6_GROUPS = 8
private const val MAX_PORT = 65_535

private const val DOMAIN_LABEL = "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"
private const val IPV4_OCTET = """(?:25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)"""

private val WEB_SCHEMES = setOf("http", "https")
private val EMAIL_FORMAT = Regex("""[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@$DOMAIN_LABEL(?:\.$DOMAIN_LABEL)+""")
private val HOSTNAME_FORMAT = Regex("""(?=.{1,253}$)$DOMAIN_LABEL(?:\.$DOMAIN_LABEL)*""")
private val DOMAIN_FORMAT = Regex("""(?=.{1,253}$)(?:$DOMAIN_LABEL\.)+(?:[A-Za-z]{2,63}|xn--[A-Za-z0-9-]{1,59})""")
private val PORT_FORMAT = Regex("""0|[1-9]\d{0,4}""")
private val IPV4_FORMAT = Regex("""$IPV4_OCTET(?:\.$IPV4_OCTET){3}""")
private val IPV6_GROUP_FORMAT = Regex("[0-9A-Fa-f]{1,4}")
private val MAC_ADDRESS_FORMAT = Regex("""[0-9A-Fa-f]{2}([:-])(?:[0-9A-Fa-f]{2}\1){4}[0-9A-Fa-f]{2}""")
private val SLUG_FORMAT = Regex("[a-z0-9]+(?:-[a-z0-9]+)*")
private val JWT_FORMAT = Regex("""[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\.[A-Za-z0-9_-]*""")
private val CIDR_PREFIX_FORMAT = Regex("""0|[1-9]\d{0,2}""")
private val DATA_URI_FORMAT = Regex(
    """data:(?:[a-z]+/[a-z0-9.+-]+)?(?:;[a-z0-9-]+=[a-z0-9.-]+)*(?:;base64)?,\S*""",
    RegexOption.IGNORE_CASE
)
private val MIME_TYPE_FORMAT = Regex(
    """(?:application|audio|font|haptics|image|message|model|multipart|text|video)/[A-Za-z0-9!#$&^_.+-]+""",
    RegexOption.IGNORE_CASE
)

/**
 * Checks that the string is an email address: a local part, `@` and a domain with at least one dot.
 *
 * Quoted local parts and IP address domains are not accepted.
 *
 * Fails with code `email` and, by default, the message `must be a valid email`
 * ([Messages.email][io.github.soleworks.validity.Messages.email]).
 *
 * ```kotlin
 * ::email required { email() }
 * ```
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.email(
    message: String = messages.email
): Unit = constraint(
    message = message,
    code = "email",
    predicate = { EMAIL_FORMAT.matches(it) }
)

/**
 * Checks that the string is an absolute URL with one of [schemes] and a host.
 *
 * Fails with code `url` and, by default, the message `must be a valid URL`
 * ([Messages.url][io.github.soleworks.validity.Messages.url]).
 *
 * ```kotlin
 * ::callback required { url(schemes = setOf("https")) }
 * ```
 *
 * @param schemes the schemes accepted, in any case. `http` and `https` by default.
 * @param message the message of the violation. `{schemes}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.url(
    schemes: Set<String> = WEB_SCHEMES,
    message: String = messages.url
): Unit = url(
    schemes = schemes,
    message = { message.filled("schemes" to schemes) }
)

/**
 * Checks that the string is an absolute URL with one of [schemes] and a host.
 *
 * Fails with code `url` and the message that [message] builds.
 *
 * @param schemes the schemes accepted, in any case. `http` and `https` by default.
 * @param message builds the message from the value (the receiver) and [schemes].
 */
public fun ValidationNode<String>.url(
    schemes: Set<String> = WEB_SCHEMES,
    message: String.(Set<String>) -> String
): Unit = constraint(
    message = { it.message(schemes) },
    code = "url",
    predicate = { it.isUrl(schemes) }
)

/**
 * Checks that the string is a hostname: labels of letters, digits and dashes, separated by dots, at most 253
 * characters.
 *
 * A single label, such as `localhost`, passes.
 *
 * Fails with code `hostname` and, by default, the message `must be a valid hostname`
 * ([Messages.hostname][io.github.soleworks.validity.Messages.hostname]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.hostname(
    message: String = messages.hostname
): Unit = constraint(
    message = message,
    code = "hostname",
    predicate = { HOSTNAME_FORMAT.matches(it) }
)

/**
 * Checks that the string is a domain name with at least two labels and a top-level domain of letters or punycode, such
 * as `example.com`.
 *
 * Fails with code `domain` and, by default, the message `must be a valid domain name`
 * ([Messages.domain][io.github.soleworks.validity.Messages.domain]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.domain(
    message: String = messages.domain
): Unit = constraint(
    message = message,
    code = "domain",
    predicate = { DOMAIN_FORMAT.matches(it) }
)

/**
 * Checks that the number is a port number, from 0 to 65535.
 *
 * Fails with code `port` and, by default, the message `must be a valid port number`
 * ([Messages.port][io.github.soleworks.validity.Messages.port]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.port(
    message: String = messages.port
): Unit = constraint(
    message = message,
    code = "port",
    predicate = { it in 0..MAX_PORT }
)

/**
 * Checks that the string is a port number, from `0` to `65535`, with no sign and no leading zeros.
 *
 * Fails with code `port` and, by default, the message `must be a valid port number`
 * ([Messages.port][io.github.soleworks.validity.Messages.port]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("portText")
public fun ValidationNode<String>.port(
    message: String = messages.port
): Unit = constraint(
    message = message,
    code = "port",
    predicate = { PORT_FORMAT.matches(it) && it.toInt() <= MAX_PORT }
)

/**
 * Checks that the string is an IPv4 address in dotted decimal, such as `192.168.0.1`, with no leading zeros.
 *
 * Fails with code `ipv4` and, by default, the message `must be a valid IPv4 address`
 * ([Messages.ipv4][io.github.soleworks.validity.Messages.ipv4]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ipv4(
    message: String = messages.ipv4
): Unit = constraint(
    message = message,
    code = "ipv4",
    predicate = { IPV4_FORMAT.matches(it) }
)

/**
 * Checks that the string is an IPv6 address, such as `2001:db8::1`, with `::` compression and an embedded IPv4 address
 * accepted.
 *
 * A zone, such as `%eth0`, is not accepted.
 *
 * Fails with code `ipv6` and, by default, the message `must be a valid IPv6 address`
 * ([Messages.ipv6][io.github.soleworks.validity.Messages.ipv6]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ipv6(
    message: String = messages.ipv6
): Unit = constraint(
    message = message,
    code = "ipv6",
    predicate = { it.isIpv6() }
)

/**
 * Checks that the string is an IPv4 or an IPv6 address.
 *
 * See [ipv4] and [ipv6] for what each accepts.
 *
 * Fails with code `ip` and, by default, the message `must be a valid IP address`
 * ([Messages.ip][io.github.soleworks.validity.Messages.ip]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ip(
    message: String = messages.ip
): Unit = constraint(
    message = message,
    code = "ip",
    predicate = { IPV4_FORMAT.matches(it) || it.isIpv6() }
)

/**
 * Checks that the string is a CIDR block: an IPv4 or IPv6 address, `/` and a prefix length that fits it, such as
 * `10.0.0.0/8`.
 *
 * Fails with code `cidr` and, by default, the message `must be a valid CIDR block`
 * ([Messages.cidr][io.github.soleworks.validity.Messages.cidr]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cidr(
    message: String = messages.cidr
): Unit = constraint(
    message = message,
    code = "cidr",
    predicate = { it.isCidr() }
)

/**
 * Checks that the string is a MAC address: six pairs of hexadecimal digits, separated all by `:` or all by `-`.
 *
 * Fails with code `macAddress` and, by default, the message `must be a valid MAC address`
 * ([Messages.macAddress][io.github.soleworks.validity.Messages.macAddress]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.macAddress(
    message: String = messages.macAddress
): Unit = constraint(
    message = message,
    code = "macAddress",
    predicate = { MAC_ADDRESS_FORMAT.matches(it) }
)

/**
 * Checks that the string is a slug: lowercase letters and digits in groups joined by single dashes, such as
 * `hello-world-2`.
 *
 * Fails with code `slug` and, by default, the message `must be a valid slug`
 * ([Messages.slug][io.github.soleworks.validity.Messages.slug]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.slug(
    message: String = messages.slug
): Unit = constraint(
    message = message,
    code = "slug",
    predicate = { SLUG_FORMAT.matches(it) }
)

/**
 * Checks that the string has the format of a JWT: three Base64URL parts separated by dots, the last of which can be
 * empty.
 *
 * Only the format is checked. The signature is not verified.
 *
 * Fails with code `jwt` and, by default, the message `must be a valid JWT`
 * ([Messages.jwt][io.github.soleworks.validity.Messages.jwt]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.jwt(
    message: String = messages.jwt
): Unit = constraint(
    message = message,
    code = "jwt",
    predicate = { JWT_FORMAT.matches(it) }
)

/**
 * Checks that the string is a `data:` URI, such as `data:image/png;base64,iVBORw0KGgo=`.
 *
 * Fails with code `dataUri` and, by default, the message `must be a valid data URI`
 * ([Messages.dataUri][io.github.soleworks.validity.Messages.dataUri]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.dataUri(
    message: String = messages.dataUri
): Unit = constraint(
    message = message,
    code = "dataUri",
    predicate = { DATA_URI_FORMAT.matches(it) }
)

/**
 * Checks that the string is a MIME type of a registered top-level type, such as `application/json` or `image/svg+xml`.
 *
 * Parameters, such as `; charset=utf-8`, are not accepted.
 *
 * Fails with code `mimeType` and, by default, the message `must be a valid MIME type`
 * ([Messages.mimeType][io.github.soleworks.validity.Messages.mimeType]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.mimeType(
    message: String = messages.mimeType
): Unit = constraint(
    message = message,
    code = "mimeType",
    predicate = { MIME_TYPE_FORMAT.matches(it) }
)

private fun String.isUrl(
    schemes: Set<String>
): Boolean {
    val uri = runCatching { URI(this) }.getOrNull()

    return uri != null && schemes.any { it.equals(uri.scheme, ignoreCase = true) } && !uri.host.isNullOrBlank()
}

private fun String.isIpv6(): Boolean {
    val halves = split("::")
    val groups = halves.map { half -> if (half.isEmpty()) emptyList() else half.split(':') }
    val embeddedIpv4 = groups.last().lastOrNull()?.takeIf { '.' in it }
    val hexGroups = groups.flatten().let { all -> if (embeddedIpv4 == null) all else all.dropLast(1) }
    val size = hexGroups.size + if (embeddedIpv4 == null) 0 else 2

    val isValidIpv4 = embeddedIpv4 == null || IPV4_FORMAT.matches(embeddedIpv4)
    val hasValidGroups = hexGroups.all(IPV6_GROUP_FORMAT::matches) && isValidIpv4
    val hasValidSize = if (halves.size == 2) size < IPV6_GROUPS else size == IPV6_GROUPS

    return halves.size <= 2 && hasValidGroups && hasValidSize
}

private fun String.isCidr(): Boolean {
    val address = substringBefore('/')
    val prefix = substringAfter('/', "").takeIf(CIDR_PREFIX_FORMAT::matches)?.toInt()

    return when {
        prefix == null -> false
        IPV4_FORMAT.matches(address) -> prefix <= IPV4_PREFIX_LENGTH
        address.isIpv6() -> prefix <= IPV6_PREFIX_LENGTH
        else -> false
    }
}
