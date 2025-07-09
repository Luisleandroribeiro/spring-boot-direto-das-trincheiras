package academy.devdojo.commons;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {

    public List<Anime> newAnimeList() {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var swordArtOnline = Anime.builder().id(2L).name("Sword Art Online").build();
        var soloLeveling = Anime.builder().id(3L).name("Solo Leveling").build();
        return new ArrayList<>(List.of(naruto, swordArtOnline, soloLeveling));
    }

    public Anime newAnimeToSave() {
        return Anime.builder().id(99L).name("Overlord").build();
    }
}
