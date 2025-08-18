package academy.devdojo.commons;

import academy.devdojo.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> newUserList() {
        var luis = User.builder().id(1L).firstName("Luis").lastName("Ribeiro").email("luis@gmail.com").build();
        var felipe = User.builder().id(2L).firstName("Felipe").lastName("Pires").email("felipe@gmail.com").build();
        var ana = User.builder().id(3L).firstName("Ana").lastName("Silva").email("ana@gmail.com").build();
        return new ArrayList<>(List.of(luis, felipe, ana));
    }

    public User newUserToSave() {
        return User.builder()
                .id(99L)
                .firstName("Andrew")
                .lastName("Polo")
                .email("andrew@gmail.com")
                .build();
    }
}
