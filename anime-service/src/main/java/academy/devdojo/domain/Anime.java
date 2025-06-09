package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();
    static {
        var naruto = new Anime (1L, "Naruto");
        var swordArtOnline = new Anime (2L, "Sword Art Online");
        var soloLeveling = new Anime (3L, "Solo Leveling");
        animes.addAll(List.of(naruto, swordArtOnline, soloLeveling));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}