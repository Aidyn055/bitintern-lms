package BitlabAcademycom.example.bitIntern.controller;

import BitlabAcademycom.example.bitIntern.dto.ChapterDto;
import BitlabAcademycom.example.bitIntern.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Главы", description = "API для управления главами курсов")
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping
    @Operation(summary = "Получить список всех глав", description = "Возворощает список всех существующих глав")
    public ResponseEntity<List<ChapterDto>> getAllChapters() {
        log.info("REST request to get all Chapters");
        return ResponseEntity.ok(chapterService.getAllChapters());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить главу по ID", description = "Возвращает информацию о главе по ее идентификатору")
    public ResponseEntity<ChapterDto> getChapterById(
            @PathVariable @Parameter(description = "Идентификатор главы", example = "1") Long id) {
        log.info("REST request to get Chapter by id: {}", id);
        return ResponseEntity.ok(chapterService.getChapterById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новую главу", description = "Сохраняет новую главу в базу данных")
    public ResponseEntity<ChapterDto> createChapter(@RequestBody ChapterDto chapterDto) {
        log.info("REST request to create new Chapter");
        ChapterDto created = chapterService.createChapter(chapterDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующую главу", description = "Обновляет данные главы по ее ID")
    public ResponseEntity<ChapterDto> updateChapter(
            @PathVariable @Parameter(description = "Идентификатор главы", example = "1") Long id,
            @RequestBody ChapterDto chapterDto) {
        log.info("REST request to update Chapter with id: {}", id);
        return ResponseEntity.ok(chapterService.updateChapter(id, chapterDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить главу по ID", description = "Удаляет главу из базы данных")
    public ResponseEntity<Void> deleteChapter(
            @PathVariable @Parameter(description = "Идентификатор главы", example = "1") Long id) {
        log.info("REST request to delete Chapter with id: {}", id);
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }
}