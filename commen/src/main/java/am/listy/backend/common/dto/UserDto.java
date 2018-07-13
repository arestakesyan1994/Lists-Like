package am.listy.backend.common.dto;

import am.listy.backend.common.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;
    private String name;
    private String surname;
    private String username;
    private String phone;
    private String email;
    private UserType userType;

}
