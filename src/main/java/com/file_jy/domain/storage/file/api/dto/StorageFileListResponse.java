package com.file_jy.domain.storage.file.api.dto;

import com.file_jy.domain.storage.file.entity.StorageFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class StorageFileListResponse {
    public final String storageId;
    public final List<StorageFileListItemDto> storageFiles;

    public static StorageFileListResponse from(String storageId, List<StorageFile> storageFiles) {
        return new StorageFileListResponse(
                storageId, StorageFileListItemDto.from(storageFiles)
        );
    }

    public static StorageFileListResponse from(List<StorageFile> storageFiles) {
        return from(null, storageFiles);
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StorageFileListItemDto {
        public final String id;
        public final String name;
        public final LocalDateTime createdTime;
        public final LocalDateTime lastModifiedTime;
        public final String fileSize;
        public final String downloadLink;
        public final boolean isSharing;

        private static List<StorageFileListItemDto> from(List<StorageFile> storageFiles) {
            return storageFiles.stream()
                    .map(storageFile -> StorageFileListItemDto.builder()
                            .id(storageFile.getId().toString())
                            .name(storageFile.getName())
                            .createdTime(storageFile.getCreatedTime())
                            .lastModifiedTime(storageFile.getLastModifiedTime())
                            .fileSize(storageFile.getDisplaySize())
                            .downloadLink(String.format("/files/%s/download", storageFile.getId()))
                            .isSharing(storageFile.isSharing())
                            .build()
                    )
                    .toList();
        }
    }
}
