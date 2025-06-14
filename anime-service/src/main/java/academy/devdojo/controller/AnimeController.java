package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/v1/animes")
@Slf4j
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;
    private AnimeService service;

    public AnimeController(AnimeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll (@RequestParam (required = false) String name){
        log.debug("Request received to list all animes, param name '{}' ", name);
        var animes = service.findAll(name);
        var producerGetResponses = MAPPER.toAnimeGetResponseList(animes);
        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById (@PathVariable Long id){
        log.debug("Request to find anime by id: {}", id);

        var animes = service.findByIdOrThrowNotFound(id);
        var producerGetResponse = MAPPER.toAnimeGetResponse(animes);
        return ResponseEntity.ok(producerGetResponse);

    }

    @PostMapping
    public ResponseEntity<AnimePostResponse>  addAnime (@RequestBody AnimePostRequest request){
        log.debug("Request to save anime: {}", request);
        var anime = MAPPER.toAnime(request);
        var animeSaved = service.save(anime);
        var animeGetResponse = MAPPER.toAnimePostResponse(animeSaved);


        return ResponseEntity.status(HttpStatus.CREATED).body(animeGetResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id){
        log.debug("Delete Anime Id: {}", id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity <Void> update (@RequestBody AnimePutRequest request){
        log.debug("Request to update anime {}", request);
        var animeToUpdate = MAPPER.toAnime(request);
        service.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }

}
