package com.gmdrive.gmdrive.global.util;

import com.gmdrive.gmdrive.global.error.errorcode.FileErrorCode;
import com.gmdrive.gmdrive.global.error.exception.external.file.FileIOException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Component
public class FileManager {
    // yml 설정파일
    private final String fileUploadPath;

    public FileManager(@Value("${file.upload.path}")String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    /**@return "저장된 파일명 UUID" + ".확장자". */
    public Path save(FilePrefix prefix, MultipartFile multipartFile){
        // empty Check. type=file 이며 name이 일치한다면, 본문이 비어있어도 MultiPartFile 객체가 생성된다.
        if (multipartFile.isEmpty()) {
            throw new FileIOException(FileErrorCode.MULTIPART_FILE_CANNOT_BE_READ);
        }
        String filenameToStore = convertFileNameToUuid(multipartFile.getOriginalFilename());
        Path filePathToStore = Paths.get(fileUploadPath + prefix.getPrefix(), filenameToStore);
        
        saveMultipartFile(multipartFile, filePathToStore);
        return filePathToStore;
    }

    public void save(FilePrefix prefix, MultipartFile multipartFile, String filenameToStore){
        // empty Check. type=file 이며 name이 일치한다면, 본문이 비어있어도 MultiPartFile 객체가 생성된다.
        if (multipartFile.isEmpty()) {
            throw new FileIOException(FileErrorCode.MULTIPART_FILE_CANNOT_BE_READ);
        }
        Path filePathToStore = Paths.get(fileUploadPath + prefix.getPrefix(), filenameToStore);

        saveMultipartFile(multipartFile, filePathToStore);
    }

    /**@return "저장된 파일명 UUID" + ".확장자". */
    public String save(FilePrefix prefix, String subPath, MultipartFile multipartFile){
        // empty Check. type=file 이며 name이 일치한다면, 본문이 비어있어도 MultiPartFile 객체가 생성된다.
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String storedFileFolderStr = fileUploadPath + subPath + prefix.getPrefix(); // 절대경로
        String storedFileStr = storedFileFolderStr + originalFileName; // 절대경로
        String storedRelativeFileStr = subPath + prefix.getPrefix() + originalFileName; // 상대경로
        Path storedFilePath = Paths.get(storedFileStr);

        File folder = new File(storedFileFolderStr);
        if(!folder.exists() && (!folder.mkdirs()))
            throw new FileIOException(FileErrorCode.FOLDER_CANNOT_BE_CREATED);
        saveMultipartFile(multipartFile, storedFilePath);

        return storedRelativeFileStr;
    }

    private void saveMultipartFile(MultipartFile multipartFile, Path filePathToStore) {
        try {
            // transferTo()는 내부적으로 알아서 Input, Output Stream close 됨.
            multipartFile.transferTo(filePathToStore);
        } catch (IOException e) {
            log.error("IOEXCEPTION 발생: originalFile: {}, filePathToStore: {}", multipartFile.getOriginalFilename(), filePathToStore);
            throw new FileIOException(FileErrorCode.FILE_CANNOT_BE_STORED, e);
        }
    }

    private String convertFileNameToUuid(String filename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(filename);

        return uuid + "." + ext;
    }

    private String extractExt(String filename) {
        int pos = filename.lastIndexOf(".");
        return filename.substring(pos +1);
    }

    public byte[] getByteArray(FilePrefix filePrefix, String fileName) {
        String fullFilePath = filePrefix.getPrefix() + fileName;
        return getByteArray(fullFilePath);
    }

    public byte[] getByteArray(String filePath) {
        try  {
            Path path = Paths.get(fileUploadPath + filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("IOEXCEPTION 발생: filePath: {}", filePath);
            throw new FileIOException(FileErrorCode.FILE_CANNOT_BE_READ, e);
        }
    }

    public void deleteFile(FilePrefix filePrefix, String fileName) {
        deleteFile(filePrefix.getPrefix()+fileName);
    }

    public void deleteFile(String filePath) {
        String filePathToDelete = fileUploadPath + filePath;
        Path pathToDelete = Paths.get(filePathToDelete);

        // NotNull 이므로 예외를 발생시키지 않고 바로 빠져나온다.
        // 파일을 찾을 수 없다면 지울 수도 없으므로 작업 취소. DB파일명은 그대로인데 물리적인 파일만 삭제했을 경우를 대비한다.
        if(!Files.exists(pathToDelete)) return;
        try {
            Files.delete(pathToDelete);
        } catch(IOException e) {
            throw new FileIOException(FileErrorCode.FILE_CANNOT_BE_DELETED, e);
        }
    }

    public void deleteFile(FilePrefix filePrefix, Collection<String> fileNames) {
        fileNames.forEach(fileName -> deleteFile(filePrefix, fileName));
    }

    public boolean isInvalidFileExtension(String fileName, FileExt fileExt) {
        String ext = extractExt(fileName);
        return !ext.equals(fileExt.label);
    }

    /**
     * this.fileUploadPath 내부에 저장될 directory 를 선택한다.
     * fileUploadPath + FilePrefix + fileName 으로 저장된다.
     */
    @Getter
    public enum FilePrefix {
        STORAGE_FILE("storage_file/"),
        NONE(""),
        ;

        private final String prefix;

        FilePrefix(String prefix) {
            this.prefix = prefix;
        }
    }

    @RequiredArgsConstructor
    public enum FileExt {
        PDF("pdf"),
        XLSX("xlsx");

        public final String label;
    }
}
