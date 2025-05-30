package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/v1/animes")
public class AnimeController {

    @GetMapping
    public List<Anime> listAll (@RequestParam (required = false) String name){
        var animes = Anime.getAnimes();
        if (name==null) return animes;

        return animes.stream().filter(n->n.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Anime findById (@PathVariable Long id){
        return Anime.getAnimes()
                .stream()
                .filter(animes->animes.getId().equals(id))
                .findFirst().orElse(null);
    }

}
