package com.flab.infrun.member.application.command;

public record SignupCommand(
    String nickname,
    String email,
    String password
) {

}
