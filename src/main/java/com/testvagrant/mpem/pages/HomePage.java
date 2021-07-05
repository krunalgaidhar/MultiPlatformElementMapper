package com.testvagrant.mpem.pages;

import com.testvagrant.mpem.mapper.ElementLocatorMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(xpath = "oldLocator01")
    public WebElement element01;

    @FindBy(xpath = "oldLocator02")
    public WebElement element02;

    protected ElementLocatorMapper elementLocatorMapper = new ElementLocatorMapper();

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, elementLocatorMapper.map(this));
    }
}
