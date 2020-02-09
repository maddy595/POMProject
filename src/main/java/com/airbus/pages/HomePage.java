package com.airbus.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.airbus.pages.InputFormPage;
import com.airbus.base.BasePage;

public class HomePage extends BasePage{
	
	//Page Factory or Repository initialization
		@FindBy(xpath="//*[@id='site-name']/a/h1")
	    private WebElement PageTitle;

	    @FindBy(xpath="//*[@id='home']/h3/span")
	    private WebElement Demotest;

	    @FindBy(xpath="//*[@id='btn_basic_example']")
	    private WebElement InputFormLink;
	    
	    //Initializing Page Objects
	    public HomePage() {
	    	PageFactory.initElements(driver, this); //LoginPage.class	
	    }
	    
	    public String validateHomePageTitle() {
	    	return driver.getTitle();	
	    }
	    
	    public String validateDemoTab() {
	    	String s = Demotest.getText();
	    	return s;	
	    }

		public InputFormPage naivagtetoInputFormPage() {
			
		    	InputFormLink.click();
		    	return new InputFormPage();
		    }
		    

}
