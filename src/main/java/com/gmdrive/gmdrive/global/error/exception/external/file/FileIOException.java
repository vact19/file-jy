package com.gmdrive.gmdrive.global.error.exception.external.file;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.error.exception.HttpException;

public class FileIOException extends HttpException {
    public FileIOException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public FileIOException(ErrorCode errorCode) {
        super(errorCode);
    }
}
