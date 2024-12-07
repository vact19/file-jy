package com.gmdrive.gmdrive.global.error.exception.common;

import com.gmdrive.gmdrive.global.error.errorcode.CommonErrorCode;
import com.gmdrive.gmdrive.global.error.exception.HttpException;

public class UncoveredEnumCaseException extends HttpException {

    public UncoveredEnumCaseException() {
        super(CommonErrorCode.UNCOVERED_ENUM_CASE);
    }
}
