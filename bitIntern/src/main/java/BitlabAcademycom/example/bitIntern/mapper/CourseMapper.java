package BitlabAcademycom.example.bitIntern.mapper;

import BitlabAcademycom.example.bitIntern.dto.CourseDto;
import BitlabAcademycom.example.bitIntern.model.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
    List<CourseDto> toDtoList(List<Course> courses);

    List<Course> toEntityList(List<CourseDto> courseDtos);
}
