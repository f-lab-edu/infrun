package com.flab.infrun.member.domain.exception

import com.flab.infrun.common.exception.ErrorCode
import com.flab.infrun.common.exception.SystemException

class DuplicatedEmailExceptionKt : SystemException(ErrorCode.DUPLICATED_EMAIL)
