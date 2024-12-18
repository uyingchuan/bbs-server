package org.levons.bbs.common.exception

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.stp.StpUtil
import jakarta.servlet.http.HttpServletRequest
import org.levons.bbs.common.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    // 处理参数验证失败异常
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseDto<Nothing> {
        val message = e.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        log.info("[参数验证失败] {}", message)
        return ResponseDto.error(message = message)
    }

    // 处理请求方式异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ResponseDto<Nothing>> {
        log.info("[请求方式错误]", e)
        return ResponseEntity.status(405).body(ResponseDto.error(message = "Method Not Allowed"))
    }

    // 处理业务逻辑异常
    @ExceptionHandler(ServiceException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: ServiceException): ResponseDto<Nothing> {
        log.info("[业务处理失败] {}，操作者ID：{}", e, StpUtil.getLoginIdDefaultNull())
        return ResponseDto.error(message = e.message ?: "业务处理失败")
    }

    // 处理权限检验未通过的异常
    @ExceptionHandler(NotPermissionException::class)
    fun handleNotPermissionException(
        e: NotPermissionException?,
        request: HttpServletRequest?
    ): ResponseEntity<ResponseDto<Nothing>> {
        log.info("[权限校验失败] 用户没有操作权限，操作者ID：{}", StpUtil.getLoginIdDefaultNull())
        return ResponseEntity.status(403).body(ResponseDto.error(message = "无权限"))
    }

    // 处理登录鉴权未通过的异常
    @ExceptionHandler(NotLoginException::class)
    fun handleNotLoginException(
        e: NotLoginException?,
        request: HttpServletRequest?
    ): ResponseEntity<ResponseDto<Nothing>> {
        return ResponseEntity.status(401).body(ResponseDto.error(message = "未登录"))
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(e: Exception): ResponseDto<Nothing> {
        log.error("系统异常", e)
        return ResponseDto.error(message = "服务器内部错误")
    }
} 