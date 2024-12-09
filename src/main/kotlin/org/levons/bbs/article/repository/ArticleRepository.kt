package org.levons.bbs.article.repository

import org.levons.bbs.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findArticleById(id: Long): Article?
    fun findByUserId(id: Long): List<Article>
}