package org.example.demo.account.entity

import jakarta.persistence.*

@Entity
@Table(name = "t_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Column(unique = true, nullable = false)
    var email: String,

    /**
     * owner: 作者
     * admin: 管理员
     * user: 用户
     */
    @Column(nullable = false)
    var roles: String,

    @Column(nullable = false)
    var avatar: String,

    @Column(nullable = false)
    var nickname: String,

    @Column()
    var description: String?,

    @Column()
    var fansCount: Int = 0,

    @Column()
    var followCount: Int = 0,
) 