package com.flab.infrun.cart.application.command;

public record DeleteCartCommand(
    Long ownerId,
    Long lectureId
) {

}
