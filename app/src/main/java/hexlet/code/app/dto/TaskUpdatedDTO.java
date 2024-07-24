package hexlet.code.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdatedDTO {
    @Size(min = 1)
    private JsonNullable<String> title;
    private JsonNullable<Integer> index;
    private JsonNullable<String> content;
    @JsonProperty("taskStatus")
    private JsonNullable<String> slug;
    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;
}
