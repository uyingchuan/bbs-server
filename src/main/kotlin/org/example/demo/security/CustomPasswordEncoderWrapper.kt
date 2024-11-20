package org.example.demo.security

import org.springframework.security.crypto.password.PasswordEncoder
import java.util.regex.Pattern

class CustomPasswordEncoderWrapper(
    private val delegate: PasswordEncoder
) : PasswordEncoder {

    companion object {
        private val VALID_PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|`~]*$")
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 32
    }

    override fun encode(rawPassword: CharSequence): String {
        validatePassword(rawPassword.toString())
        return delegate.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return try {
            validatePassword(rawPassword.toString())
            delegate.matches(rawPassword, encodedPassword)
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun validatePassword(password: String) {
        require(password.length in MIN_LENGTH..MAX_LENGTH) {
            "密码长度必须在 $MIN_LENGTH 到 $MAX_LENGTH 个字符之间"
        }
        require(VALID_PASSWORD_REGEX.matcher(password).matches()) {
            "密码只能包含字母、数字和常见特殊字符"
        }
    }
} 