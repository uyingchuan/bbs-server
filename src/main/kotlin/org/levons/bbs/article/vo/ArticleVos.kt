package org.levons.bbs.article.vo

import jakarta.validation.constraints.NotBlank

data class CreateArticleRequest(
    @field:NotBlank(message = "标题不能为空")
    val title: String,

    @field:NotBlank(message = "内容不能为空")
    val content: String,
)