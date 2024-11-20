package org.example.demo.service

import org.example.demo.dto.*
import org.example.demo.security.JwtUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val userService: UserService,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder
) {

    fun login(email: String, password: String): String {
        // 获取用户信息
        val user = userService.findByUsername(email)
            ?: throw IllegalArgumentException("用户不存在")

        // 使用找到的用户信息进行认证
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user.email, password)
        )

        val userDetails = userDetailsService.loadUserByUsername(user.email)
        return jwtUtils.generateToken(userDetails)
    }

    @Transactional
    fun register(request: RegisterRequest): RegisterResponse {
        // 检查邮箱是否已存在
        if (userService.findByEmail(request.email) != null) {
            throw IllegalArgumentException("邮箱已被注册")
        }

        // 检查用户名是否已存在
        if (userService.findByUsername(request.username) != null) {
            throw IllegalArgumentException("用户名已被使用")
        }

        // 创建新用户
        val user = userService.createUser(
            username = request.username,
            password = request.password,
            email = request.email
        )

        return RegisterResponse(
            username = user.username,
            email = user.email
        )
    }

    @Transactional
    fun changePassword(request: ChangePasswordRequest): ChangePasswordResponse {
        // 查找用户
        val user = userService.findByEmail(request.email)
            ?: throw IllegalArgumentException("邮箱未注册")

        // 验证验证码
        if (request.verificationCode != "ccnb") {
            throw IllegalArgumentException("验证码错误")
        }

        // 更新密码
        user.password = passwordEncoder.encode(request.newPassword)
        userService.updateUser(user)

        return ChangePasswordResponse(
            email = user.email
        )
    }
} 