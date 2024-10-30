package com.gmdrive.gmdrive.domain.common;

import lombok.RequiredArgsConstructor;

/**
 * DB에서 찾을 수 있는 데이터들의 목록. ResourceNotFoundException에서 사용된다.
 * 각 테이블의 한글 용어사전으로써의 기능도 함.
 */
@RequiredArgsConstructor
public enum Datasource {
    // ======== 사용자 ========
    USER("사용자")

    ;
    public final String label;
}
