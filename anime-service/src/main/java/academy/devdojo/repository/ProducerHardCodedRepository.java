package academy.devdojo.repository;

import academy.devdojo.domain.Producer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ProducerHardCodedRepository {
    private static final List<Producer> PRODUCERS = new ArrayList<>();

    static {
        var mappa =  Producer.builder().id(1L).name( "Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation =   Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madhouse =  Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        PRODUCERS.addAll(List.of(mappa, kyotoAnimation, madhouse));
    }


    public List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id){
        return PRODUCERS.stream()
                .filter(producers->producers.getId().equals(id))
                .findFirst();
    }

    public List<Producer> findByName (String name){
        return PRODUCERS.stream()
                .filter(producers -> producers.getName().equals(name)).toList();
    }

    public Producer save (Producer producer){
        PRODUCERS.add(producer);
        return producer;
    }

    public void delete (Producer producer){
        PRODUCERS.remove(producer);
    }

    public void update (Producer producer){
        delete(producer);
        save(producer);
    }
}
