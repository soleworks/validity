package io.github.soleworks.validity.spring

import kotlinx.serialization.json.JsonObject

const val VALID_REGISTRATION = """{"email":"ana@example.com","password":"correct horse"}"""
const val INVALID_REGISTRATION = """{"email":"ana@","password":"123"}"""
const val INVITATION_OF_ANA = """{"email":"ana@example.com"}"""

val BOTH_VIOLATIONS: JsonObject = json(
    """{"title":"Bad Request","status":400,"violations":[""" +
        """{"path":"email","code":"email","message":"must be a valid email"},""" +
        """{"path":"password","code":"minLength","message":"must have at least 8 characters"}]}"""
)
val MEMBER_VIOLATION: JsonObject = json(
    """{"title":"Bad Request","status":400,"violations":[""" +
        """{"path":"email","code":"member","message":"is already in the team"}]}"""
)
val NO_BODY = JsonObject(emptyMap())
