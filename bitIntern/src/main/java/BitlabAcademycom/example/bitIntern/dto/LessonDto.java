package BitlabAcademycom.example.bitIntern.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Объект передачи данных урока (DTO)")
public class LessonDto {

    @Schema(description = "Уникальный ID урока", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Название урока", example = "Что такое HTTP методы", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Краткое описание урока", example = "Разбор GET, POST, PUT, DELETE запросов")
    private String description;

    @Schema(description = "Текстовое содержание (контент) урока", example = "В этом уроке мы детально изучим протокол...")
    private String content;

    @Schema(description = "Порядковый номер урока для сортировки внутри главы", example = "1")
    private int order;

    @Schema(description = "ID главы, к которой относится этот урок", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long chapterId;

    @Schema(description = "Дата и время создания урока", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdTime;

    @Schema(description = "Дата и время последнего изменения урока", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedTime;
}