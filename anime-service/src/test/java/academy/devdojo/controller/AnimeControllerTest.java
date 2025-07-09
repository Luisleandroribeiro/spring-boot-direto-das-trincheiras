package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardcodedRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean
    private AnimeHardcodedRepository repository;
    private List<Anime> animeList;
    @Autowired
    private ResourceLoader resourceLoader;



    @BeforeEach
    void init(){
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var swordArtOnline = Anime.builder().id(2L).name("Sword Art Online").build();
        var soloLeveling = Anime.builder().id(3L).name("Solo Leveling").build();
        animeList = new ArrayList<>(List.of(naruto, swordArtOnline, soloLeveling));
    }
    @Test
    @DisplayName("GET v1/animes returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = readResourceFile("anime/get-animes-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

    @Test
    @DisplayName("GET v1/animes?name=Naruto returns list with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundAnimeInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = readResourceFile("anime/get-animes-naruto-name-200.json");
        var name = "Naruto";
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("GET v1/animes?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = readResourceFile("anime/get-animes-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/1 returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = readResourceFile("anime/get-animes-by-id-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/99 throws responseStatusException 404 when anime is not found")
    @Order(5)
    void findById_ThrowsResponseException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime Not Found"));
        ;
    }

    @Test
    @DisplayName("POST v1/animes creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() throws Exception {
        var request = readResourceFile("anime/post-request-animes-200.json");
        var response = readResourceFile("anime/post-response-animes-201.json");
        var animeToSave = Anime.builder().id(99L).name("Overlord").build();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/animes updates an anime")
    @Order(7)
    void update_UpdatesAnime_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var request = readResourceFile("anime/put-request-animes-200.json");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT v1/animes throws ResponseStatusException when anime is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var request = readResourceFile("anime/put-request-animes-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/animes")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime Not Found"));

    }

    @Test
    @DisplayName("DELETE v1/animes/1 removes an anime")
    @Order(9)
    void delete_RemoveAnime_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);

        var id = animeList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/animes/99 throws ResponseStatusException when anime is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_WhenAnimeIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime Not Found"));

    }

}