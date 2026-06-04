package BitlabAcademycom.example.bitIntern.controller;

import BitlabAcademycom.example.bitIntern.dto.LessonDto;
import BitlabAcademycom.example.bitIntern.service.LessonService;
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

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @Autowired
    private ObjectMapper objectMapper;

    private LessonDto lessonDto;

    @BeforeEach
    void setUp() {
        lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setName("Введение в Spring Boot");
        lessonDto.setContent("Spring Boot — это платформа...");
    }

    @Test
    void testGetAllLessons_Success() throws Exception {
        Mockito.when(lessonService.getAllLessons()).thenReturn(List.of(lessonDto));

        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Введение в Spring Boot"));
    }

    @Test
    void testGetLessonById_Success() throws Exception {
        Mockito.when(lessonService.getLessonById(1L)).thenReturn(lessonDto);

        mockMvc.perform(get("/api/lessons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Введение в Spring Boot"));
    }

    @Test
    void testCreateLesson_Success() throws Exception {
        Mockito.when(lessonService.createLesson(any(LessonDto.class))).thenReturn(lessonDto);

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonDto)))
                .andExpect(status().isCreated()) // 201 Created
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Введение в Spring Boot"));
    }

    @Test
    void testUpdateLesson_Success() throws Exception {
        Mockito.when(lessonService.updateLesson(eq(1L), any(LessonDto.class))).thenReturn(lessonDto);

        mockMvc.perform(put("/api/lessons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Введение в Spring Boot"));
    }

    @Test
    void testDeleteLesson_Success() throws Exception {
        Mockito.doNothing().when(lessonService).deleteLesson(1L);

        mockMvc.perform(delete("/api/lessons/{id}", 1L))
                .andExpect(status().isNoContent()); // 204 No Content
    }
}