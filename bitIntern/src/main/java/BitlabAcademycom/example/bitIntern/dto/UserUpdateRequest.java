package BitlabAcademycom.example.bitIntern.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
