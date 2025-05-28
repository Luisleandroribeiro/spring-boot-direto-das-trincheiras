package academy.devdojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping ("/v1/animes")
public class AnimeController {
    @GetMapping
    public String listAnimes (){
        ArrayList<String> Animes =new ArrayList ();
        Animes.add ("Naruto");
        Animes.add ("Sword Art Online");
        Animes.add ("Solo Leveling");

        return Animes.toString();
    }
}
