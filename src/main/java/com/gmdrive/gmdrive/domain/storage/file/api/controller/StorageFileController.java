package com.gmdrive.gmdrive.domain.storage.file.api.controller;

import com.gmdrive.gmdrive.api.ResponseTemplate;
import com.gmdrive.gmdrive.domain.storage.file.api.dto.StorageFileDownloadResponse;
import com.gmdrive.gmdrive.domain.storage.file.api.dto.StorageFileListResponse;
import com.gmdrive.gmdrive.domain.storage.file.entity.StorageFile;
import com.gmdrive.gmdrive.domain.storage.file.service.StorageFileService;
import com.gmdrive.gmdrive.global.error.errorcode.FileErrorCode;
import com.gmdrive.gmdrive.global.error.exception.external.file.FileIOException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class StorageFileController {
    private final StorageFileService storageFileService;

    @PostMapping("/storages/{storageId}/files")
    public ResponseEntity<ResponseTemplate<Void>> handleStorageFileUpload(
        MultipartFile file
        , @PathVariable long storageId
        , @AuthenticationPrincipal long userId
    ) {
        StorageFile uploadedFile = storageFileService.save(file, storageId, userId);
        ResponseTemplate<Void> result = new ResponseTemplate<>(HttpStatus.CREATED,
                String.format("업로드 완료. 파일명: %s, 파일 크기: %s", uploadedFile.getName(), uploadedFile.getDisplaySize())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 본인 저장소 파일 조회. 스크롤-커서기반 페이지네이션 이후 적용
    @GetMapping("/storages/me/files")
    public ResponseTemplate<StorageFileListResponse> handleGetStorageFiles(
            @AuthenticationPrincipal long userId
    ) {
        StorageFileListResponse downloadResponse = storageFileService.getLists(userId);
        return new ResponseTemplate<>(HttpStatus.OK
                , String.format("파일 %d건 조회 완료", downloadResponse.storageFiles.size())
                , downloadResponse);
    }

    @GetMapping("/files/{fileId}/download")
    public void handleStorageFileUpload(
            @PathVariable String fileId
            , @AuthenticationPrincipal long userId
            , HttpServletResponse response
    ) {
        StorageFileDownloadResponse downloadResponse = storageFileService.download(fileId, userId);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(downloadResponse.name, StandardCharsets.UTF_8)
                .build();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        try(ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(downloadResponse.data);
        } catch (IOException e) {
            throw new FileIOException(FileErrorCode.FAILED_RESPONSE, e);
        }
    }
}
