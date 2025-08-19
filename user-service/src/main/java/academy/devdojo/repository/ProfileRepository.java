package academy.devdojo.repository;

import academy.devdojo.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByNameIgnoreCase(String name);

}
