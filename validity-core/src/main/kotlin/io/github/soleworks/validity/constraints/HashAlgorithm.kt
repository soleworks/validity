package io.github.soleworks.validity.constraints

public enum class HashAlgorithm(
    internal val length: Int
) {
    MD4(32),
    MD5(32),
    SHA1(40),
    SHA256(64),
    SHA384(96),
    SHA512(128),
    RIPEMD128(32),
    RIPEMD160(40),
    TIGER128(32),
    TIGER160(40),
    TIGER192(48),
    CRC32(8),
    CRC32B(8)
}
