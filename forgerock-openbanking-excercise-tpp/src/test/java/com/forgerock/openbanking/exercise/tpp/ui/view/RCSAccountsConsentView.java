package com.forgerock.openbanking.exercise.tpp.ui.view;

import com.forgerock.openbanking.exercise.tpp.ui.SeleniumConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RCSAccountsConsentView {

    private SeleniumConfig config;

    public RCSAccountsConsentView(SeleniumConfig config) {
        this.config = config;
    }

    public void allow() {
        config.getDriver().findElement(By.className("allow-answer")).click();
    }

    public void submit() {
        config.getDriver().findElements(By.className("btn-primary")).get(0).click();
    }

}
