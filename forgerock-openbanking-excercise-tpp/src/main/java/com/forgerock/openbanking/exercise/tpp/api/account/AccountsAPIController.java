package com.forgerock.openbanking.exercise.tpp.api.account;

import com.forgerock.openbanking.exercise.tpp.model.aspsp.AspspConfiguration;
import com.forgerock.openbanking.exercise.tpp.repository.AspspConfigurationRepository;
import com.forgerock.openbanking.exercise.tpp.services.aspsp.rs.RSAccountAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import uk.org.openbanking.datamodel.account.OBReadAccount2;

import java.util.Optional;

@Controller
public class AccountsAPIController implements AccountsAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsAPIController.class);

    @Autowired
    private RSAccountAPIService rsAccountAPIService;
    @Autowired
    private AspspConfigurationRepository aspspConfigurationRepository;

    @Override
    public ResponseEntity readAccounts(
            @RequestParam(value = "aspspId") String aspspId,
            @RequestHeader(value = "accessToken") String accessToken) {

        Optional<AspspConfiguration> configuration = aspspConfigurationRepository.findById(aspspId);

        if (!configuration.isPresent()) {
            LOGGER.error("Could not retrieve the AspspConfiguration accounts for id " + aspspId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        OBReadAccount2 obReadAccount2 = null;
        try {
            obReadAccount2 = rsAccountAPIService.readAccounts(configuration.get(), accessToken);
        } catch (Exception e) {
            LOGGER.error("Could not retrieve the RS Accounts for id " + aspspId + " and access token " + accessToken, e);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(obReadAccount2, HttpStatus.OK);
    }
}
