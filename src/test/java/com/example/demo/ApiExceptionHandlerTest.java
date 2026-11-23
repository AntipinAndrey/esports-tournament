package com.example.demo;

import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.controller.TeamController;
import com.example.demo.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TeamController.class)
@Import(com.example.demo.controller.ApiExceptionHandler.class)
class ApiExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;

    @Test
    void shouldReturnDetailed404ForMissingTeam() throws Exception {
        when(teamService.findByName("Missing"))
                .thenThrow(new ResourceNotFoundException(
                        "Team not found: Missing",
                        "Check teamName and use GET /api/teams."
                ));

        mockMvc.perform(get("/api/teams/Missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Ресурс не найден"))
                .andExpect(jsonPath("$.message")
                        .value("Team not found: Missing"))
                .andExpect(jsonPath("$.hint")
                        .value("Check teamName and use GET /api/teams."))
                .andExpect(jsonPath("$.path").value("/api/teams/Missing"));
    }

    @Test
    void shouldReturnDetailed409ForDuplicateTeam() throws Exception {
        when(teamService.create(any()))
                .thenThrow(new DuplicateResourceException(
                        "Team already exists: Falcons",
                        "Use another teamName."
                ));

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "teamName": "Falcons",
                                  "tag": "FLC",
                                  "country": "USA",
                                  "rating": 88
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Дубликат ресурса"))
                .andExpect(jsonPath("$.hint").value("Use another teamName."))
                .andExpect(jsonPath("$.path").value("/api/teams"));
    }

    @Test
    void shouldExplainValidationError() throws Exception {
        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Ошибка валидации данных"))
                .andExpect(jsonPath("$.hint").exists())
                .andExpect(jsonPath("$.path").value("/api/teams"));
    }

    @Test
    void shouldExplainMalformedJson() throws Exception {
        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teamName\":"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error")
                        .value("Невозможно прочитать тело запроса"))
                .andExpect(jsonPath("$.hint").exists());
    }
}
