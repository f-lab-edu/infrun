package com.flab.infrun.member.domain

import com.flab.infrun.common.entity.BaseEntity
import jakarta.persistence.*

@Table(name = "members")
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
    var id: Long = id
        protected set

    @Column(name = "nickname")
    var nickname: String = nickname
        protected set

    @Column(name = "email")
    var email: String = email
        protected set

    @Column(name = "password")
    var password: String = password
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role = role
        protected set

    fun promoteRole(role: Role) {
        this.role = Role.TEACHER
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberKt

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}