package org.levons.bbs.account.vo

data class RegisterResponse(
    val username: String,
    val email: String
)

data class LoginResponse(
    val token: String,
    val userInfo: UserInfo
)

data class UserInfo(
    val email: String,
    val username: String,
    val nickname: String,
    val avatar: String?,
    val description: String?,
    val fansCount: Int,
    val followCount: Int,
)
