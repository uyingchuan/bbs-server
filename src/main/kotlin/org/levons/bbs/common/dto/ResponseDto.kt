package org.levons.bbs.common.dto

class ResponseDto<T>(
    val success: Boolean,
    val code: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null): ResponseDto<T> =
            ResponseDto(
                success = true,
                code = "000000",
                message = "success",
                data = data
            )

        fun <T> error(code: String = "111111", message: String): ResponseDto<T> =
            ResponseDto(
                success = false,
                code = code,
                message = message,
                data = null
            )
    }
}

