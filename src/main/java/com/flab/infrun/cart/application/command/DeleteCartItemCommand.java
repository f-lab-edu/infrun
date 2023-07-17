package com.flab.infrun.cart.application.command;

public record DeleteCartItemCommand(
    Long ownerId,
    Long lectureId
) {

}
