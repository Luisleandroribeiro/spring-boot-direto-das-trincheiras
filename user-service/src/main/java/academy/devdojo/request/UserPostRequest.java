package academy.devdojo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPostRequest {
    private String firstName;
    private String lastName;
    private String email;
}