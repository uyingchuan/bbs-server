package org.levons.bbs.article.controller

import cn.dev33.satoken.annotation.SaCheckLogin
import org.levons.bbs.article.dto.ArticleInfo
import org.levons.bbs.article.dto.GetArticlePageRequest
import org.levons.bbs.article.service.ArticleService
import org.levons.bbs.article.vo.CreateArticleRequest
import org.levons.bbs.common.dto.PageResponse
import org.levons.bbs.common.dto.ResponseDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/article")
class ArticleController(private val articleService: ArticleService) {
    @SaCheckLogin
    @PostMapping("/create")
    fun create(@RequestBody createArticleRequest: CreateArticleRequest): ResponseDto<Long> {
        return articleService.create(createArticleRequest)
    }

    @PostMapping("/get")
    fun getArticleList(@RequestBody getArticlePageRequest: GetArticlePageRequest): ResponseDto<PageResponse<ArticleInfo>> {
        return articleService.getArticlePage(getArticlePageRequest)
    }
}