package org.example.demo.controller

import jakarta.validation.Valid
import org.example.demo.dto.*
import org.example.demo.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseDto<LoginResponse> {
        val token = authService.login(loginRequest.loginId, loginRequest.password)
        return ResponseDto.success(LoginResponse(token))
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseDto<RegisterResponse> {
        val response = authService.register(request)
        return ResponseDto.success(response)
    }

    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody request: ChangePasswordRequest): ResponseDto<ChangePasswordResponse> {
        val response = authService.changePassword(request)
        return ResponseDto.success(response)
    }
} 