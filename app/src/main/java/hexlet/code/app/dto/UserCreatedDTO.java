package hexlet.code.app.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

public class UserCreatedDTO {
    @NotNull
    @Email
    private String email;
    
    private String firstName;

    private String lastName;

    @NotNull
    @Size(min = 3)
    private String password;
}
