package academy.devdojo.domain;

import java.util.List;

public class Anime {

    private Long id;
    private String name;

    public static List<Anime> getAnimes () {
        var naruto = new Anime (1, "Naruto");
        var swordArtOnline = new Anime (2, "Sword Art Online");
        var soloLeveling = new Anime (3, "Solo Leveling");

        return List.of(naruto, swordArtOnline, soloLeveling);
    }

    public Anime(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
