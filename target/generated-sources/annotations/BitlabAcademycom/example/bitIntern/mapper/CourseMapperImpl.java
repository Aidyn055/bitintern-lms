package BitlabAcademycom.example.bitIntern.mapper;

import BitlabAcademycom.example.bitIntern.dto.CourseDto;
import BitlabAcademycom.example.bitIntern.model.Course;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-19T12:39:08+0500",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDto courseDto = new CourseDto();

        courseDto.setId( course.getId() );
        courseDto.setName( course.getName() );
        courseDto.setDescription( course.getDescription() );
        courseDto.setCreatedTime( course.getCreatedTime() );
        courseDto.setUpdatedTime( course.getUpdatedTime() );

        return courseDto;
    }

    @Override
    public Course toEntity(CourseDto courseDto) {
        if ( courseDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDto.getId() );
        course.name( courseDto.getName() );
        course.description( courseDto.getDescription() );
        course.createdTime( courseDto.getCreatedTime() );
        course.updatedTime( courseDto.getUpdatedTime() );

        return course.build();
    }

    @Override
    public List<CourseDto> toDtoList(List<Course> courses) {
        if ( courses == null ) {
            return null;
        }

        List<CourseDto> list = new ArrayList<CourseDto>( courses.size() );
        for ( Course course : courses ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    @Override
    public List<Course> toEntityList(List<CourseDto> courseDtos) {
        if ( courseDtos == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( courseDtos.size() );
        for ( CourseDto courseDto : courseDtos ) {
            list.add( toEntity( courseDto ) );
        }

        return list;
    }
}
