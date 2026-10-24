package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.and
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.containsDigit
import io.github.soleworks.validity.constraints.containsSymbol
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.endsWith
import io.github.soleworks.validity.constraints.integer
import io.github.soleworks.validity.constraints.maxLength
import io.github.soleworks.validity.constraints.maxSize
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.not
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.constraints.startsWith
import io.github.soleworks.validity.samples.Address
import io.github.soleworks.validity.samples.Company
import io.github.soleworks.validity.samples.Mailbox
import io.github.soleworks.validity.samples.Operation
import io.github.soleworks.validity.samples.Profile
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationNodeTest {
    @Nested
    @DisplayName("When constraint is called")
    inner class Constraint {
        @Test
        fun `given a code should report it with the violation`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = "must be positive", code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "positive")
            )
        }

        @Test
        fun `given a message function and a code should report both`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = { "$it is not positive" }, code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "-1 is not positive", code = "positive")
            )
        }

        @Test
        fun `given no code should report an empty code`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint("must be positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "")
            )
        }

        @Test
        fun `given the path and value placeholders should replace them in the message`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = "{path} must be positive, got {value}", code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "amount must be positive, got -1", code = "positive")
            )
        }

        @Test
        fun `given the placeholders in a message function should replace them too`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = { "{path}: $it is not positive" }, code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "amount: -1 is not positive", code = "positive")
            )
        }

        @Test
        fun `given a path with a placeholder text should not fill it again`() {
            val node = ValidationNode(path = "prices[{value}]", value = -1).apply {
                constraint("{path} must be positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices[{value}]", message = "prices[{value}] must be positive", code = "")
            )
        }

        @Test
        fun `given a path placeholder inside a logical operator of a nested object should fill the full path`() {
            val node = ValidationNode(path = "company", value = Company(document = "123")).apply { valid() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "company.document",
                    message = "company.document is not a CPF or company.document is not a CNPJ",
                    code = "or"
                )
            )
        }
    }

    @Nested
    @DisplayName("When metadata is called")
    inner class Metadata {
        @Test
        fun `given the block inside a logical operator should keep each rule as an alternative`() {
            val node = ValidationNode(path = "document", value = "52998224725").apply {
                or {
                    metadata("operationId" to "op-1") {
                        cpf()
                        cnpj()
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given rules inside the block should attach the entries to their violations`() {
            val node = ValidationNode(path = "amount", value = 0).apply {
                positive()
                metadata("operationId" to "op-1") { min(10) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "positive"),
                Violation(
                    path = "amount",
                    message = "must be at least 10",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given nested blocks should merge the entries and restore the outer ones after them`() {
            val node = ValidationNode(path = "amount", value = 0).apply {
                metadata("operationId" to "op-1", "step" to "outer") {
                    min(1)
                    metadata("step" to "inner") { min(2) }
                    min(3)
                }
                min(4)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "amount",
                    message = "must be at least 1",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1", "step" to "outer")
                ),
                Violation(
                    path = "amount",
                    message = "must be at least 2",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1", "step" to "inner")
                ),
                Violation(
                    path = "amount",
                    message = "must be at least 3",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1", "step" to "outer")
                ),
                Violation(path = "amount", message = "must be at least 4", code = "min")
            )
        }

        @Test
        fun `given each inside the block should attach the entries to every element`() {
            val node = ValidationNode(path = "tags", value = listOf("a", "b")).apply {
                metadata("operationId" to "op-1") { each { minLength(3) } }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "tags[0]",
                    message = "must have at least 3 characters",
                    code = "minLength",
                    metadata = mapOf("operationId" to "op-1")
                ),
                Violation(
                    path = "tags[1]",
                    message = "must have at least 3 characters",
                    code = "minLength",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given a nested object inside the block should merge the entries with its own`() {
            val node = ValidationNode(path = "operation", value = Operation(amount = 0, currency = "BR")).apply {
                metadata("clientId" to "c-9", "operationId" to "outer") { valid() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "operation.amount",
                    message = "must be positive",
                    code = "positive",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-1")
                ),
                Violation(
                    path = "operation.currency",
                    message = "must have exactly 3 characters",
                    code = "length",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-1", "field" to "currency")
                )
            )
        }

        @Test
        fun `given a logical operator inside the block should attach the entries to its violation`() {
            val node = ValidationNode(path = "document", value = "123").apply {
                metadata("operationId" to "op-1") {
                    or {
                        cpf()
                        cnpj()
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "document",
                    message = "must be a valid CPF or must be a valid CNPJ",
                    code = "or",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }
    }

    @Nested
    @DisplayName("When failFast is called")
    inner class FailFast {
        @Test
        fun `given every rule passing should accept the value`() {
            val node = ValidationNode(path = "email", value = "ana@mail.com").apply {
                failFast {
                    email()
                    maxLength(20)
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an empty block should accept the value`() {
            val node = ValidationNode(path = "email", value = "ana@").apply { failFast {} }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given two failing rules should report only the first one`() {
            val node = ValidationNode(path = "email", value = "ana@").apply {
                failFast {
                    email()
                    maxLength(3)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "must be a valid email", code = "email")
            )
        }

        @Test
        fun `given a failing rule after passing ones should report it`() {
            val node = ValidationNode(path = "email", value = "ana@mail.com").apply {
                failFast {
                    email()
                    maxLength(5)
                    minLength(50)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "must have at most 5 characters", code = "maxLength")
            )
        }

        @Test
        fun `given rules after the block should report them too`() {
            val node = ValidationNode(path = "email", value = "ana@").apply {
                failFast {
                    email()
                    maxLength(3)
                }
                minLength(10)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "email", message = "must have at least 10 characters", code = "minLength")
            )
        }

        @Test
        fun `given two blocks should report the first failure of each`() {
            val node = ValidationNode(path = "password", value = "abc").apply {
                failFast {
                    minLength(8)
                    maxLength(2)
                }
                failFast {
                    containsDigit()
                    containsSymbol()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must have at least 8 characters", code = "minLength"),
                Violation(path = "password", message = "must contain a digit", code = "containsDigit")
            )
        }

        @Test
        fun `given a nested block should report the same as a single block`() {
            val node = ValidationNode(path = "email", value = "ana@mail.com").apply {
                failFast {
                    email()
                    failFast {
                        maxLength(5)
                        minLength(50)
                    }
                    minLength(60)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "must have at most 5 characters", code = "maxLength")
            )
        }

        @Test
        fun `given each inside the block should report every failing item and stop`() {
            val node = ValidationNode(path = "tags", value = listOf("a", "b", "ok")).apply {
                failFast {
                    each { minLength(2) }
                    maxSize(1)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags[0]", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "tags[1]", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given the block inside each should report the first failure of each item`() {
            val node = ValidationNode(path = "emails", value = listOf("ana@", "ana@mail.com.br.example")).apply {
                each {
                    failFast {
                        email()
                        maxLength(20)
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails[0]", message = "must be a valid email", code = "email"),
                Violation(path = "emails[1]", message = "must have at most 20 characters", code = "maxLength")
            )
        }

        @Test
        fun `given a nested object inside the block should report it with the full path and stop`() {
            val node = ValidationNode(path = "address", value = Address(street = null)).apply {
                failFast {
                    valid()
                    constraint("is never reached") { false }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "address.street", message = "is required", code = "required")
            )
        }

        @Test
        fun `given an or inside the block should report it as one rule`() {
            val node = ValidationNode(path = "document", value = "123").apply {
                failFast {
                    or {
                        cpf()
                        cnpj()
                    }
                    minLength(20)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "document", message = "must be a valid CPF or must be a valid CNPJ", code = "or")
            )
        }

        @Test
        fun `given the block inside an or should report its first failure as the branch`() {
            val node = ValidationNode(path = "login", value = "ana@").apply {
                or {
                    failFast {
                        email()
                        maxLength(3)
                    }
                    cpf()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "login", message = "must be a valid email or must be a valid CPF", code = "or")
            )
        }

        @Test
        fun `given the block inside an and should report its first failure with the other rules`() {
            val node = ValidationNode(path = "login", value = "ana@").apply {
                and {
                    failFast {
                        email()
                        maxLength(3)
                    }
                    minLength(10)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "login",
                    message = "must be a valid email and must have at least 10 characters",
                    code = "and"
                )
            )
        }

        @Test
        fun `given the block inside not should report a value that passes every rule`() {
            val node = ValidationNode(path = "username", value = "admin").apply {
                not {
                    failFast {
                        startsWith("ad")
                        endsWith("in")
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "is not allowed", code = "not")
            )
        }

        @Test
        fun `given metadata around the block should report the failure with the entries`() {
            val node = ValidationNode(path = "email", value = "ana@").apply {
                metadata("field" to "email") {
                    failFast {
                        email()
                        maxLength(3)
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "email",
                    message = "must be a valid email",
                    code = "email",
                    metadata = mapOf("field" to "email")
                )
            )
        }

        @Test
        fun `given metadata inside the block should report the failure with the entries`() {
            val node = ValidationNode(path = "email", value = "ana@").apply {
                failFast {
                    metadata("field" to "email") { email() }
                    maxLength(3)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "email",
                    message = "must be a valid email",
                    code = "email",
                    metadata = mapOf("field" to "email")
                )
            )
        }

        @Test
        fun `given the block in a nested object should report the first failure at the full path`() {
            val profile = Profile(mailbox = Mailbox(email = "ana@"))

            val result = profile.validate()

            result.violations shouldBe listOf(
                Violation(path = "mailbox.email", message = "must be a valid email", code = "email")
            )
        }

        @Test
        fun `given the block in a list of nested objects should report it at the index with the entries`() {
            val profile = Profile(mailboxes = listOf(Mailbox(), Mailbox(email = "ana@")))

            val result = profile.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "mailboxes[1].email",
                    message = "must be a valid email",
                    code = "email",
                    metadata = mapOf("list" to "mailboxes")
                )
            )
        }

        @Test
        fun `given the block inside an or of a nested object should report the or at the full path`() {
            val profile = Profile(mailbox = Mailbox(login = "ana@"))

            val result = profile.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "mailbox.login",
                    message = "must be a valid email or must be a valid CPF",
                    code = "or"
                )
            )
        }

        @Test
        fun `given a failing rule should skip the rules after it`() {
            var calls = 0
            val node = ValidationNode(path = "email", value = "ana@").apply {
                failFast {
                    email()
                    constraint("is already registered") { calls++ == 0 }
                }
            }

            node.validate()

            calls shouldBe 0
        }

        @Test
        fun `given a value that is not a number should skip the rule that parses it`() {
            val node = ValidationNode(path = "page", value = "abc").apply {
                failFast {
                    integer()
                    constraint("must be at least 1") { it.toInt() >= 1 }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "page", message = "must be an integer", code = "integer")
            )
        }

        @Test
        fun `given the block inside an or should skip the rules after its first failure`() {
            var calls = 0
            val node = ValidationNode(path = "login", value = "ana@").apply {
                or {
                    failFast {
                        email()
                        constraint("is already registered") { calls++ == 0 }
                    }
                    cpf()
                }
            }

            node.validate()

            calls shouldBe 0
        }
    }
}
