package BitlabAcademycom.example.bitIntern.mapper;

import BitlabAcademycom.example.bitIntern.dto.LessonDto;
import BitlabAcademycom.example.bitIntern.model.Chapter;
import BitlabAcademycom.example.bitIntern.model.Lesson;
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
public class LessonMapperImpl implements LessonMapper {

    @Override
    public LessonDto toDto(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }

        LessonDto.LessonDtoBuilder lessonDto = LessonDto.builder();

        lessonDto.chapterId( lessonChapterId( lesson ) );
        lessonDto.id( lesson.getId() );
        lessonDto.name( lesson.getName() );
        lessonDto.description( lesson.getDescription() );
        lessonDto.content( lesson.getContent() );
        lessonDto.order( lesson.getOrder() );
        lessonDto.createdTime( lesson.getCreatedTime() );
        lessonDto.updatedTime( lesson.getUpdatedTime() );

        return lessonDto.build();
    }

    @Override
    public Lesson toEntity(LessonDto lessonDto) {
        if ( lessonDto == null ) {
            return null;
        }

        Lesson.LessonBuilder lesson = Lesson.builder();

        lesson.chapter( lessonDtoToChapter( lessonDto ) );
        lesson.id( lessonDto.getId() );
        lesson.name( lessonDto.getName() );
        lesson.description( lessonDto.getDescription() );
        lesson.content( lessonDto.getContent() );
        lesson.order( lessonDto.getOrder() );
        lesson.createdTime( lessonDto.getCreatedTime() );
        lesson.updatedTime( lessonDto.getUpdatedTime() );

        return lesson.build();
    }

    @Override
    public List<LessonDto> toDtoList(List<Lesson> lessons) {
        if ( lessons == null ) {
            return null;
        }

        List<LessonDto> list = new ArrayList<LessonDto>( lessons.size() );
        for ( Lesson lesson : lessons ) {
            list.add( toDto( lesson ) );
        }

        return list;
    }

    @Override
    public List<Lesson> toEntityList(List<LessonDto> lessonDtos) {
        if ( lessonDtos == null ) {
            return null;
        }

        List<Lesson> list = new ArrayList<Lesson>( lessonDtos.size() );
        for ( LessonDto lessonDto : lessonDtos ) {
            list.add( toEntity( lessonDto ) );
        }

        return list;
    }

    private Long lessonChapterId(Lesson lesson) {
        Chapter chapter = lesson.getChapter();
        if ( chapter == null ) {
            return null;
        }
        return chapter.getId();
    }

    protected Chapter lessonDtoToChapter(LessonDto lessonDto) {
        if ( lessonDto == null ) {
            return null;
        }

        Chapter.ChapterBuilder chapter = Chapter.builder();

        chapter.id( lessonDto.getChapterId() );

        return chapter.build();
    }
}
