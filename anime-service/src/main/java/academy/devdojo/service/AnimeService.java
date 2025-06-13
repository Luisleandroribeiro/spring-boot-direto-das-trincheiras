package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardcodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class AnimeService {
    private AnimeHardcodedRepository repository;

    public AnimeService(){
        this.repository= new AnimeHardcodedRepository();
    }

    public List<Anime> findAll (String name){
        return name == null? repository.findAll():repository.findByName(name);
    }

    public Anime findByIdOrThrowNotFound (Long id){
        return repository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer Not Found"));
    }

    public Anime save (Anime anime){
        return repository.save(anime);
    }

    public void delete (Long id){
        var anime = findByIdOrThrowNotFound(id);
        repository.delete(anime);
    }

    public void update (Anime animeToUpdate){
        AssertAnimeExists(animeToUpdate.getId());
        repository.update(animeToUpdate);
    }

    public void AssertAnimeExists (Long id){
        findByIdOrThrowNotFound(id);
    }
}
