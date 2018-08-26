package com.forgerock.openbanking.exercise.tpp.api.account;

import com.forgerock.openbanking.exercise.tpp.services.aspsp.rs.RSAccountAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Controller
public class AccountsAPIController implements AccountsAPI {

    @Autowired
    private RSAccountAPIService rsAccountAPIService;

    @Override
    public ResponseEntity<String> readAccounts(
            @RequestParam(value = "aspspId") String aspspId,
            @RequestHeader(value = "accessToken") String accessToken) {
        //TODO: exercise: retrieve the aspsp configuration

        //TODO exercise: call the RS-ASPSP accounts endpoint via the service
        throw new NotImplementedException();
    }
}
