package com.gmdrive.gmdrive.domain.storage.service;

import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import com.gmdrive.gmdrive.domain.storage.repository.StorageRepository;
import com.gmdrive.gmdrive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final StorageRepository storageRepository;

    public Storage save(User user) {
        return storageRepository.save(Storage.personalStorage(user));
    }
}
