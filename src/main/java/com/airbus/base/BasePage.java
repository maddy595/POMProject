package com.airbus.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.airbus.util.WebDriverActionLogs;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class BasePage {
	
	public static Properties prop;
	public static WebDriver driver;
	public static ExtentTest test;
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extent;
	
	public BasePage() {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File ("C:\\Users\\madhav.gaikwad\\eclipse-workspace2\\POMSample\\src\\main\\java\\com\\airbus\\base\\config.properties")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(extent==null) {
		reporter=new ExtentHtmlReporter("C:\\Users\\madhav.gaikwad\\eclipse-workspace2\\POMSample\\ExtentReports.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		}
	}
	
	public static void init() {
		if(prop.getProperty("browser").equals("chrome")){

			System.setProperty("webdriver.chrome.driver", "C:\\Selenium Files\\chromedriver.exe");
	                driver=new ChromeDriver();
	                
			}
		
		EventFiringWebDriver efd = new EventFiringWebDriver(driver);
		WebDriverActionLogs wda = new WebDriverActionLogs();
		efd.register(wda);
		driver = efd;
		driver.get("https://www.seleniumeasy.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
	}
	
	
	
	public void navigate(String url) {
		try {
			driver.get(prop.getProperty(url));
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
		
		public void type(String xpathEleKey , String email) {
			getElement(xpathEleKey).sendKeys(email);
			
		}
		
		public void click(String xpathEleKey) {
				getElement(xpathEleKey).click();
			}
		
		public WebElement getElement(String locatorKey) {
			WebElement e = null;
			try {
				e= driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			}catch(Exception ex) {
				reportfailure(ex.getMessage());
				ex.printStackTrace();
			}
			return e;
			
		}

		/************************Validations***********************/
		
		public String getTitle() {
			try {
				String title = driver.getTitle();
				
				return title;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		public void verifyPageTitle(String ExpectedMessage) {
			WebDriverWait wait = new WebDriverWait(driver, 10);

			String PageTitle=driver.getTitle();
			if(PageTitle.contains(ExpectedMessage)) {
				test.pass("Title verified successfully");
			}else {
				test.fail("Title not verified successfully");
			}
		}
		
		public boolean verify(WebElement elem, String ExpectedText) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.invisibilityOfAllElements(elem));
			String ElementText=elem.getText();
			if(ElementText.contains(ExpectedText)) {
				test.pass("Verification successfully completed");
				return true;
			}else {
				test.fail("Verification failed");
				Assert.fail("Expected text is not displayed");
				return false;
			}
		}
		
		public void waitfor(int time) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void IsLoginSuccessful(String ExpectedText) {
			String PageTitle = driver.getTitle();
			System.out.println("TItle is -> " +PageTitle);
			List<WebElement> ele = driver.findElements(By.xpath("//*[@id='error']"));
			if(PageTitle.contains(ExpectedText)) {
				test.pass("Title verified successfully");
				System.out.println("Login is Successful");
			}else if(ele.size()>0) {
				String errorMessage = driver.findElement(By.xpath("//*[@id='error']")).getText();
				if(errorMessage.contains("Please check your username and password")) {
					System.out.println("Unable to login as username or password is incorrect");
					Assert.fail();
				}
			}else {
				System.out.println("Unable to login to Application");
				Assert.fail();
			}
		}
		
		public boolean isElementPresent(String locatorKey) {
			List<WebElement> elementlist = null;
			try {
				elementlist= driver.findElements(By.xpath(prop.getProperty(locatorKey)));
			if(elementlist.size()==0)
				return false;
			else
				return true;
			}catch(Exception ex) {
				ex.printStackTrace();
				Assert.fail();
			}
			return false;
			
		}
		
		public void verifyText(String locatorKey,String ExpectedText) {
			String actualText=getElement(locatorKey).getText().trim();
			if (actualText.contains(ExpectedText)) {
				test.pass("Org verified successfully");
			}else {
			test.log(Status.FAIL, "Org verification failed");
			reportfailure("Failed");
			test.fail("Org verification failed");
			}
		}

	    /****************************reporting************************/
		
		public void reportpass(String msg) {
		
			test.log(Status.PASS, msg);
		}
		
		public void reportfailure(String msg) {
			test.log(Status.FAIL, msg);
			takeScreenshot();
			//This line will stop the test case from executing
			Assert.fail();
		}
		
		public String takeScreenshot() {
			String destPath = null;
			TakesScreenshot src = (TakesScreenshot)driver;
			File srcfile = src.getScreenshotAs(OutputType.FILE);
			Date d = new Date();
			String filename= d.toString().replaceAll(":", "_").replaceAll(" ", "_")+".jpg";
			try {
			destPath = 	System.getProperty("user.dir")+"//Screenshot//"+filename;
			FileHandler.copy(srcfile, new File(destPath));
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			//put screenshot file in reports
			return destPath;
		}
		
		
		public void screenshotwithName() { //Challange - File in destination is replaced EveryTime
			File Dest;
			Date d = new Date();
			System.out.println(d);
			SimpleDateFormat sdf = new SimpleDateFormat("MM_DD_YYYY_HH_mm");
			String s = sdf.format(d);
			TakesScreenshot tks = (TakesScreenshot)driver;
			File source = tks.getScreenshotAs(OutputType.FILE);
			String methodName= Thread.currentThread().getStackTrace()[1].getMethodName();
			System.out.println(methodName);
			//String destFile = SalesforceConstants.ScreenshotFilePath+"\\"+methodName+s+".JPG";
			String destFile="";
			System.out.println(destFile);
			Dest = new File(destFile);
			try {
				FileHandler.copy(source, Dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
	
	
	
}
