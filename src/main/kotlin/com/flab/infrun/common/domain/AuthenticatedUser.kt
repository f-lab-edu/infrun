package com.flab.infrun.common.domain

import java.time.Instant

data class AuthenticatedUser(
    val jti: String,
    val userId: Long,
    val email: String,
    val roles: List<Role>,
    val issuer: String,
    val issuedAt: Instant,
    val expiry: Instant,
)
