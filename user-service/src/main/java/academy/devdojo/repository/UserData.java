package academy.devdojo.repository;

import academy.devdojo.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {
    private List<User> users = new ArrayList<>();

    {
        var luis = User.builder().id(1L).firstName("Luis").lastName("Ribeiro").email("luis@gmail.com").build();
        var felipe = User.builder().id(2L).firstName("Felipe").lastName("Pires").email("felipe@gmail.com").build();
        var ana = User.builder().id(3L).firstName("Ana").lastName("Alves").email("ana@gmail.com").build();
        users = new ArrayList<>(List.of(luis, felipe, ana));
    }

    public List<User> getUsers() {
        return users;
    }

}
