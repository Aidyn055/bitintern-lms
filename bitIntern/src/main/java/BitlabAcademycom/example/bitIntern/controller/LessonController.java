package BitlabAcademycom.example.bitIntern.controller;

import BitlabAcademycom.example.bitIntern.dto.LessonDto;
import BitlabAcademycom.example.bitIntern.service.LessonService;
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
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Уроки", description = "API для управления уроками")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    @Operation(summary = "Получить список всех уроков", description = "Возвращает все уроки из базы данных")
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        log.info("REST request to get all Lessons");
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить урок по ID", description = "Возвращает информацию об уроке по его ID")
    public ResponseEntity<LessonDto> getLessonById(
            @PathVariable @Parameter(description = "Идентификатор урока", example = "1") Long id) {
        log.info("REST request to get Lesson by id: {}", id);
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новый урок", description = "Сохраняет новый урок в базу данных")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDto) {
        log.info("REST request to create new Lesson");
        LessonDto created = lessonService.createLesson(lessonDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующий урок", description = "Обновляет поля урока по его ID")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable @Parameter(description = "Идентификатор урока", example = "1") Long id,
            @RequestBody LessonDto lessonDto) {
        log.info("REST request to update Lesson with id: {}", id);
        return ResponseEntity.ok(lessonService.updateLesson(id, lessonDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить урок по ID", description = "Удаляет урок из базы данных")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable @Parameter(description = "Идентификатор урока", example = "1") Long id) {
        log.info("REST request to delete Lesson with id: {}", id);
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}