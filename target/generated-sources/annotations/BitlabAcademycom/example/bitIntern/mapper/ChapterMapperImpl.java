package BitlabAcademycom.example.bitIntern.mapper;

import BitlabAcademycom.example.bitIntern.dto.ChapterDto;
import BitlabAcademycom.example.bitIntern.model.Chapter;
import BitlabAcademycom.example.bitIntern.model.Course;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T23:40:05+0500",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ChapterMapperImpl implements ChapterMapper {

    @Override
    public ChapterDto toDto(Chapter chapter) {
        if ( chapter == null ) {
            return null;
        }

        ChapterDto.ChapterDtoBuilder chapterDto = ChapterDto.builder();

        chapterDto.courseId( chapterCourseId( chapter ) );
        chapterDto.id( chapter.getId() );
        chapterDto.name( chapter.getName() );
        chapterDto.description( chapter.getDescription() );
        chapterDto.order( chapter.getOrder() );
        chapterDto.createdTime( chapter.getCreatedTime() );
        chapterDto.updatedTime( chapter.getUpdatedTime() );

        return chapterDto.build();
    }

    @Override
    public Chapter toEntity(ChapterDto chapterDto) {
        if ( chapterDto == null ) {
            return null;
        }

        Chapter.ChapterBuilder chapter = Chapter.builder();

        chapter.course( chapterDtoToCourse( chapterDto ) );
        chapter.id( chapterDto.getId() );
        chapter.name( chapterDto.getName() );
        chapter.description( chapterDto.getDescription() );
        chapter.order( chapterDto.getOrder() );
        chapter.createdTime( chapterDto.getCreatedTime() );
        chapter.updatedTime( chapterDto.getUpdatedTime() );

        return chapter.build();
    }

    @Override
    public List<ChapterDto> toDtoList(List<Chapter> chapters) {
        if ( chapters == null ) {
            return null;
        }

        List<ChapterDto> list = new ArrayList<ChapterDto>( chapters.size() );
        for ( Chapter chapter : chapters ) {
            list.add( toDto( chapter ) );
        }

        return list;
    }

    @Override
    public List<Chapter> toEntityList(List<ChapterDto> chapterDtos) {
        if ( chapterDtos == null ) {
            return null;
        }

        List<Chapter> list = new ArrayList<Chapter>( chapterDtos.size() );
        for ( ChapterDto chapterDto : chapterDtos ) {
            list.add( toEntity( chapterDto ) );
        }

        return list;
    }

    private Long chapterCourseId(Chapter chapter) {
        Course course = chapter.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getId();
    }

    protected Course chapterDtoToCourse(ChapterDto chapterDto) {
        if ( chapterDto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( chapterDto.getCourseId() );

        return course.build();
    }
}
