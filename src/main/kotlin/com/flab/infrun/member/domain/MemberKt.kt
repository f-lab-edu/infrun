package com.flab.infrun.member.domain

import com.flab.infrun.common.entity.BaseEntity
import jakarta.persistence.*

@Table(name = "members_kt")
@Entity
class MemberKt(
    id: Long = 0,
    nickname: String,
    email: String,
    password: String,
    role: Role = Role.USER
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "nickname")
    var nickname = nickname
        protected set

    @Column(name = "email")
    var email = email
        protected set

    @Column(name = "password")
    var password = password
        protected set

    @Enumerated(value = EnumType.STRING)
    var role = role
        protected set

    enum class Role {
        USER, TEACHER
    }

    fun promoteToTeacher() {
        role = Role.TEACHER
    }
}
