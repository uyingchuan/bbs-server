package org.levons.bbs.account.service

import org.levons.bbs.account.entity.User
import org.levons.bbs.account.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

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
            avatar = "https://i2.hdslb.com/bfs/face/456751421fe6c1c12116503906e960892dd25d03.jpg",
            nickname = generateRandomCode(),
            roles = "user",
            description = "(￢︿̫̿￢☆)"
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

    private fun generateRandomCode(length: Int = 8): String {
        val characters = "-ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_"
        return (1..length)
            .map { characters.random() }
            .joinToString("")
    }
}