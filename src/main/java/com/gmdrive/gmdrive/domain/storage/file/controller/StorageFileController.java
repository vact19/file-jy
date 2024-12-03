package com.gmdrive.gmdrive.domain.storage.file.controller;

import com.gmdrive.gmdrive.api.ResponseTemplate;
import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import com.gmdrive.gmdrive.domain.storage.file.service.StorageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class StorageFileController {
    private final StorageFileService storageFileService;

    @PostMapping("/storages/{storageId}/files")
    public ResponseEntity<ResponseTemplate<Void>> handleStorageFileUpload(
        MultipartFile file,
        @PathVariable long storageId,
        @AuthenticationPrincipal long uploaderId
    ) {
        StorageFile uploadedFile = storageFileService.save(file, storageId, uploaderId);
        ResponseTemplate<Void> result = new ResponseTemplate<>(HttpStatus.CREATED,
                String.format("업로드 완료. 파일명: %s, 파일 크기: %s", uploadedFile.getOriginalFilename(), uploadedFile.getDisplaySize())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
