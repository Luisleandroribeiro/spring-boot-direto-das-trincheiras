package academy.devdojo.service;


import academy.devdojo.domain.Profile;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;

    public List<Profile> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByNameIgnoreCase(name);
    }

    public Profile findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profile not Found"));
    }

    public Profile save(Profile profile) {
        return repository.save(profile);
    }
}
