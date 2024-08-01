package hexlet.code.dto.taskStatusDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskStatusUpdatedDTO {
    @Column(unique = true)
    @Size(min = 1)
    private JsonNullable<String> name;

    @Column(unique = true)
    @Size(min = 1)
    private JsonNullable<String> slug;
}
