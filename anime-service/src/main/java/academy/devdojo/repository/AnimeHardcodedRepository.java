package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import academy.devdojo.domain.Producer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AnimeHardcodedRepository {
    private static final List<Anime> ANIMES = new ArrayList<>();

    static {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var swordArtOnline = Anime.builder().id(2L).name("Sword Art Online").build();
        var soloLeveling = Anime.builder().id(3L).name("Solo Leveling").build();
        ANIMES.addAll(List.of(naruto, swordArtOnline, soloLeveling));
    }

    public List<Anime> findAll() {
        return ANIMES;
    }


    public Optional <Anime> findById (Long id){
        return ANIMES.stream()
                .filter(animes->animes.getId().equals(id))
                .findFirst();
    }
    public List<Anime> findByName (String name){
        return ANIMES.stream()
                .filter(animes-> animes.getName().equals(name)).toList();

    }

    public Anime save (Anime anime){
        ANIMES.add(anime);
        return anime;
    }

    public void delete (Anime anime){
        ANIMES.remove(anime);
    }

    public void update (Anime anime){
        delete(anime);
        save(anime);
    }
}
