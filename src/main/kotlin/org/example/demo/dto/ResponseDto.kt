package org.example.demo.dto

data class ResponseDto<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null): ResponseDto<T> = 
            ResponseDto(
                success = true,
                code = 200,
                message = "success",
                data = data
            )
            
        fun <T> error(code: Int, message: String): ResponseDto<T> = 
            ResponseDto(
                success = false,
                code = code,
                message = message,
                data = null
            )
    }
} 