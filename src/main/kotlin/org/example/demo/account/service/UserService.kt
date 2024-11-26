package org.example.demo.account.service

import org.example.demo.entity.User
import org.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    @Transactional
    fun createUser(username: String, password: String, email: String): User {
        val user = User(
            username = username,
            password = password,
            email = email,
            roles = "user"
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

    private fun isEmail(input: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return emailRegex.matches(input)
    }

}