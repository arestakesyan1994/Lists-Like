package am.listy.backend.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    private String surname;
    private String username;
    private String phone;
    private String email;
    private String password;
    private UserType type;
    private String picUrl;
    private String description;
    private String linkedUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String youTubeUrl;
}
