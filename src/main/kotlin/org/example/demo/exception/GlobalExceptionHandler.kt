package org.example.demo.exception

import org.example.demo.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(UsernameNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUsernameNotFoundException(e: UsernameNotFoundException): ResponseDto<Nothing> {
        log.warn("用户不存在: ${e.message}", e)
        return ResponseDto.error(404, e.message ?: "用户不存在")
    }

    @ExceptionHandler(BadCredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseDto<Nothing> {
        log.warn("认证失败: ${e.message}", e)
        return ResponseDto.error(401, "用户名或密码错误")
    }

    // 处理参数验证失败异常
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(e: org.springframework.web.bind.MethodArgumentNotValidException): ResponseDto<Nothing> {
        val message = e.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("参数验证失败: $message", e)
        return ResponseDto.error(400, message)
    }

    // 处理业务逻辑异常
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseDto<Nothing> {
        log.warn("业务逻辑异常: ${e.message}", e)
        return ResponseDto.error(400, e.message ?: "请求参数错误")
    }

    // 处理权限不足异常
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(e: org.springframework.security.access.AccessDeniedException): ResponseDto<Nothing> {
        log.warn("权限不足: ${e.message}", e)
        return ResponseDto.error(403, "权限不足")
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(e: Exception): ResponseDto<Nothing> {
        log.error("系统异常", e)
        return ResponseDto.error(500, "服务器内部错误")
    }
} 