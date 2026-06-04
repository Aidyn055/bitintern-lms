package BitlabAcademycom.example.bitIntern.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Объект передачи данных курса (DTO)")
public class CourseDto {

    @Schema(description = "Уникальный автоматический ID курса", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Название учебного курса", example = "Java Spring Boot бэкенд", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Подробное описание программы курса", example = "Изучение баз данных, REST API и логирования")
    private String description;

    @Schema(description = "Дата и время создания записи", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdTime;

    @Schema(description = "Дата и время последнего изменения", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedTime;
}
