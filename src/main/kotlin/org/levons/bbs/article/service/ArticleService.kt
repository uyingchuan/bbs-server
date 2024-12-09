package org.levons.bbs.article.service

import cn.dev33.satoken.stp.StpUtil
import org.levons.bbs.account.service.UserService
import org.levons.bbs.article.dto.ArticleInfo
import org.levons.bbs.article.dto.BaseArticleImpl
import org.levons.bbs.article.dto.GetArticlePageRequest
import org.levons.bbs.article.entity.Article
import org.levons.bbs.article.repository.ArticleRepository
import org.levons.bbs.article.vo.CreateArticleRequest
import org.levons.bbs.common.dto.PageResponse
import org.levons.bbs.common.dto.ResponseDto
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userService: UserService
) {

    @Transactional
    fun create(request: CreateArticleRequest): ResponseDto<Long> {
        val userId = StpUtil.getLoginIdAsLong()

        val paragraphs = request.content.split("\n\n")
        val summary = paragraphs.take(3).joinToString("\n\n").substring(0, 100)

        val article = Article(
            userId = userId,
            title = request.title,
            summary = summary,
            content = request.content,
            contentType = null,
            cover = null,
            status = 0,
            viewCount = 0,
            commentCount = 0,
            likeCount = 0,
        )


        val result = articleRepository.save(article)
        val log = LoggerFactory.getLogger(this::class.java)
        log.info(
            "[新增文章成功] 操作人ID：{}，操作人名称：{}，文章ID：{}，文章标题：{}",
            StpUtil.getLoginId(),
            StpUtil.getTokenValue(),
            article.id,
            article.title
        )
        return ResponseDto.success(data = result.id)
    }

    fun getArticlePage(getArticlePageRequest: GetArticlePageRequest)
            : ResponseDto<PageResponse<ArticleInfo>> {
        // 取文章
        val pageable =
            PageRequest.of(
                getArticlePageRequest.pageNum,
                getArticlePageRequest.pageSize,
                Sort.by(Sort.Order.asc("createTime"))
            )
        val page = articleRepository.findAll(pageable)

        // 合并用户信息
        val articleList = page.content.map { article ->
            val author = userService.getUserById(article.userId)
                ?: throw Exception("用户数据异常")
            ArticleInfo(
                id = article.id,
                baseArticle = BaseArticleImpl(
                    title = article.title,
                    summary = article.summary,
                    categoryId = "",
                    userId = author.id,
                )
            )
        }

        return ResponseDto.success(
            data = PageResponse.from(
                page = page,
                data = articleList
            ),
        )
    }
}