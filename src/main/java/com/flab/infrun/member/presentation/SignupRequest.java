package com.flab.infrun.member.presentation;

import com.flab.infrun.member.application.command.SignupCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(

    @NotBlank(message = "닉네임은 필수 입력입니다.")
    String nickname,

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    String email,

    @Size(min = 8, max = 14, message = "비밀번호는 8자 이상, 14자 이하여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    String password
) {

    public SignupCommand toCommand() {
        return new SignupCommand(nickname, email, password);
    }
}