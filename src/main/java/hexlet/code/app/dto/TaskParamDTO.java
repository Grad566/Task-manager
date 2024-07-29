package hexlet.code.app.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskParamDTO {
    private Long assigneeId;
    private String titleCont;
    private String status;
    private Long labelId;
}
