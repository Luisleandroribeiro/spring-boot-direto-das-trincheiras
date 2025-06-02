package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Producer {

    private Long id;
    @JsonProperty("name")
    private String name;
    private static List<Producer> producers = new ArrayList<>();
    static {
        var masashiKishimoto = new Producer(1L, "Masashi Kishimoto");
        var aniplex = new Producer(2L, "Aniplex");
        var a1Pictures = new Producer(3L, "A-1 Pictures");
        producers.addAll(List.of(masashiKishimoto, aniplex, a1Pictures));
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}