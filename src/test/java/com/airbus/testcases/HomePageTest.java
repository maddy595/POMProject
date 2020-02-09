package com.airbus.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.airbus.base.BasePage;
import com.airbus.pages.HomePage;
import com.airbus.pages.LoginPage;
import com.aventstack.extentreports.Status;

//The BasePage is implicitly initialzed when Object of LoginPage is created.

public class HomePageTest extends BasePage{
	LoginPage lp;
	HomePage hp;
	
	
	@BeforeMethod
	public void setup() {
		init();
		lp = new LoginPage();    //If you create a child object, the super constructor is (implicitly) called.
		hp = lp.naivagtetoHomePage();
	}
	
	@Test
	public void HomePagetitleTest() {
		test = extent.createTest("HomePagetitleTest");
		String title = hp.validateHomePageTitle();
		System.out.println(driver.getTitle());  //Check if driver is initialized
		System.out.println(prop.getProperty("browser")); //Check if properties is initialized
 		Assert.assertEquals(title, "Selenium Easy - Best Demo website to practice Selenium Webdriver Online");	
 		test.log(Status.INFO, "HomePagetitleTest validation");
	    test.log(Status.PASS, "HomePagetitleTest validation is succsffull");
	}
	
	@Test
	public void DemoulinkTest() {
		test = extent.createTest("DemoulinkTest");
		String title=hp.validateDemoTab();
		Assert.assertEquals(title, "DEMO");	
		test.log(Status.INFO, "DemoulinkTestvalidation");
	    test.log(Status.PASS, "DemoulinkTest validation is succsffull");
	}
	
	
	@Test
	public void InputFormNavigationTest() {
		test = extent.createTest("InputFormNavigationTest");
		hp.naivagtetoInputFormPage();
		test.log(Status.INFO, "InputFormNavigationTest validation");
	    test.log(Status.PASS, "InputFormNavigationTest validation is succsffull");
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
		extent.flush();
	}
	
	
	

}
