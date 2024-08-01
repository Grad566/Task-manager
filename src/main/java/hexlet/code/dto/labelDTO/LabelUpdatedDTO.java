package hexlet.code.dto.labelDTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelUpdatedDTO {
    @Size(min = 3, max = 1000)
    private JsonNullable<String> name;
}
