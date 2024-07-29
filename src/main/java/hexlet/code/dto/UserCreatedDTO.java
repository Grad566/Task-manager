package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class UserCreatedDTO {
    @NotNull
    @Email
    private String email;

    private JsonNullable<String> firstName;

    private JsonNullable<String> lastName;

    @NotNull
    @Size(min = 3)
    private String password;
}
