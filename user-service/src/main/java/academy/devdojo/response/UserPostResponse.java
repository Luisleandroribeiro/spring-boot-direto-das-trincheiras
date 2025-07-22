package academy.devdojo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserPostResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}