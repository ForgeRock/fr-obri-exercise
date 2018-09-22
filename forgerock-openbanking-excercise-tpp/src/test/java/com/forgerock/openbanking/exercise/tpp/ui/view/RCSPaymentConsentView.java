package com.forgerock.openbanking.exercise.tpp.ui.view;

import com.forgerock.openbanking.exercise.tpp.ui.SeleniumConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RCSPaymentConsentView {

    private SeleniumConfig config;

    public RCSPaymentConsentView(SeleniumConfig config) {
        this.config = config;
    }

    public void allow() {
        config.getDriver().findElement(By.xpath("//*[contains(text(), 'Allow')]")).click();
    }

    public void select(int accountIndex) {
        List<WebElement> li = config.getDriver().findElements(By.tagName("mat-radio-button"));
        li.get(accountIndex).click();
    }

}
