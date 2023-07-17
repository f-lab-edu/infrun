package com.flab.infrun.cart.application.command;

public record AddCartCommand(
    Long ownerId,
    Long lectureId
) {

}
