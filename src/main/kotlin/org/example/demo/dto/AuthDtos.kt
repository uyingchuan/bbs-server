package org.example.demo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "登录账号不能为空")
    val loginId: String,
    
    @field:NotBlank(message = "密码不能为空")
    val password: String
)

data class LoginResponse(
    val token: String
)

data class RegisterRequest(
    @field:NotBlank(message = "用户名不能为空")
    @field:Size(min = 4, max = 20, message = "用户名长度必须在4-20之间")
    val username: String,

    @field:NotBlank(message = "密码不能为空")
    @field:Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    val password: String,

    @field:NotBlank(message = "邮箱不能为空")
    @field:Email(message = "邮箱格式不正确")
    val email: String
)

data class RegisterResponse(
    val username: String,
    val email: String
)

data class ChangePasswordRequest(
    @field:NotBlank(message = "邮箱不能为空")
    @field:Email(message = "邮箱格式不正确")
    val email: String,

    @field:NotBlank(message = "验证码不能为空")
    val verificationCode: String,

    @field:NotBlank(message = "新密码不能为空")
    @field:Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    val newPassword: String
)

data class ChangePasswordResponse(
    val email: String,
    val message: String = "密码修改成功"
) 