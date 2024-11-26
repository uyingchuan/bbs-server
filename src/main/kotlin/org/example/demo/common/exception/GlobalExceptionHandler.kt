package org.example.demo.common.exception

import org.example.demo.common.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 处理参数验证失败异常
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(e: org.springframework.web.bind.MethodArgumentNotValidException): ResponseDto<Nothing> {
        val message = e.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("参数验证失败: $message", e)
        return ResponseDto.error(message = message)
    }

    // 处理业务逻辑异常
    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseDto<Nothing> {
        log.warn("业务逻辑异常: ${e.message}", e)
        return ResponseDto.error(message = e.message ?: "请求参数错误")
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(e: Exception): ResponseDto<Nothing> {
        log.error("系统异常", e)
        return ResponseDto.error(message = "服务器内部错误")
    }
} 