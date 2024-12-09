package org.levons.bbs.article.dto

import org.levons.bbs.common.dto.PageRequest


interface BaseArticle {
    val categoryId: String
    val userId: Long
    val title: String
    val summary: String
}

data class BaseArticleImpl(
    override val categoryId: String,
    override val userId: Long,
    override val title: String,
    override val summary: String
) : BaseArticle

data class ArticleInfo(
    val id: Long,
    val baseArticle: BaseArticle,
) : BaseArticle by baseArticle

data class ArticleDetails(
    val id: Long,
    val baseArticle: BaseArticle,
) : BaseArticle by baseArticle

data class GetArticlePageRequest(
    val pageRequest: PageRequest,
    val userId: Long? = null,
) : PageRequest by pageRequest
