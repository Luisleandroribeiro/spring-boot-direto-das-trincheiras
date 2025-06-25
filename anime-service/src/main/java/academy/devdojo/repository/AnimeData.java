package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AnimeData {
    private static final List<Anime> animes = new ArrayList<>();

    {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var swordArtOnline = Anime.builder().id(2L).name("Sword Art Online").build();
        var soloLeveling = Anime.builder().id(3L).name("Solo Leveling").build();
        animes.addAll(List.of(naruto, swordArtOnline, soloLeveling));
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}
