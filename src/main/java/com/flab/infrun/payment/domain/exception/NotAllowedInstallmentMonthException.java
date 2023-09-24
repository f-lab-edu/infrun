package com.flab.infrun.payment.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class NotAllowedInstallmentMonthException extends SystemException {

    public NotAllowedInstallmentMonthException() {
        super(ErrorCode.NOT_ALLOWED_INSTALLMENT_MONTH);
    }
}
