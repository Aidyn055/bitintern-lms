package BitlabAcademycom.example.bitIntern.service.impl;

import BitlabAcademycom.example.bitIntern.dto.ChapterDto;
import BitlabAcademycom.example.bitIntern.mapper.ChapterMapper;
import BitlabAcademycom.example.bitIntern.model.Chapter;
import BitlabAcademycom.example.bitIntern.repository.ChapterRepository;
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
class ChapterServiceImplTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    private Chapter chapterEntity;
    private ChapterDto chapterDto;

    @BeforeEach
    void setUp() {
        chapterDto = new ChapterDto();
        chapterDto.setId(1L);
        chapterDto.setName("Spring Data JPA");
        chapterDto.setOrder(2);

        chapterEntity = new Chapter();
        chapterEntity.setId(1L);
        chapterEntity.setName("Spring Data JPA");
        chapterEntity.setOrder(2);
    }

    @Test
    void testGetAllChapters_Success() {
        Mockito.when(chapterRepository.findAll()).thenReturn(List.of(chapterEntity));
        Mockito.when(chapterMapper.toDtoList(anyList())).thenReturn(List.of(chapterDto));

        List<ChapterDto> result = chapterService.getAllChapters();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spring Data JPA", result.get(0).getName());
        Mockito.verify(chapterRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetChapterById_Success() {
        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapterEntity));
        Mockito.when(chapterMapper.toDto(chapterEntity)).thenReturn(chapterDto);

        ChapterDto result = chapterService.getChapterById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        Mockito.verify(chapterRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetChapterById_NotFound() {
        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                chapterService.getChapterById(1L)
        );

        assertEquals("Глава с id 1 не найдена!", exception.getMessage());
    }

    @Test
    void testCreateChapter_Success() {
        Mockito.when(chapterMapper.toEntity(any(ChapterDto.class))).thenReturn(chapterEntity);
        Mockito.when(chapterRepository.save(any(Chapter.class))).thenReturn(chapterEntity);
        Mockito.when(chapterMapper.toDto(any(Chapter.class))).thenReturn(chapterDto);

        ChapterDto result = chapterService.createChapter(chapterDto);

        assertNotNull(result);
        assertEquals("Spring Data JPA", result.getName());
        Mockito.verify(chapterRepository, Mockito.times(1)).save(any(Chapter.class));
    }

    @Test
    void testUpdateChapter_Success() {
        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapterEntity));
        Mockito.when(chapterRepository.save(any(Chapter.class))).thenReturn(chapterEntity);
        Mockito.when(chapterMapper.toDto(any(Chapter.class))).thenReturn(chapterDto);

        ChapterDto result = chapterService.updateChapter(1L, chapterDto);

        assertNotNull(result);
        assertEquals("Spring Data JPA", result.getName());
        Mockito.verify(chapterRepository, Mockito.times(1)).save(any(Chapter.class));
    }

    @Test
    void testDeleteChapter_Success() {
        // Твой код делает строгую проверку if (!chapterRepository.existsById(id))
        Mockito.when(chapterRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(chapterRepository).deleteById(1L);

        assertDoesNotThrow(() -> chapterService.deleteChapter(1L));

        Mockito.verify(chapterRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteChapter_NotFound() {
        Mockito.when(chapterRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                chapterService.deleteChapter(1L)
        );

        assertEquals("Невозможно удалить! Глава с id 1 не найдена.", exception.getMessage());
    }
}