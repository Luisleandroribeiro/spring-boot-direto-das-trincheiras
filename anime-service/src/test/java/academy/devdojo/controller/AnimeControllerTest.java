package academy.devdojo.controller;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.commons.FileUtils;
import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardcodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "academy.devdojo")
class AnimeControllerTest {
    private final static String URL = "/v1/animes";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean
    private AnimeHardcodedRepository repository;
    private List<Anime> animeList;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private AnimeUtils animeUtils;


    @BeforeEach
    void init() {
        animeList = animeUtils.newAnimeList();
    }

    @Test
    @DisplayName("GET v1/animes returns a list with all animes when argument is null")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenArgumentIsNull() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = fileUtils.readResourceFile("anime/get-animes-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("GET v1/animes?name=Naruto returns list with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundAnimeInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = fileUtils.readResourceFile("anime/get-animes-naruto-name-200.json");
        var name = "Naruto";
        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }


    @Test
    @DisplayName("GET v1/animes?name=x returns empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = fileUtils.readResourceFile("anime/get-animes-x-name-200.json");
        var name = "x";

        mockMvc.perform(MockMvcRequestBuilders.get(URL).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/1 returns a anime with given id")
    @Order(4)
    void findById_ReturnsAnimeById_WhenSuccessful() throws Exception {
        BDDMockito.when(animeData.getAnimes()).thenReturn(animeList);
        var response = fileUtils.readResourceFile("anime/get-animes-by-id-200.json");
        var id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
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

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime Not Found"));

    }

    @Test
    @DisplayName("POST v1/animes creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() throws Exception {
        var request = fileUtils.readResourceFile("anime/post-request-animes-200.json");
        var response = fileUtils.readResourceFile("anime/post-response-animes-201.json");
        var animeToSave = animeUtils.newAnimeToSave();

        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
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

        var request = fileUtils.readResourceFile("anime/put-request-animes-200.json");
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
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
        var request = fileUtils.readResourceFile("anime/put-request-animes-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
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
    @ParameterizedTest
    @MethodSource("postAnimesBadRequestSource")
    @DisplayName("POST v1/animes returns bad request when fields are invalid")
    @Order(11)
    void save_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("anime/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        org.assertj.core.api.Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> postAnimesBadRequestSource (){
        var allRequiredErrors = requiredErrors();

        return Stream.of(
                Arguments.of("post-request-animes-blank-fields-400.json", allRequiredErrors),
                Arguments.of("post-request-animes-empty-fields-400.json", allRequiredErrors)

        );
    }
    private static List<String> requiredErrors(){
        var nameIsRequired= "The field 'name' is required";
        return List.of(nameIsRequired);
    }



    @ParameterizedTest
    @MethodSource("putAnimesBadRequestSource")
    @DisplayName("Put v1/animes returns bad request when fields are invalid")
    @Order(12)
    void update_ReturnsBadRequest_WhenFieldsAreInvalid(String fileName, List<String> errors) throws Exception {
        var request = fileUtils.readResourceFile("anime/%s".formatted(fileName));

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var resolvedException = mvcResult.getResolvedException();

        Assertions.assertThat(resolvedException).isNotNull();

        Assertions.assertThat(resolvedException.getMessage()).contains(errors);
    }

    private static Stream<Arguments> putAnimesBadRequestSource (){

        var allRequiredErrors = new java.util.ArrayList<>(requiredErrors());
        allRequiredErrors.add("The field 'id' can't be null");
        return Stream.of(
                Arguments.of("put-request-animes-blank-fields-400.json", allRequiredErrors),
                Arguments.of("put-request-animes-empty-fields-400.json", allRequiredErrors)

        );
    }
}