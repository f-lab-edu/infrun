package com.flab.infrun.cart.application.command;

public record AddCartItemCommand(
    Long ownerId,
    Long lectureId
) {

}
