package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Directory
import io.github.soleworks.validity.samples.Warehouse
import io.github.soleworks.validity.samples.readme.Destination
import io.github.soleworks.validity.samples.readme.destinationValidation
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a valid destination should be valid`() {
            val destination = Destination(street = "Rua das Flores", zipCode = "01310")

            val result = runBlocking { destinationValidation.validate(destination) }

            result.isValid shouldBe true
        }

        @Test
        fun `given two destinations should report the rules of each with its own values`() {
            val first = Destination(street = "", zipCode = "01310")
            val second = Destination(street = "Rua das Flores", zipCode = "013")

            val result = runBlocking {
                destinationValidation.validate(first).violations + destinationValidation.validate(second).violations
            }

            result shouldBe listOf(
                Violation(path = "street", message = "must not be blank", code = "notBlank"),
                Violation(path = "zipCode", message = "must have at least 5 characters", code = "minLength")
            )
        }

        @Test
        fun `given rules written in a class with a property of the same name should report the value checked`() {
            val rules = Warehouse(street = "Rua das Flores").destinationValidation()

            val result = runBlocking { rules.validate(Destination(street = "", zipCode = "01310")) }

            result.violations shouldBe listOf(
                Violation(path = "street", message = "must not be blank", code = "notBlank")
            )
        }
    }

    @Nested
    @DisplayName("When validateBlocking is called")
    inner class ValidateBlocking {
        @Test
        fun `given a valid destination should be valid`() {
            val destination = Destination(street = "Rua das Flores", zipCode = "01310")

            val result = destinationValidation.validateBlocking(destination)

            result.isValid shouldBe true
        }

        @Test
        fun `given a destination with a blank street and a short zip code should report both`() {
            val destination = Destination(street = "", zipCode = "013")

            val result = destinationValidation.validateBlocking(destination)

            result.violations shouldBe listOf(
                Violation(path = "street", message = "must not be blank", code = "notBlank"),
                Violation(path = "zipCode", message = "must have at least 5 characters", code = "minLength")
            )
        }

        @Test
        fun `given a Validity with messages should report the rules with them`() {
            val validity = Validity { messages { notBlank = "não pode ficar em branco" } }
            val destination = Destination(street = "", zipCode = "01310")

            val result = destinationValidation.validateBlocking(destination, validity)

            result.violations shouldBe listOf(
                Violation(path = "street", message = "não pode ficar em branco", code = "notBlank")
            )
        }

        @Test
        fun `given a block that calls a suspend function should report the rule that depends on it`() {
            val streets = Directory("Rua Fechada")
            val rules = validation<Destination> {
                val isClosed = street != null && streets.exists(street)
                constraint(::street, message = "is closed", code = "closed") { !isClosed }
            }

            val result = rules.validateBlocking(Destination(street = "Rua Fechada", zipCode = "01310"))

            result.violations shouldBe listOf(
                Violation(path = "street", message = "is closed", code = "closed")
            )
        }
    }
}
