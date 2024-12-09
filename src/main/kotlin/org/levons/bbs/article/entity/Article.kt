package org.levons.bbs.article.entity

import jakarta.persistence.*

@Entity
@Table(name = "t_article")
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 1000,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var summary: String,

    @Column(nullable = false)
    var content: String,

    @Column()
    var contentType: String? = null,

    @Column()
    var cover: String? = null,

    @Column(nullable = false)
    var status: Int,

    @Column
    var viewCount: Int = 0,

    @Column
    var commentCount: Int = 0,

    @Column
    var likeCount: Int = 0
)