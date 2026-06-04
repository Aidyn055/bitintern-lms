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
@Schema(description = "Объект передачи данных главы (DTO)")
public class ChapterDto {

    @Schema(description = "Уникальный ID главы", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Название главы", example = "Введение в REST API", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Краткое описание содержания главы", example = "В этой главе изучаются основы протокола HTTP")
    private String description;

    @Schema(description = "Порядковый номер главы для сортировки внутри курса", example = "1")
    private int order;

    @Schema(description = "ID курса, к которому привязана эта глава", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long courseId;

    @Schema(description = "Дата и время создания главы", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdTime;

    @Schema(description = "Дата и время последнего обновления главы", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedTime;
}