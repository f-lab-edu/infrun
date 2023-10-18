package com.flab.infrun.member.domain.exception

import com.flab.infrun.common.exception.ErrorCode
import com.flab.infrun.common.exception.SystemException

class NotMatchPasswordExceptionKt : SystemException(ErrorCode.NOT_MATCH_PASSWORD)
