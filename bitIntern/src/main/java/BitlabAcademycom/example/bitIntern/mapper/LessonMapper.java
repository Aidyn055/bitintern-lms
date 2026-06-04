package BitlabAcademycom.example.bitIntern.mapper;

import BitlabAcademycom.example.bitIntern.dto.LessonDto;
import BitlabAcademycom.example.bitIntern.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @Mapping(source = "chapter.id", target = "chapterId")
    LessonDto toDto(Lesson lesson);

    @Mapping(source = "chapterId", target = "chapter.id")
    Lesson toEntity(LessonDto lessonDto);

    List<LessonDto> toDtoList(List<Lesson> lessons);
    List<Lesson> toEntityList(List<LessonDto> lessonDtos);
}
