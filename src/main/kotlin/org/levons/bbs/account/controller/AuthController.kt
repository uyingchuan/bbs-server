package org.levons.bbs.account.controller

import org.levons.bbs.account.dto.ChangePasswordRequest
import org.levons.bbs.account.dto.LoginRequest
import org.levons.bbs.account.dto.LoginResponse
import org.levons.bbs.account.dto.RegisterRequest
import org.levons.bbs.account.service.AuthService
import org.levons.bbs.common.dto.ResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseDto<LoginResponse> {
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