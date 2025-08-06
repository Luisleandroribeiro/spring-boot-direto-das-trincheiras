package academy.devdojo.service;

import academy.devdojo.domain.User;
import academy.devdojo.exception.EmailAlreadyExistsException;
import academy.devdojo.exception.NotFoundException;
import academy.devdojo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll(String firstName) {
        return firstName == null ? repository.findAll() : repository.findByFirstNameIgnoreCase(firstName);
    }

    public User findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not Found"));
    }

    public User save(User user) {
        assertEmailDoesNotExist(user.getEmail());
        return repository.save(user);
    }

    public void delete(Long id) {
        var user = findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public void update(User userToUpdate) {
        assertUserExists(userToUpdate.getId());
        assertEmailDoesNotExist(userToUpdate.getEmail(),  userToUpdate.getId());
        repository.save(userToUpdate);
    }

    public void assertUserExists(Long id) {
        findByIdOrThrowNotFound(id);
    }

    public void assertEmailDoesNotExist(String email) {
        repository.findByEmail(email)
                .ifPresent(this::throwEmailExistException);

    }

    public void assertEmailDoesNotExist(String email, Long id) {
        repository.findByEmailAndIdNot(email, id)
                .ifPresent(this::throwEmailExistException);

    }

    private void throwEmailExistException(User user) {
        throw new EmailAlreadyExistsException("E-mail %s already exists".formatted(user.getEmail()));
    }

}