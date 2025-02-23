package com.file_jy.global.error.exception.common;

import com.file_jy.global.error.errorcode.CommonErrorCode;
import com.file_jy.global.error.exception.HttpException;

public class UncoveredEnumCaseException extends HttpException {

    public UncoveredEnumCaseException() {
        super(CommonErrorCode.UNCOVERED_ENUM_CASE);
    }
}
