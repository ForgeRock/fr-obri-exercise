package com.forgerock.openbanking.exercise.tpp.onboarding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgerock.openbanking.exercise.tpp.api.registration.TPPRegistrationController;
import com.forgerock.openbanking.exercise.tpp.model.aspsp.AspspConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnBoardingApiTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Test
    public void contextLoads() {
    }

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    private String aspspConfigId = null;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document("{method-name}",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    public void onBoarding() throws Exception {
        MvcResult result = this.mockMvc.perform(
                post("/api/registration/aspsp")
                        .header("financial_id", "0015800001041REAAY")
                        .header("as_discovery_endpoint", "https://as.aspsp.ob.forgerock.financial/oauth2/")
                        .header("rs_discovery_endpoint", "https://rs.aspsp.ob.forgerock.financial/open-banking/discovery")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
        .andDo(
                document("on-boarding",
                        responseFields(
                                fieldWithPath("id").description("The internal ID of this on-boarding")
                        )
                )
        ).andReturn()
        ;
        TPPRegistrationController.RegistrationResponse registrationResponse = mapper.readValue(result.getResponse().getContentAsString(), TPPRegistrationController.RegistrationResponse.class);
        aspspConfigId = registrationResponse.getId();
    }

    @After
    public void unsetup() throws Exception {
        MockMvcBuilders.webAppContextSetup(this.context).build().perform(delete("/api/registration/aspsp/{aspspId}", aspspConfigId));
    }
}
