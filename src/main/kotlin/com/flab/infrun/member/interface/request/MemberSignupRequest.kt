package com.flab.infrun.member.`interface`.request

import com.flab.infrun.member.application.processor.MemberSignupProcessorKt
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class MemberSignupRequest(
    @NotBlank(message = "닉네임은 필수 입력입니다.")
    val nickname: String,

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?=.*[\\W_])[A-Za-z\\d\\W_]{8,16}$",
        message = "비밀번호는 8자리 ~ 16자리의 영문 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    val password: String,
) {
    fun toCommand(): MemberSignupProcessorKt.Command {
        return MemberSignupProcessorKt.Command(
            nickname = this.nickname,
            email = this.email,
            password = this.password,
        )
    }
}
