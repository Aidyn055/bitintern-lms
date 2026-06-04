package BitlabAcademycom.example.bitIntern.service;

import BitlabAcademycom.example.bitIntern.dto.ChapterDto;

import java.util.List;

public interface ChapterService {
    List<ChapterDto> getAllChapters();
    ChapterDto getChapterById(Long id);
    ChapterDto createChapter(ChapterDto chapterDto);
    ChapterDto updateChapter(Long id, ChapterDto chapterDto);
    void deleteChapter(Long id);
}
