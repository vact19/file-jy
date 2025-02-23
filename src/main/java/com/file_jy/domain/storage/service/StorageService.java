package com.file_jy.domain.storage.service;

import com.file_jy.domain.storage.entity.Storage;
import com.file_jy.domain.storage.repository.StorageRepository;
import com.file_jy.domain.user.entity.User;
import com.file_jy.global.error.errorcode.StorageErrorCode;
import com.file_jy.global.error.exception.business.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final StorageRepository storageRepository;

    @Transactional
    public Storage savePersonal(User user) {
        if (storageRepository.existsPersonalByOwnerId(user.getId())) {
            throw new BusinessException(StorageErrorCode.PERSONAL_STORAGE_ALREADY_EXISTS);
        }
        return storageRepository.save(Storage.personalStorage(user));
    }
}
