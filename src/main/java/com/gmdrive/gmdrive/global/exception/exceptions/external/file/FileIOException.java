package com.gmdrive.gmdrive.global.exception.exceptions.external.file;

import com.gmdrive.gmdrive.global.exception.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.exception.exceptions.HttpException;

public class FileIOException extends HttpException {
    public FileIOException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public FileIOException(ErrorCode errorCode) {
        super(errorCode);
    }
}
