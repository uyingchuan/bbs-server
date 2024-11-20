package org.example.demo.entity

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
    var email: String
) 