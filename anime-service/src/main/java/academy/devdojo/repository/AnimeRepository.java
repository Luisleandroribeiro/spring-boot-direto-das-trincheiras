package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByNameIgnoreCase(String name);

}
