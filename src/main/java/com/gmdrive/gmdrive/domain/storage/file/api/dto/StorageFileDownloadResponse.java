package com.gmdrive.gmdrive.domain.storage.file.api.dto;

import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageFileDownloadResponse {
    public final String name;
    public final byte[] data;
    public final long sizeInBytes;

    public static StorageFileDownloadResponse from(StorageFile storageFile, byte[] fileData) {
        return new StorageFileDownloadResponse(storageFile.getName(), fileData, storageFile.getSizeInBytes());
    }
}
