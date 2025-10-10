package io.hexlet.cv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.dto.pagesection.PageSectionCreateDTO;
import io.hexlet.cv.dto.pagesection.PageSectionUpdateDTO;
import io.hexlet.cv.mapper.PageSectionMapper;
import io.hexlet.cv.model.PageSection;
import io.hexlet.cv.repository.PageSectionRepository;
import io.hexlet.cv.utils.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PageSectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PageSectionRepository pageSectionRepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PageSectionMapper pageSectionMapper;

    @Autowired
    private ModelGenerator modelGenerator;

    private PageSection section1;
    private PageSection section2;

    @BeforeEach
    public void setUp() {

        pageSectionRepository.deleteAll();

        section1 = Instancio.of(modelGenerator.getPageSectionModel()).create();
        section2 = Instancio.of(modelGenerator.getPageSectionModel()).create();

        section1.setPageKey("main");
        section2.setPageKey("profile");

        section2.setActive(false);

        pageSectionRepository.save(section1);
        pageSectionRepository.save(section2);
    }

    @Test
    public void testGetAll() throws Exception {

        var response = mockMvc.perform(get("/api/pages/sections"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString())
            .contains(section1.getPageKey())
            .contains(section2.getPageKey())
            .contains(section1.getSectionKey())
            .contains(section2.getSectionKey());
    }

    @Test
    public void testGetAllWithParams() throws Exception {

        var section3 = Instancio.of(modelGenerator.getPageSectionModel()).create();
        section3.setPageKey(section1.getPageKey());
        section3.setActive(section2.isActive());
        pageSectionRepository.save(section3);

        var response = mockMvc.perform(get("/api/pages/sections")
                .param("page", section1.getPageKey())
                .param("active", String.valueOf(section2.isActive())))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString())
            .doesNotContain(section1.getSectionKey())
            .doesNotContain(section2.getSectionKey())
            .contains(section3.getSectionKey());
    }

    @Test
    public void testGet() throws Exception {

        var response = mockMvc.perform(get("/api/pages/sections/" + section1.getId()))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThatJson(response.getContentAsString()).and(
            v -> v.node("id").isEqualTo(section1.getId()),
            v -> v.node("pageKey").isEqualTo(section1.getPageKey()),
            v -> v.node("sectionKey").isEqualTo(section1.getSectionKey())
        );
    }

    @Test
    public void testCreate() throws Exception {

        pageSectionRepository.delete(section1);

        var dto = new PageSectionCreateDTO();
        dto.setPageKey(section1.getPageKey());
        dto.setSectionKey(section1.getSectionKey());
        dto.setTitle(section1.getTitle());
        dto.setContent(section1.getContent());
        dto.setActive(section1.isActive());

        var response = mockMvc.perform(post("/api/pages/sections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
            .andExpect(status().isCreated());

        var section = assertDoesNotThrow(() ->
            pageSectionRepository.findBySectionKey(dto.getSectionKey()).orElseThrow());

        assertThat(section.getPageKey()).isEqualTo(dto.getPageKey());
        assertThat(section.getTitle()).isEqualTo(dto.getTitle());
        assertThat(section.getContent()).isEqualTo(dto.getContent());
        assertThat(section.isActive()).isEqualTo(dto.getActive());
    }

    @Test
    public void testUpdate() throws Exception {

        var dto = new PageSectionUpdateDTO();
        dto.setPageKey(JsonNullable.of("profile"));
        dto.setSectionKey(JsonNullable.of("tech_stack"));
        dto.setActive(JsonNullable.of(false));

        dto.setTitle(JsonNullable.undefined());
        dto.setContent(JsonNullable.undefined());

        var oldTitle = section1.getTitle();
        var oldContent = section1.getContent();

        var response = mockMvc.perform(put("/api/pages/sections/" + section1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
            .andExpect(status().isOk());

        var section = assertDoesNotThrow(() ->
            pageSectionRepository.findById(section1.getId()).orElseThrow());

        assertThat(section.getPageKey()).isEqualTo(dto.getPageKey().get());
        assertThat(section.getSectionKey()).isEqualTo(dto.getSectionKey().get());
        assertThat(section.isActive()).isEqualTo(dto.getActive().get());

        assertThat(section.getTitle()).isEqualTo(oldTitle);
        assertThat(section.getContent()).isEqualTo(oldContent);
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/api/pages/sections/" + section1.getId()))
            .andExpect(status().isNoContent());

        assertThat(pageSectionRepository.existsById(section1.getId())).isFalse();
        assertThat(pageSectionRepository.existsById(section2.getId())).isTrue();
    }
}
