package org.levons.bbs.common.dto

import org.springframework.data.domain.Page

open class PageResponse<T>(
    val pageSize: Int,
    val pageNum: Int,
    val total: Long,
    val data: List<T>
) {
    companion object {
        fun <T> from(page: Page<*>, data: List<T>): PageResponse<T> =
            PageResponse(
                pageSize = page.size,
                pageNum = page.number,
                total = page.totalElements,
                data = data
            )
    }
}

interface PageRequest {
    val pageNum: Int
    val pageSize: Int
}
