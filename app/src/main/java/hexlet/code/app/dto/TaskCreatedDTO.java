package hexlet.code.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;


@Getter
@Setter
public class TaskCreatedDTO {
    @NotNull
    @Size(min = 1)
    private String title;

    private JsonNullable<Integer> index;

    private JsonNullable<String> content;

    @NotNull
    @JsonProperty("taskStatus")
    private String slug;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;
}
