package academy.devdojo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/heroes")
public class HeroController {
    private static final List<String> Heroes = List.of("Guts", "Zoro", "Kakashi", "Goku");

    @GetMapping
    public List<String> listAllHeroes() {
        return Heroes;
    }

    @GetMapping("filter")
    public List<String> listAllHeroesParam(@RequestParam String name) {
        return Heroes.stream().filter(hero -> hero.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterList")
    public List<String> listAllHeroesParamList(@RequestParam List<String> names) {
        return Heroes.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public String findByeName(@PathVariable String name) {
        return Heroes
                .stream()
                .filter(hero -> hero.equalsIgnoreCase(name))
                .findFirst().orElse("");
    }
}
