package com.gmdrive.gmdrive.global.error.exception.external.db;

import com.gmdrive.gmdrive.domain.common.Datasource;
import com.gmdrive.gmdrive.global.error.exception.HttpException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends HttpException {
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(Datasource datasource, Object id) {
        super(String.format("Id=[%s]인 %s 를 찾을 수 없습니다.", id.toString(), datasource.name())
                , NOT_FOUND);
    }

    public ResourceNotFoundException(Datasource datasource, String attribute, Object value) {
        super(String.format("%s=[%s]인 %s 를 찾을 수 없습니다.", attribute, value.toString(), datasource.name())
                , NOT_FOUND);
    }
}
