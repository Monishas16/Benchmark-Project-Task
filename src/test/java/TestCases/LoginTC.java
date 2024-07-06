package TestCases;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.github.javafaker.Faker;
import com.google.common.io.Files;

import libraries.CommonFunctions;
import objects.LoginObj;
public class LoginTC {
	static String screenShot;
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static FileInputStream stream;
	public static ExtentSparkReporter report;
	public static ExtentReports reports;
	public static ExtentTest test;

	@BeforeSuite

	public void report() {
		report = new ExtentSparkReporter("Reports/Loginpage.html");
	
		//report = new ExtentSparkReporter("Reports/Recruitment.html");
		reports = new ExtentReports();
		reports.attachReporter(report);

		reports.setSystemInfo("<b>Application<b>", "<b>Benchmark Learning<b>");
		reports.setSystemInfo("<b>Module<b>", "<b>Monisha<\b>");
		reports.setSystemInfo("<b>Author<b>", "<b> Cordova Testing Team<b>");
					
		
	}
	@BeforeTest
	public void educore() throws IOException, InterruptedException {
	driver = new ChromeDriver();
	 
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	}
 

	@Test(priority = 1)
	public void loginverification_nims_dxb() throws Exception {
		test = reports.createTest("Testcase for Login Page ");
		
		PageFactory.initElements(driver,LoginObj.class);
		test.info("<b><font color = 'purple'>Test case-1 Verify the Dashborad page is displayed </b>");
		
		FileInputStream stream = new FileInputStream("config.properties");
		Properties properties = new Properties();
		properties.load(stream);
		
	
	
          String URL = properties.getProperty("url");
	        String[][] roles = {
	            {"admin", properties.getProperty("admin.username"), properties.getProperty("admin.password")},
	            {"hos", properties.getProperty("Hos.username"), properties.getProperty("admin.password")},
	            {"hod", properties.getProperty("Hod.username"), properties.getProperty("admin.password")},
	            {"teacher",properties.getProperty("Teacher.username"),properties.getProperty("admin.password")},
//	            {"principal", properties.getProperty("Principal.username"), properties.getProperty("admin.password")},

	           
	        };

	        for (String[] role : roles) {
	            test = reports.createTest("Testcase for Login Page - " + role[0]);
	            String username = role[1];
	            String password = role[2];

	            // Open the login page
	            driver.navigate().to(URL);
	            PageFactory.initElements(driver, LoginObj.class);
	            Thread.sleep(3000);

	            // Log in
	            LoginObj.username.sendKeys(username);
	            LoginObj.password.sendKeys(password);
	            LoginObj.sigin.click();
	            test.pass("Logged in as " + role[0] + " successfully");
	            Thread.sleep(3000);
	           
	//compare the dashboard
	            
	            String DashboardText=LoginObj.DashHead.getText();
	            if(DashboardText.equals("Dashboard")) {
	            	 test.pass(" Dashboard page is displayed");

	            } else {
		            test.fail("<font color='red'>Shift Schedular page is not displayed</font>",MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot(driver,screenShot)).build());
		            
	            	
	            }
	            
	            
	            String userHeadText = LoginObj.UserHead.getText();
	            if (userHeadText.equalsIgnoreCase(username)) {
	                test.pass("User header displays correct username for " + role[0]);
	            } else {
	                String screenshotPath = takeScreenshot(driver, role[0] + "_LoginFailure");
	                test.fail("User header does not display correct username for " + role[0],
	                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
	            }

	            
	            
	            // Log out
	            LoginObj.menubtn.click();
	            test.pass("Menu button clicked successfully for " + role[0]);
	            Thread.sleep(3000);
	            LoginObj.LogoutOpt.click();
	            test.pass("Logged out successfully for " + role[0]);
	        }
		
		
	}
	  @AfterSuite
		public void reportflush() {
			reports.flush();
		}  
	        public static String takeScreenshot(WebDriver driver, String screenshotName) throws Exception {
				String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				screenShot = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + timestamp + ".png";
				File screenshotfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshotfile, new File(screenShot));
				return screenShot;
	        
	        
	        
		

		}

		@AfterTest
		public void close() {
			driver.close();
		}	
	 
		
}
