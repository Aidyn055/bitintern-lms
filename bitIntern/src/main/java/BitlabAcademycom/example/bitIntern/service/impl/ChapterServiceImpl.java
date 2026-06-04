package BitlabAcademycom.example.bitIntern.service.impl;

import BitlabAcademycom.example.bitIntern.dto.ChapterDto;
import BitlabAcademycom.example.bitIntern.mapper.ChapterMapper;
import BitlabAcademycom.example.bitIntern.model.Chapter;
import BitlabAcademycom.example.bitIntern.repository.ChapterRepository;
import BitlabAcademycom.example.bitIntern.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public List<ChapterDto> getAllChapters() {
        log.info("Fetching all chapters from database");
        return chapterMapper.toDtoList(chapterRepository.findAll());
    }

    @Override
    public ChapterDto getChapterById(Long id) {
        log.info("Fetching chapter with id: {}", id);
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Глава с id " + id + " не найдена!"));
        log.debug("Successfully fetched chapter details: {}", chapter);
        return chapterMapper.toDto(chapter);
    }

    @Override
    public ChapterDto createChapter(ChapterDto chapterDto) {
        log.info("Creating new chapter"); // INFO: чистый текст действия
        log.debug("Payload for chapter creation: {}", chapterDto); // DEBUG: все данные объекта

        Chapter chapter = chapterMapper.toEntity(chapterDto);
        Chapter savedChapter = chapterRepository.save(chapter);

        log.info("Chapter successfully created with id: {}", savedChapter.getId());
        return chapterMapper.toDto(savedChapter);
    }

    @Override
    public ChapterDto updateChapter(Long id, ChapterDto chapterDto) {
        log.info("Updating chapter with id: {}", id);
        log.debug("New data payload for update: {}", chapterDto);

        Chapter existingChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Глава с id " + id + " не найдена для обновления!"));

        existingChapter.setName(chapterDto.getName());
        existingChapter.setOrder(chapterDto.getOrder());
        // Если у вас есть привязка к курсу, её тоже можно сетить здесь, например:
        // existingChapter.setCourse(chapterMapper.toEntity(chapterDto.getCourse()));

        Chapter updatedChapter = chapterRepository.save(existingChapter);
        log.info("Chapter with id: {} successfully updated", updatedChapter.getId());

        return chapterMapper.toDto(updatedChapter);
    }

    @Override
    public void deleteChapter(Long id) {
        log.info("Deleting chapter with id: {}", id);

        if (!chapterRepository.existsById(id)) {
            throw new IllegalArgumentException("Невозможно удалить! Глава с id " + id + " не найдена.");
        }

        chapterRepository.deleteById(id);
        log.info("Chapter with id: {} successfully deleted", id);
    }
}
