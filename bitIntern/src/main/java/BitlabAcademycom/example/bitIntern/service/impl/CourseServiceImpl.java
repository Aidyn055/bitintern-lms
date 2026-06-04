package BitlabAcademycom.example.bitIntern.service.impl;

import BitlabAcademycom.example.bitIntern.dto.CourseDto;
import BitlabAcademycom.example.bitIntern.mapper.CourseMapper;
import BitlabAcademycom.example.bitIntern.model.Course;
import BitlabAcademycom.example.bitIntern.repository.CourseRepository;
import BitlabAcademycom.example.bitIntern.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> getAllCourses() {
        log.info("Fetching all courses from database");
        return courseMapper.toDtoList(courseRepository.findAll());
    }

    @Override
    public CourseDto getCourseById(Long id) {
        log.info("Fetching course with id: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Курс с id " + id + " не найден!"));
        log.debug("Successfully fetched course details: {}", course);
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        log.info("Creating new course");
        log.debug("Payload for course creation: {}", courseDto);

        Course course = courseMapper.toEntity(courseDto);
        Course savedCourse = courseRepository.save(course);

        log.info("Course successfully created with id: {}", savedCourse.getId());
        return courseMapper.toDto(savedCourse);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        log.info("Updating course with id: {}", id);
        log.debug("New data payload for update: {}", courseDto);

        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Курс с id " + id + " не найден для обновления!"));

        existingCourse.setName(courseDto.getName());
        existingCourse.setDescription(courseDto.getDescription());

        Course updatedCourse = courseRepository.save(existingCourse);
        log.info("Course with id: {} successfully updated", updatedCourse.getId());

        return courseMapper.toDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);

        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Невозможно удалить! Курс с id " + id + " не найден.");
        }

        courseRepository.deleteById(id);
        log.info("Course with id: {} successfully deleted", id);
    }
}