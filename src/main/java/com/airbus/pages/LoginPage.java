package com.airbus.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.airbus.base.BasePage;

public class LoginPage extends BasePage{
	//https://www.seleniumeasy.com/
	
	//Page Factory or Repository initialization
	@FindBy(xpath="//*[@id='site-name']/a/h1")
    private WebElement PageTitle;

    @FindBy(xpath="//*[@id='block-block-57']/div/div/a")
    private WebElement DemoSiteurl;

    @FindBy(xpath="//*[@id='navbar-collapse']/nav/ul/li[1]/a")
    private WebElement Hometab;
    
    //Initializing Page Objects
    public LoginPage() {
    	PageFactory.initElements(driver, this); //LoginPage.class	
    }
    
    public String validateLoginPageTitle() {
    	return driver.getTitle();	
    }
    
    public String validatehomeTab() {
    	String s = Hometab.getText();
    	return s;	
    }
    
    public HomePage naivagtetoHomePage() {
    	DemoSiteurl.click();
		
    	return new HomePage();
    }
    
    
}
