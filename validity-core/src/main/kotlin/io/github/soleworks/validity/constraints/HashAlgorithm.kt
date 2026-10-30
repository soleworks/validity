package io.github.soleworks.validity.constraints

/**
 * A hash algorithm, for [hash]: each one sets the number of hexadecimal digits of its digest.
 */
public enum class HashAlgorithm(
    internal val length: Int
) {
    /** MD4, 32 hexadecimal digits. */
    MD4(32),

    /** MD5, 32 hexadecimal digits. */
    MD5(32),

    /** SHA-1, 40 hexadecimal digits. */
    SHA1(40),

    /** SHA-256, 64 hexadecimal digits. */
    SHA256(64),

    /** SHA-384, 96 hexadecimal digits. */
    SHA384(96),

    /** SHA-512, 128 hexadecimal digits. */
    SHA512(128),

    /** RIPEMD-128, 32 hexadecimal digits. */
    RIPEMD128(32),

    /** RIPEMD-160, 40 hexadecimal digits. */
    RIPEMD160(40),

    /** Tiger/128, 32 hexadecimal digits. */
    TIGER128(32),

    /** Tiger/160, 40 hexadecimal digits. */
    TIGER160(40),

    /** Tiger/192, 48 hexadecimal digits. */
    TIGER192(48),

    /** CRC-32, 8 hexadecimal digits. */
    CRC32(8),

    /** CRC-32B, 8 hexadecimal digits. */
    CRC32B(8)
}
