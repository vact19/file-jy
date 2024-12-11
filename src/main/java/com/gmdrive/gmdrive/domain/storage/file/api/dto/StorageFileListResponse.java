package com.gmdrive.gmdrive.domain.storage.file.api.dto;

import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class StorageFileListResponse {
    public final List<StorageFileListItemDto> storageFiles;

    public static StorageFileListResponse from(List<StorageFile> storageFiles) {
        return new StorageFileListResponse(
                StorageFileListItemDto.from(storageFiles)
        );
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StorageFileListItemDto {
        public final String id;
        public final String name;
        public final LocalDateTime createdTime;
        public final LocalDateTime lastModifiedTime;
        public final String fileSize;

        private static List<StorageFileListItemDto> from(List<StorageFile> storageFiles) {
            return storageFiles.stream()
                    .map(storageFile -> StorageFileListItemDto.builder()
                            .id(storageFile.getId())
                            .name(storageFile.getName())
                            .createdTime(storageFile.getCreatedTime())
                            .lastModifiedTime(storageFile.getLastModifiedTime())
                            .fileSize(storageFile.getDisplaySize())
                            .build()
                    )
                    .toList();
        }
    }
}
