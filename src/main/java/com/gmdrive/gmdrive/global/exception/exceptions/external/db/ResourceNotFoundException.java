package com.gmdrive.gmdrive.global.exception.exceptions.external.db;

import com.gmdrive.gmdrive.domain.common.Datasource;
import com.gmdrive.gmdrive.global.exception.exceptions.HttpException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends HttpException {
    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(Datasource datasource, long id) {
        super(String.format("Id=[%d]인 %s 를 찾을 수 없습니다.", id, datasource.name())
                , NOT_FOUND);
    }

    public ResourceNotFoundException(Datasource datasource, String attribute, String value) {
        super(String.format("%s=[%s]인 %s 를 찾을 수 없습니다.", attribute, value, datasource.name())
                , NOT_FOUND);
    }
}
