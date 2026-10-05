package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.samples.Survey
import io.github.soleworks.validity.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AnyConstraintsTest {
    @Nested
    @DisplayName("When notNull is called")
    inner class NotNull {
        @Test
        fun `given a missing comment should report it as required`() {
            val survey = Survey(comments = listOf("great service", null))

            survey.validate().violations shouldBe listOf(Violation("comments[1]", "is required"))
        }

        @Test
        fun `given an unanswered question should report the custom message`() {
            val survey = Survey(answers = listOf("yes", null))

            survey.validate().violations shouldBe listOf(Violation("answers[1]", "must be answered"))
        }
    }
}
