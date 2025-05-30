package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;

    public static List<Anime> getAnimes () {
        var naruto = new Anime (1L, "Naruto");
        var swordArtOnline = new Anime (2L, "Sword Art Online");
        var soloLeveling = new Anime (3L, "Solo Leveling");

        return List.of(naruto, swordArtOnline, soloLeveling);
    }

}