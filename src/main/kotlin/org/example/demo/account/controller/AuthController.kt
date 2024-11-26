package org.example.demo.account.controller

import org.example.demo.account.dto.ChangePasswordRequest
import org.example.demo.account.dto.LoginRequest
import org.example.demo.account.dto.RegisterRequest
import org.example.demo.account.service.AuthService
import org.example.demo.common.dto.ResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseDto<String> {
        return authService.login(loginRequest)
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseDto<Unit> {
        return authService.register(registerRequest)
    }

    @PostMapping("/change-password")
    fun changePassword(@RequestBody changePasswordRequest: ChangePasswordRequest): ResponseDto<Unit> {
        return authService.changePassword(changePasswordRequest)
    }
}