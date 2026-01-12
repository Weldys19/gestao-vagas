package com.br.weldyscarmo.gestao_vagas.modules.company.controllers;

import com.br.weldyscarmo.gestao_vagas.exceptions.CompanyNotFoundException;
import com.br.weldyscarmo.gestao_vagas.exceptions.UserNotFoundException;
import com.br.weldyscarmo.gestao_vagas.modules.company.dto.CreateJobDTO;
import com.br.weldyscarmo.gestao_vagas.modules.company.entities.CompanyEntity;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.br.weldyscarmo.gestao_vagas.modules.company.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;


import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldBeAbleToCreateAJob() throws Exception {

        var company = CompanyEntity.builder()
                .name("Company")
                .username("CompanyUsername")
                .email("company@gmail.com")
                .description("Vagas Junior")
                .password("1234567890")
                .build();

        var createdJobDTO = CreateJobDTO.builder()
                .description("Description")
                .level("Junior")
                .benefits("Benefits")
                .build();

        company = companyRepository.saveAndFlush(company);

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdJobDTO))
                        .header("Authorization", "Bearer " + TestUtils.generatedToken(company.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateAJob() throws Exception {
        var createdJobDTO = CreateJobDTO.builder()
                .description("Description")
                .level("Junior")
                .benefits("Benefits")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdJobDTO))
                .header("Authorization", "Bearer " + TestUtils.generatedToken(UUID.randomUUID())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
