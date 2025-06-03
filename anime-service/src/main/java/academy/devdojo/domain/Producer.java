package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Producer {

    private Long id;
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;
    private static List<Producer> producers = new ArrayList<>();
    static {
        var masashiKishimoto = Producer.builder().id(1L).name("Masashi Kishimoto").createdAt(LocalDateTime.now()).build();
        var aniplex = Producer.builder().id(2L).name("Aniplex").createdAt(LocalDateTime.now()).build();
        var a1Pictures = Producer.builder().id(3L).name("A-1 Pictures").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(masashiKishimoto, aniplex, a1Pictures));
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}