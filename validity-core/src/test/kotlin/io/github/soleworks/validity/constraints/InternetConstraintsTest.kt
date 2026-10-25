package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InternetConstraintsTest {
    @Nested
    @DisplayName("When email is called")
    inner class Email {
        @ParameterizedTest
        @ValueSource(strings = ["ana@mail.com", "ana.maria+tag@sub.example.com.br"])
        fun `given a valid email should accept it`(
            email: String
        ) {
            val node = ValidationNode(path = "email", value = email).apply { email() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["ana", "ana@", "ana@mail", "ana@@mail.com", "ana maria@mail.com", "@mail.com"])
        fun `given an invalid email should report it`(
            email: String
        ) {
            val node = ValidationNode(path = "email", value = email).apply { email() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "must be a valid email", code = "email")
            )
        }
    }

    @Nested
    @DisplayName("When url is called")
    inner class Url {
        @ParameterizedTest
        @ValueSource(strings = ["https://example.com", "http://example.com/path?query=1#top"])
        fun `given a valid web URL should accept it`(
            website: String
        ) {
            val node = ValidationNode(path = "website", value = website).apply { url() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an allowed custom scheme should accept it`() {
            val node = ValidationNode(path = "website", value = "ftp://files.example.com").apply { url(setOf("ftp")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an allowed scheme in uppercase should accept it`() {
            val node = ValidationNode(path = "website", value = "https://example.com").apply { url(setOf("HTTPS")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["example.com", "ftp://example.com", "https://", "http://exa mple.com"])
        fun `given an invalid web URL should report it`(
            website: String
        ) {
            val node = ValidationNode(path = "website", value = website).apply { url() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "website", message = "must be a valid URL", code = "url")
            )
        }

        @Test
        fun `given a custom message with the schemes placeholder should replace it`() {
            val node = ValidationNode(
                path = "website",
                value = "ftp://example.com"
            ).apply { url(message = "must use {schemes}") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "website", message = "must use http, https", code = "url")
            )
        }

        @Test
        fun `given a message function should build it from the value and the schemes`() {
            val node = ValidationNode(path = "website", value = "ftp://example.com").apply {
                url { schemes -> "$this must use $schemes" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "website", message = "ftp://example.com must use [http, https]", code = "url")
            )
        }
    }

    @Nested
    @DisplayName("When hostname is called")
    inner class Hostname {
        @ParameterizedTest
        @ValueSource(strings = ["example.com", "servidor-01.empresa.com.br", "localhost"])
        fun `given a valid hostname should accept it`(
            host: String
        ) {
            val node = ValidationNode(path = "host", value = host).apply { hostname() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "-example.com",
                "exa_mple.com",
                "example..com",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.com"
            ]
        )
        fun `given an invalid hostname should report it`(
            host: String
        ) {
            val node = ValidationNode(path = "host", value = host).apply { hostname() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "host", message = "must be a valid hostname", code = "hostname")
            )
        }
    }

    @Nested
    @DisplayName("When domain is called")
    inner class Domain {
        @ParameterizedTest
        @ValueSource(strings = ["example.com", "loja.empresa.com.br", "EXAMPLE.ORG", "xn--80ak6aa92e.xn--p1ai"])
        fun `given a domain name with a top-level domain should accept it`(
            domain: String
        ) {
            val node = ValidationNode(path = "domain", value = domain).apply { domain() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "localhost",
                "example.c",
                "example.123",
                "-example.com",
                "example..com",
                "example.com."
            ]
        )
        fun `given a host that is not a domain name should report it`(
            domain: String
        ) {
            val node = ValidationNode(path = "domain", value = domain).apply { domain() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "domain", message = "must be a valid domain name", code = "domain")
            )
        }
    }

    @Nested
    @DisplayName("When port is called")
    inner class Port {
        @ParameterizedTest
        @ValueSource(ints = [0, 80, 8080, 65_535])
        fun `given a port number in range should accept it`(
            port: Int
        ) {
            val node = ValidationNode(path = "port", value = port).apply { port() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "443", "65535"])
        fun `given a port text in range should accept it`(
            port: String
        ) {
            val node = ValidationNode(path = "port", value = port).apply { port() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [-1, 65_536])
        fun `given a port number out of range should report it`(
            port: Int
        ) {
            val node = ValidationNode(path = "port", value = port).apply { port() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "port", message = "must be a valid port number", code = "port")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["65536", "99999", "080", "-1", "", "http"])
        fun `given a port text out of range or not a number should report it`(
            port: String
        ) {
            val node = ValidationNode(path = "port", value = port).apply { port() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "port", message = "must be a valid port number", code = "port")
            )
        }
    }

    @Nested
    @DisplayName("When ipv4 is called")
    inner class Ipv4 {
        @ParameterizedTest
        @ValueSource(strings = ["192.168.0.1", "0.0.0.0", "255.255.255.255"])
        fun `given a valid IPv4 address should accept it`(
            ip: String
        ) {
            val node = ValidationNode(path = "ip", value = ip).apply { ipv4() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["256.1.1.1", "192.168.0", "192.168.01.1", "1.2.3.4.5"])
        fun `given an invalid IPv4 address should report it`(
            ip: String
        ) {
            val node = ValidationNode(path = "ip", value = ip).apply { ipv4() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ip", message = "must be a valid IPv4 address", code = "ipv4")
            )
        }
    }

    @Nested
    @DisplayName("When ipv6 is called")
    inner class Ipv6 {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "2001:db8::1",
                "::1",
                "::",
                "2001:0db8:85a3:0000:0000:8a2e:0370:7334",
                "::ffff:192.168.0.1"
            ]
        )
        fun `given a valid IPv6 address should accept it`(
            ip: String
        ) {
            val node = ValidationNode(path = "ip", value = ip).apply { ipv6() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "2001:db8::1::2",
                "2001:db8:::1",
                "12345::",
                "1:2:3:4:5:6:7:8:9",
                "1.2.3.4::",
                ":1:2:3:4:5:6:7",
                "1::2:3:4:5:6:7:8"
            ]
        )
        fun `given an invalid IPv6 address should report it`(
            ip: String
        ) {
            val node = ValidationNode(path = "ip", value = ip).apply { ipv6() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ip", message = "must be a valid IPv6 address", code = "ipv6")
            )
        }
    }

    @Nested
    @DisplayName("When ip is called")
    inner class Ip {
        @ParameterizedTest
        @ValueSource(strings = ["192.168.0.1", "2001:db8::1"])
        fun `given a valid IP address should accept it`(
            ip: String
        ) {
            val node = ValidationNode(path = "ip", value = ip).apply { ip() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["999.1.1.1", "localhost"])
        fun `given an invalid IP address should report it`(
            ip: String
        ) {
            val node = ValidationNode(path = "ip", value = ip).apply { ip() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ip", message = "must be a valid IP address", code = "ip")
            )
        }
    }

    @Nested
    @DisplayName("When cidr is called")
    inner class Cidr {
        @ParameterizedTest
        @ValueSource(strings = ["10.0.0.0/8", "192.168.0.0/24", "2001:db8::/32"])
        fun `given a valid CIDR block should accept it`(
            network: String
        ) {
            val node = ValidationNode(path = "network", value = network).apply { cidr() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "10.0.0.0/33",
                "2001:db8::/129",
                "10.0.0.0",
                "10.0.0.0/",
                "10.0.0.0/+8",
                "10.0.0.0/08",
                "10.0.0.0/٨"
            ]
        )
        fun `given an invalid CIDR block should report it`(
            network: String
        ) {
            val node = ValidationNode(path = "network", value = network).apply { cidr() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "network", message = "must be a valid CIDR block", code = "cidr")
            )
        }
    }

    @Nested
    @DisplayName("When macAddress is called")
    inner class MacAddress {
        @ParameterizedTest
        @ValueSource(strings = ["00:1A:2B:3C:4D:5E", "00-1a-2b-3c-4d-5e"])
        fun `given a valid MAC address should accept it`(
            macAddress: String
        ) {
            val node = ValidationNode(path = "macAddress", value = macAddress).apply { macAddress() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["00:1A-2B:3C:4D:5E", "001A2B3C4D5E", "00:1A:2B:3C:4D"])
        fun `given an invalid MAC address should report it`(
            macAddress: String
        ) {
            val node = ValidationNode(path = "macAddress", value = macAddress).apply { macAddress() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "macAddress", message = "must be a valid MAC address", code = "macAddress")
            )
        }
    }

    @Nested
    @DisplayName("When slug is called")
    inner class Slug {
        @ParameterizedTest
        @ValueSource(strings = ["my-post", "post-2026"])
        fun `given a valid slug should accept it`(
            slug: String
        ) {
            val node = ValidationNode(path = "slug", value = slug).apply { slug() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["My-Post", "my--post", "-my-post", "my_post"])
        fun `given an invalid slug should report it`(
            slug: String
        ) {
            val node = ValidationNode(path = "slug", value = slug).apply { slug() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "slug", message = "must be a valid slug", code = "slug")
            )
        }
    }

    @Nested
    @DisplayName("When jwt is called")
    inner class Jwt {
        @ParameterizedTest
        @ValueSource(strings = ["eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.sig-nat_ure"])
        fun `given a valid JWT should accept it`(
            token: String
        ) {
            val node = ValidationNode(path = "token", value = token).apply { jwt() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["abc.def", "abc.def.ghi.jkl", "abc.d=ef.ghi"])
        fun `given an invalid JWT should report it`(
            token: String
        ) {
            val node = ValidationNode(path = "token", value = token).apply { jwt() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "token", message = "must be a valid JWT", code = "jwt")
            )
        }
    }

    @Nested
    @DisplayName("When dataUri is called")
    inner class DataUri {
        @ParameterizedTest
        @ValueSource(strings = ["data:text/plain;base64,SGVsbG8=", "data:,Hello", "data:;charset=US-ASCII,Hello"])
        fun `given a valid data URI should accept it`(
            avatar: String
        ) {
            val node = ValidationNode(path = "avatar", value = avatar).apply { dataUri() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["data:text/plain;base64", "text/plain,Hello", "data:text plain,x"])
        fun `given an invalid data URI should report it`(
            avatar: String
        ) {
            val node = ValidationNode(path = "avatar", value = avatar).apply { dataUri() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "avatar", message = "must be a valid data URI", code = "dataUri")
            )
        }
    }

    @Nested
    @DisplayName("When mimeType is called")
    inner class MimeType {
        @ParameterizedTest
        @ValueSource(strings = ["image/png", "application/vnd.api+json", "Image/PNG", "haptics/ivs"])
        fun `given a valid MIME type should accept it`(
            contentType: String
        ) {
            val node = ValidationNode(path = "contentType", value = contentType).apply { mimeType() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["image", "png", "unknown/png", "image/ png"])
        fun `given an invalid MIME type should report it`(
            contentType: String
        ) {
            val node = ValidationNode(path = "contentType", value = contentType).apply { mimeType() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "contentType", message = "must be a valid MIME type", code = "mimeType")
            )
        }
    }
}
