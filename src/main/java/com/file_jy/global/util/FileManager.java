package com.file_jy.global.util;

import com.file_jy.global.error.errorcode.FileErrorCode;
import com.file_jy.global.error.exception.external.file.FileIOException;
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

/**
 * 파일 경로는
 * base path -> FileManager.baseFilePath
 * sub path -> context path는 FilePrefix
 * filename -> DB.

 * basePath + subPath + filename 을 조합하여 파일 추가, 삭제를 수행함.
 */
@Slf4j
@Component
public class FileManager {
    // yml 설정파일
    private final String baseFilePath;

    public FileManager(@Value("${file.base-path}")String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    /**@return "저장된 파일명 UUID" + ".확장자". */
    public String save(FilePrefix prefix, MultipartFile multipartFile){
        // empty Check. type=file 이며 name이 일치한다면, 본문이 비어있어도 MultiPartFile 객체가 생성된다.
        if (multipartFile.isEmpty()) {
            throw new FileIOException(FileErrorCode.MULTIPART_FILE_CANNOT_BE_READ);
        }
        String directoryToStore = baseFilePath + prefix.getPrefix();
        String filenameToStore = convertFileNameToUuid(multipartFile.getOriginalFilename());
        Path filePath = Path.of(directoryToStore, filenameToStore);

        createDirectory(Path.of(directoryToStore));
        saveMultipartFile(multipartFile, filePath);
        return filenameToStore;
    }

    public void save(FilePrefix prefix, MultipartFile multipartFile, String filenameToStore){
        // empty Check. type=file 이며 name이 일치한다면, 본문이 비어있어도 MultiPartFile 객체가 생성된다.
        if (multipartFile.isEmpty()) {
            throw new FileIOException(FileErrorCode.MULTIPART_FILE_CANNOT_BE_READ);
        }
        Path filePathToStore = Paths.get(baseFilePath + prefix.getPrefix(), filenameToStore);

        saveMultipartFile(multipartFile, filePathToStore);
    }

    /**@return "저장된 파일명 UUID" + ".확장자". */
    public String save(FilePrefix prefix, String subPath, MultipartFile multipartFile){
        // empty Check. type=file 이며 name이 일치한다면, 본문이 비어있어도 MultiPartFile 객체가 생성된다.
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String storedFileFolderStr = baseFilePath + subPath + prefix.getPrefix(); // 절대경로
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
        Path fullFilePath = Path.of(baseFilePath, filePrefix.getPrefix(), fileName);
        return getByteArray(fullFilePath);
    }

    private byte[] getByteArray(Path filePath) {
        try  {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("IOEXCEPTION 발생: filePath: {}", filePath);
            throw new FileIOException(FileErrorCode.FILE_CANNOT_BE_READ, e);
        }
    }

    public void deleteFile(FilePrefix filePrefix, String fileName) {
        deleteFile(Path.of(filePrefix.getPrefix(), fileName));
    }

    private void deleteFile(Path pathToDelete) {
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

    private void createDirectory(Path dirPath) {
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            throw new FileIOException(FileErrorCode.FOLDER_CANNOT_BE_CREATED, e);
        }
    }

    /**
     * fileUploadPath + FilePrefix + fileName 으로 저장된다.
     */
    @Getter
    public enum FilePrefix {
        STORAGE_FILE("storage_file/"),
        NONE(""),
        ;

        public final String prefix;

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
