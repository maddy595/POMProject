package com.airbus.testcases;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.airbus.base.BasePage;
import com.airbus.pages.HomePage;
import com.airbus.pages.LoginPage;
import com.aventstack.extentreports.Status;

//The BasePage is implicitly initialzed when Object of LoginPage is created.

public class LoginPageTest extends BasePage{
	LoginPage lp;
	HomePage hp;
	Logger log = Logger.getLogger(LoginPageTest.class);
	
	@BeforeMethod
	public void setup() {
		init();
		lp = new LoginPage();
		log.debug("Log mesages");
		log.info("nInfo logs");
		
	}
	
	@Test
	public void LoginPagetitleTest() {
		test = extent.createTest("Login Page Test");
		
		log.debug("Log mesages");
		log.info("nInfo logs");
		String title = lp.validateLoginPageTitle();
		System.out.println(driver.getTitle());
		System.out.println(prop.getProperty("browser"));
		Assert.assertEquals(title, "Learn Selenium with Best Practices and Examples | Selenium Easy");
		test.log(Status.INFO, "Login Selenium Easy");
	    test.log(Status.PASS, "Login successfull to loginPage");
	    
	}
	
	
	@Test
	public void HomePagevalidationTest() {
		test = extent.createTest("HomePagevalidationTest");
		log.debug("Log mesages");
		log.info("nInfo logs");
		
		String hometabtext = lp.validatehomeTab();
		Assert.assertEquals(hometabtext, "Home");
		test.log(Status.INFO, "Home Page validation");
	    test.log(Status.FAIL, "Home Page validation is succsffull");
	}
	
	@Test
	public void LoginTest() {
		test = extent.createTest("LoginTest");
		hp = lp.naivagtetoHomePage();	
		test.log(Status.INFO, "Login Page validation");
	    test.log(Status.FAIL, "Login Page validation is succsffull");
	}

	
	@AfterMethod
	public void teardown(ITestResult result) {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			String ScreenshotPath = takeScreenshot();
			test.log(Status.FAIL , result.getMethod().getMethodName());
			System.out.println(result.getMethod().getMethodName());
            try {
				test.log(Status.FAIL, "Snapshot below: " + test.addScreenCaptureFromPath(ScreenshotPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		driver.quit();
	}
	
	@AfterTest
    public void endreport(){
		extent.flush();
		
    }
	
	
	
	
	

}
