package org.example.demo.account.service

import cn.dev33.satoken.stp.StpUtil
import org.example.demo.account.dto.*
import org.example.demo.common.dto.ResponseDto
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class AuthService(private val userService: UserService) {
    fun login(request: LoginRequest): ResponseDto<LoginResponse> {

        val user = when {
            request.account.contains("@") -> userService.findByEmail(request.account)
            else -> userService.findByUsername(request.account)
        } ?: throw IllegalArgumentException("账号不存在")

        if (!authenticate(request.password, user.password)) {
            return ResponseDto.error(code = "100001", message = "账号或密码错误")
        }

        // 登录，生成token
        StpUtil.login(user.id)

        // 返回
        val userInfo = UserInfo(
            email = user.email,
            username = user.username,
            avatar = user.avatar,
            nickname = user.nickname,
            description = user.description,
            fansCount = user.fansCount,
            followCount = user.followCount,
        )
        val response = LoginResponse(
            token = StpUtil.getTokenValue(),
            userInfo = userInfo
        )
        return ResponseDto.success(response)
    }

    fun register(request: RegisterRequest): ResponseDto<Unit> {
        if (userService.existsByEmail(request.email)) {
            return ResponseDto.error(code = "100001", message = "邮箱已注册")
        }

        val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())
        userService.createUser(
            username = request.username,
            password = hashedPassword,
            email = request.email
        )
        return ResponseDto.success()
    }

    fun changePassword(request: ChangePasswordRequest): ResponseDto<Unit> {
        val user = userService.findByEmail(request.email) ?: throw IllegalArgumentException("账号不存在")

        if (request.verificationCode != "ccnb") {
            return ResponseDto.error(code = "100001", message = "验证码错误")
        }

        user.password = hashPassword(request.newPassword)
        userService.updateUser(user)
        return ResponseDto.success()
    }

    private fun authenticate(password: String, hashedPassword: String): Boolean {
        return verifyPassword(password, hashedPassword)
    }

    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    private fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(password, hashedPassword)
    }
}