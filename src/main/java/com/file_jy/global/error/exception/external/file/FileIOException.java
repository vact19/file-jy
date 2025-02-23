package com.file_jy.global.error.exception.external.file;

import com.file_jy.global.error.errorcode.ErrorCode;
import com.file_jy.global.error.exception.HttpException;

public class FileIOException extends HttpException {
    public FileIOException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public FileIOException(ErrorCode errorCode) {
        super(errorCode);
    }
}
