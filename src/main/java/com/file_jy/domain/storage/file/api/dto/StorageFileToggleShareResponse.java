package com.file_jy.domain.storage.file.api.dto;

public class StorageFileToggleShareResponse {
    public final String shareLink;
    public final boolean toggleResult;

    public StorageFileToggleShareResponse(String loginUserId, boolean toggleResult) {
        this.shareLink = "/share/files/" + loginUserId;
        this.toggleResult = toggleResult;
    }
}
