package BitlabAcademycom.example.bitIntern.controller;

import BitlabAcademycom.example.bitIntern.dto.CourseDto;
import BitlabAcademycom.example.bitIntern.service.CourseService;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Курсы", description = "API для управления учебными курсами")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "Получить список всех курсов", description = "Возвращает полный список курсов из базы данных")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        log.info("REST request to get all Courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить курс по ID", description = "Возвращает информацию о конкретном курсе по его уникальному идентификатору")
    public ResponseEntity<CourseDto> getCourseById(
            @PathVariable @Parameter(description = "Идентификатор курса", example = "1") Long id) {
        log.info("REST request to get Course by id: {}", id);
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новый курс", description = "Принимает данные курса, сохраняет в БД и возвращает созданный объект с ID")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        log.info("REST request to create new Course");
        CourseDto created = courseService.createCourse(courseDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующий курс", description = "Обновляет текстовые поля курса (имя, описание) по его ID")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable @Parameter(description = "Идентификатор курса", example = "1") Long id,
            @RequestBody CourseDto courseDto) {
        log.info("REST request to update Course with id: {}", id);
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить курс по ID", description = "Удаляет курс и все связанные с ним данные из базы по его ID")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable @Parameter(description = "Идентификатор курса", example = "1") Long id) {
        log.info("REST request to delete Course with id: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}