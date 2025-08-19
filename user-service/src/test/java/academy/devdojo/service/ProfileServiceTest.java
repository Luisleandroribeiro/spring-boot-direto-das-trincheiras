package academy.devdojo.service;

import academy.devdojo.commons.ProfileUtils;
import academy.devdojo.domain.Profile;
import academy.devdojo.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileServiceTest {
    @InjectMocks
    private ProfileService service;
    @Mock
    private ProfileRepository repository;
    private List<Profile> profileList;
    @InjectMocks
    private ProfileUtils profileUtils;

    @BeforeEach
    void init() {
        profileList = profileUtils.newProfileList();
    }

    @Test
    @DisplayName("findAll returns a list with all profiles when argument is null")
    @Order(1)
    void findAll_ReturnsAllProfiles_WhenArgumentIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(profileList);

        var profiles = service.findAll(null);
        Assertions.assertThat(profiles).isNotNull().hasSameElementsAs(profileList);
    }

    @Test
    @DisplayName("findAll returns list with found object when firstName exists")
    @Order(2)
    void findByName_ReturnsFoundProfileInList_WhenFirstNameIsFound() {
        var profile = profileList.getFirst();
        var expectedProfilesFound = singletonList(profile);

        BDDMockito.when(repository.findByNameIgnoreCase(profile.getName())).thenReturn(expectedProfilesFound);

        var profilesFound = service.findAll(profile.getName());
        Assertions.assertThat(profilesFound).containsAll(expectedProfilesFound);
    }

    @Test
    @DisplayName("findAll returns empty list when name is not found")
    @Order(3)
    void findByName_ReturnsEmptyList_WhenFirstNameIsNotFound() {
        var name = "not-found";
        BDDMockito.when(repository.findByNameIgnoreCase(name)).thenReturn(emptyList());

        var profiles = service.findAll(name);
        Assertions.assertThat(profiles).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns a profile with given id")
    @Order(4)
    void findById_ReturnsProfileById_WhenSuccessful() {
        var expectedProfile = profileList.getFirst();
        BDDMockito.when(repository.findById(expectedProfile.getId())).thenReturn(Optional.of(expectedProfile));

        var profiles = service.findByIdOrThrowNotFound(expectedProfile.getId());

        Assertions.assertThat(profiles).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when profile is not found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenProfileIsNotFound() {
        var expectedProfile = profileList.getFirst();
        BDDMockito.when(repository.findById(expectedProfile.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProfile.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save creates a profile")
    @Order(6)
    void save_CreatesProfile_WhenSuccessful() {
        var profileToSave = profileUtils.newProfileToSave();
        var profileSaved = profileUtils.newProfileSaved();

        BDDMockito.when(repository.save(profileToSave)).thenReturn(profileSaved);

        var savedProfile = service.save(profileToSave);

        Assertions.assertThat(savedProfile).isEqualTo(profileSaved).hasNoNullFieldsOrProperties();
    }
}