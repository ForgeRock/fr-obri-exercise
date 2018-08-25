package com.forgerock.openbanking.exercise.tpp.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgerock.openbanking.exercise.tpp.api.registration.TPPRegistrationController;
import com.forgerock.openbanking.exercise.tpp.configuration.TppConfiguration;
import com.forgerock.openbanking.exercise.tpp.ui.view.AMLoginView;
import com.forgerock.openbanking.exercise.tpp.ui.view.RCSPaymentConsentView;
import com.forgerock.openbanking.exercise.tpp.ui.SeleniumConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import uk.org.openbanking.datamodel.payment.OBTransactionIndividualStatus1Code;
import uk.org.openbanking.datamodel.payment.paymentsubmission.OBPaymentSubmissionResponse1;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentApiTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Test
    public void contextLoads() {}

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TppConfiguration tppConfiguration;

    private MockMvc mockMvcForDocs;
    private MockMvc mockMvcForSettingUpTest;

    private String aspspConfigId = null;
    private SeleniumConfig config;

    @Before
    public void setUp() throws Exception {
        this.mockMvcForSettingUpTest = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.mockMvcForDocs = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document("{ClassName}/{method-name}/{step}",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
        tppConfiguration.setPispRedirectUri("https://localhost");

        MvcResult result =  this.mockMvcForSettingUpTest.perform(
                post("/api/registration/aspsp")
                        .header("financial_id", "0015800001041REAAY")
                        .header("as_discovery_endpoint", "https://as.aspsp.integ-ob.forgerock.financial/oauth2/")
                        .header("rs_discovery_endpoint", "https://rs.aspsp.integ-ob.forgerock.financial/open-banking/discovery")
        )
                .andReturn();
        TPPRegistrationController.RegistrationResponse registrationResponse = mapper.readValue(result.getResponse().getContentAsString(), TPPRegistrationController.RegistrationResponse.class);
        aspspConfigId = registrationResponse.getId();
        this.config = new SeleniumConfig();
    }

    @Test
    public void initiatePayment() throws Exception {
        String redirectedUrl = this.mockMvcForDocs.perform(
                post("/api/open-banking/payment-requests/initiate")
                        .param("aspspId", aspspConfigId)
        )
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andReturn().getResponse().getContentAsString();

        AMLoginView amLoginView = new AMLoginView(config);
        amLoginView.navigate(redirectedUrl);
        amLoginView.login("demo", "changeit");
        RCSPaymentConsentView paymentConsentView = new RCSPaymentConsentView(config);

        paymentConsentView.allow();
        paymentConsentView.submit(0);

        //Simulate the javascript redirect, as we are limited by what we can simulate
        MultiValueMap<String, String> queryMap = getQueryMap(new URI(config.getDriver().getCurrentUrl()).getFragment());
        String obPaymentSubmissionResponseSerialised = this.mockMvcForDocs.perform(
                get("/api/open-banking/payment-requests/exchange_code")
                        .params(queryMap)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        OBPaymentSubmissionResponse1 obPaymentSubmissionResponse = mapper.readValue(obPaymentSubmissionResponseSerialised, OBPaymentSubmissionResponse1.class);

        assertThat(obPaymentSubmissionResponse.getData().getStatus()).isEqualTo(OBTransactionIndividualStatus1Code.ACCEPTED_SETTLEMENT_IN_PROCESS);
    }

    @After
    public void unsetup() throws Exception {
        mockMvcForSettingUpTest.perform(delete("/api/registration/aspsp/{aspspId}", aspspConfigId));
        config.getDriver().close();
    }

    public static MultiValueMap<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.add(name, value);
        }
        return map;
    }
}
