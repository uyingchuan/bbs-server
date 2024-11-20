package org.example.demo.service

import org.example.demo.entity.User
import org.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
    
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
    
    @Transactional
    fun createUser(username: String, password: String, email: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(
            username = username,
            password = encodedPassword,
            email = email
        )
        return userRepository.save(user)
    }
    
    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }
    
    @Transactional
    fun updateUser(user: User): User {
        return userRepository.save(user)
    }

    fun login(input: String, password: String): User? {
        return if (isEmail(input)) {
            // 使用邮箱进行登录
            val user = findByEmail(input)
            if (user != null && passwordEncoder.matches(password, user.password)) {
                user
            } else {
                null
            }
        } else {
            // 使用用户名进行登录
            val user = findByUsername(input)
            if (user != null && passwordEncoder.matches(password, user.password)) {
                user
            } else {
                null
            }
        }
    }

    private fun isEmail(input: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return emailRegex.matches(input)
    }
} 