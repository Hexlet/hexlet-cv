package io.hexlet.cv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.mapper.PageSectionMapper;
import io.hexlet.cv.model.PageSection;
import io.hexlet.cv.repository.PageSectionRepository;
import io.hexlet.utils.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainPageControllerTest {

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

    private final String PAGE_KEY = "main";

    @BeforeEach
    public void setUp() {

        pageSectionRepository.deleteAll();

        section1 = Instancio.of(modelGenerator.getPageSectionModel()).create();
        section2 = Instancio.of(modelGenerator.getPageSectionModel()).create();

        section1.setPageKey(PAGE_KEY);
        section2.setPageKey(PAGE_KEY);

        pageSectionRepository.save(section1);
        pageSectionRepository.save(section2);
    }

    @Test
    public void testIndex() throws Exception {

        var response = mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

        assertThat(response.getContentAsString())
            .contains(PAGE_KEY)
            .contains(section1.getSectionKey())
            .contains(section2.getSectionKey());
    }
}
