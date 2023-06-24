package com.flab.infrun.member.application.command;

public record LoginCommand(
    String email,
    String password
) {

}
