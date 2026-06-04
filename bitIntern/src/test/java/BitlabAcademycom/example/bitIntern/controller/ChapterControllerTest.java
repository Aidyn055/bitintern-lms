package BitlabAcademycom.example.bitIntern.controller;

import BitlabAcademycom.example.bitIntern.dto.ChapterDto;
import BitlabAcademycom.example.bitIntern.service.ChapterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChapterController.class)
class ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChapterService chapterService;

    @Autowired
    private ObjectMapper objectMapper;

    private ChapterDto chapterDto;

    @BeforeEach
    void setUp() {
        chapterDto = new ChapterDto();
        chapterDto.setId(1L);
        chapterDto.setName("Spring Data JPA");
    }

    @Test
    void testGetAllChapters_Success() throws Exception {
        Mockito.when(chapterService.getAllChapters()).thenReturn(List.of(chapterDto));

        mockMvc.perform(get("/api/chapters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Spring Data JPA"));
    }

    @Test
    void testGetChapterById_Success() throws Exception {
        Mockito.when(chapterService.getChapterById(1L)).thenReturn(chapterDto);

        mockMvc.perform(get("/api/chapters/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Spring Data JPA"));
    }

    @Test
    void testCreateChapter_Success() throws Exception {
        Mockito.when(chapterService.createChapter(any(ChapterDto.class))).thenReturn(chapterDto);

        mockMvc.perform(post("/api/chapters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapterDto)))
                .andExpect(status().isCreated()) // Ждем 201 CREATED
                .andExpect(jsonPath("$.name").value("Spring Data JPA"));
    }

    @Test
    void testUpdateChapter_Success() throws Exception {
        Mockito.when(chapterService.updateChapter(eq(1L), any(ChapterDto.class))).thenReturn(chapterDto);

        mockMvc.perform(put("/api/chapters/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Data JPA"));
    }

    @Test
    void testDeleteChapter_Success() throws Exception {
        Mockito.doNothing().when(chapterService).deleteChapter(1L);

        mockMvc.perform(delete("/api/chapters/{id}", 1L))
                .andExpect(status().isNoContent()); // Твой контроллер возвращает noContent() (204)
    }
}