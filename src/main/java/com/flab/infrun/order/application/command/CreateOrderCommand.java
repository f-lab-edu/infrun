package com.flab.infrun.order.application.command;

import com.flab.infrun.member.domain.Member;
import java.util.List;

public record CreateOrderCommand(
    Member customer,
    List<Long> lectureIds,
    String couponCode
) {

}
