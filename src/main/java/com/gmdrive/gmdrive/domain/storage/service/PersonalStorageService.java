package com.gmdrive.gmdrive.domain.storage.service;

import com.gmdrive.gmdrive.domain.storage.entity.Storage;
import com.gmdrive.gmdrive.domain.storage.repository.PersonalStorageRepository;
import com.gmdrive.gmdrive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonalStorageService {
    private final PersonalStorageRepository personalStorageRepository;

    public Storage save(User user) {
        return personalStorageRepository.save(Storage.personalStorage(user));
    }
}
