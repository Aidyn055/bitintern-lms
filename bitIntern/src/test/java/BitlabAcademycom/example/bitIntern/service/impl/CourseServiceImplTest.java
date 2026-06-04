package BitlabAcademycom.example.bitIntern.service.impl;

import BitlabAcademycom.example.bitIntern.dto.CourseDto;
import BitlabAcademycom.example.bitIntern.mapper.CourseMapper;
import BitlabAcademycom.example.bitIntern.model.Course;
import BitlabAcademycom.example.bitIntern.repository.CourseRepository;
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
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course courseEntity;
    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Java Spring Boot");
        courseDto.setDescription("Test Description");

        courseEntity = new Course();
        courseEntity.setId(1L);
        courseEntity.setName("Java Spring Boot");
        courseEntity.setDescription("Test Description");
    }

    @Test
    void testGetAllCourses_Success() {
        Mockito.when(courseRepository.findAll()).thenReturn(List.of(courseEntity));
        Mockito.when(courseMapper.toDtoList(anyList())).thenReturn(List.of(courseDto));

        List<CourseDto> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Spring Boot", result.get(0).getName());
        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetCourseById_Success() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        Mockito.when(courseMapper.toDto(courseEntity)).thenReturn(courseDto);

        CourseDto result = courseService.getCourseById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        Mockito.verify(courseRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetCourseById_NotFound() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.getCourseById(1L)
        );

        assertEquals("Курс с id 1 не найден!", exception.getMessage());
    }

    @Test
    void testCreateCourse_Success() {
        Mockito.when(courseMapper.toEntity(any(CourseDto.class))).thenReturn(courseEntity);
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(courseEntity);
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(courseDto);

        CourseDto result = courseService.createCourse(courseDto);

        assertNotNull(result);
        assertEquals("Java Spring Boot", result.getName());
        Mockito.verify(courseRepository, Mockito.times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse_Success() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(courseEntity));
        Mockito.when(courseRepository.save(any(Course.class))).thenReturn(courseEntity);
        Mockito.when(courseMapper.toDto(any(Course.class))).thenReturn(courseDto);

        CourseDto result = courseService.updateCourse(1L, courseDto);

        assertNotNull(result);
        assertEquals("Java Spring Boot", result.getName());
        Mockito.verify(courseRepository, Mockito.times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse_NotFound() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.updateCourse(1L, courseDto)
        );

        assertEquals("Курс с id 1 не найден для обновления!", exception.getMessage());
    }

    @Test
    void testDeleteCourse_Success() {
        Mockito.when(courseRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(courseRepository).deleteById(1L);

        assertDoesNotThrow(() -> courseService.deleteCourse(1L));

        Mockito.verify(courseRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCourse_NotFound() {
        Mockito.when(courseRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.deleteCourse(1L)
        );

        assertEquals("Невозможно удалить! Курс с id 1 не найден.", exception.getMessage());
    }
}