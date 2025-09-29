package academy.devdojo.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePostResponse {
    private Long id;
    private String name;
    private String description;
}