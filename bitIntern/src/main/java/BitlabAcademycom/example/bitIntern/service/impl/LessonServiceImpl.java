package BitlabAcademycom.example.bitIntern.service.impl;

import BitlabAcademycom.example.bitIntern.dto.LessonDto;
import BitlabAcademycom.example.bitIntern.mapper.LessonMapper;
import BitlabAcademycom.example.bitIntern.model.Lesson;
import BitlabAcademycom.example.bitIntern.repository.LessonRepository;
import BitlabAcademycom.example.bitIntern.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    @Override
    public List<LessonDto> getAllLessons() {
        log.info("Fetching all lessons from database");
        return lessonMapper.toDtoList(lessonRepository.findAll());
    }

    @Override
    public LessonDto getLessonById(Long id) {
        log.info("Fetching lesson with id: {}", id);
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Урок с id " + id + " не найден!"));
        log.debug("Successfully fetched lesson details: {}", lesson);
        return lessonMapper.toDto(lesson);
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        log.info("Creating new lesson"); // INFO: чистый текст
        log.debug("Payload for lesson creation: {}", lessonDto); // DEBUG: все данные объекта

        Lesson lesson = lessonMapper.toEntity(lessonDto);
        Lesson savedLesson = lessonRepository.save(lesson);

        log.info("Lesson successfully created with id: {}", savedLesson.getId());
        return lessonMapper.toDto(savedLesson);
    }

    @Override
    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        log.info("Updating lesson with id: {}", id);
        log.debug("New data payload for update: {}", lessonDto);

        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Урок с id " + id + " не найден для обновления!"));

        existingLesson.setName(lessonDto.getName());
        existingLesson.setContent(lessonDto.getContent()); // Или какое у вас поле для текста урока

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        log.info("Lesson with id: {} successfully updated", updatedLesson.getId());

        return lessonMapper.toDto(updatedLesson);
    }

    @Override
    public void deleteLesson(Long id) {
        log.info("Deleting lesson with id: {}", id);

        if (!lessonRepository.existsById(id)) {
            throw new IllegalArgumentException("Невозможно удалить! Урок с id " + id + " не найден.");
        }

        lessonRepository.deleteById(id);
        log.info("Lesson with id: {} successfully deleted", id);
    }
}