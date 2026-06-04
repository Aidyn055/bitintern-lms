package BitlabAcademycom.example.bitIntern.service.impl;

import BitlabAcademycom.example.bitIntern.dto.LessonDto;
import BitlabAcademycom.example.bitIntern.mapper.LessonMapper;
import BitlabAcademycom.example.bitIntern.model.Lesson;
import BitlabAcademycom.example.bitIntern.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Lesson lessonEntity;
    private LessonDto lessonDto;

    @BeforeEach
    void setUp() {
        lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setName("Введение в Spring Boot");
        lessonDto.setContent("Spring Boot — это платформа...");

        lessonEntity = new Lesson();
        lessonEntity.setId(1L);
        lessonEntity.setName("Введение в Spring Boot");
        lessonEntity.setContent("Spring Boot — это платформа...");
    }

    @Test
    void testGetAllLessons_Success() {
        Mockito.when(lessonRepository.findAll()).thenReturn(List.of(lessonEntity));
        Mockito.when(lessonMapper.toDtoList(anyList())).thenReturn(List.of(lessonDto));

        List<LessonDto> result = lessonService.getAllLessons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Введение в Spring Boot", result.get(0).getName());
        Mockito.verify(lessonRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetLessonById_Success() {
        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lessonEntity));
        Mockito.when(lessonMapper.toDto(lessonEntity)).thenReturn(lessonDto);

        LessonDto result = lessonService.getLessonById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        Mockito.verify(lessonRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetLessonById_NotFound() {
        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lessonService.getLessonById(1L)
        );

        assertEquals("Урок с id 1 не найден!", exception.getMessage());
    }

    @Test
    void testCreateLesson_Success() {
        Mockito.when(lessonMapper.toEntity(any(LessonDto.class))).thenReturn(lessonEntity);
        Mockito.when(lessonRepository.save(any(Lesson.class))).thenReturn(lessonEntity);
        Mockito.when(lessonMapper.toDto(any(Lesson.class))).thenReturn(lessonDto);

        LessonDto result = lessonService.createLesson(lessonDto);

        assertNotNull(result);
        assertEquals("Введение в Spring Boot", result.getName());
        Mockito.verify(lessonRepository, Mockito.times(1)).save(any(Lesson.class));
    }

    @Test
    void testUpdateLesson_Success() {
        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lessonEntity));
        Mockito.when(lessonRepository.save(any(Lesson.class))).thenReturn(lessonEntity);
        Mockito.when(lessonMapper.toDto(any(Lesson.class))).thenReturn(lessonDto);

        LessonDto result = lessonService.updateLesson(1L, lessonDto);

        assertNotNull(result);
        assertEquals("Введение в Spring Boot", result.getName());
        Mockito.verify(lessonRepository, Mockito.times(1)).save(any(Lesson.class));
    }

    @Test
    void testUpdateLesson_NotFound() {
        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lessonService.updateLesson(1L, lessonDto)
        );

        assertEquals("Урок с id 1 не найден для обновления!", exception.getMessage());
    }

    @Test
    void testDeleteLesson_Success() {
        Mockito.when(lessonRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(lessonRepository).deleteById(1L);

        assertDoesNotThrow(() -> lessonService.deleteLesson(1L));

        Mockito.verify(lessonRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLesson_NotFound() {
        Mockito.when(lessonRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                lessonService.deleteLesson(1L)
        );

        assertEquals("Невозможно удалить! Урок с id 1 не найден.", exception.getMessage());
    }
}