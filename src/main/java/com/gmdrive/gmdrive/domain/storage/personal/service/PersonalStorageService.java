package com.gmdrive.gmdrive.domain.storage.personal.service;

import com.gmdrive.gmdrive.domain.storage.personal.entity.PersonalStorage;
import com.gmdrive.gmdrive.domain.storage.personal.repository.PersonalStorageRepository;
import com.gmdrive.gmdrive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonalStorageService {
    private final PersonalStorageRepository personalStorageRepository;

    public PersonalStorage save(User user) {
        return personalStorageRepository.save(new PersonalStorage(user));
    }
}
