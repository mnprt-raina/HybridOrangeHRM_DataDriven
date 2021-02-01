package logintest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.dom4j.io.SAXReader;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class LoginTest extends Reusables
{
	InputStream is;

	String browser;
	
	@BeforeSuite
	void loadInitialFiles() throws FileNotFoundException
	{
		try
		{
			report = new ExtentReports();
			ExtentHtmlReporter avent = new ExtentHtmlReporter(System.getProperty("user.dir")+"/er.html");
			report.attachReporter(avent);

			//load OR
			InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/main/resources/OR.xml");								
			SAXReader saxReader = new SAXReader();					
			document = saxReader.read(is);		
		}

		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@DataProvider(name = "Authentication")
	public Object[][] Authentication() 
	{
		Object[][] testObjArray = null;
		try 
		{
			testObjArray = getTableArray("C:\\Users\\leo\\eclipse-workspace\\w2\\HybridOrangeHRM\\src\\main\\java\\testdata\\TestData.xlsx","mysheet");

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return (testObjArray);
	}

	@BeforeTest
	@Parameters("browser")
	public void setup(String browser)
	{
		try
		{
			this.browser = browser;
			if(browser.equalsIgnoreCase("firefox"))
			{

				System.setProperty("webdriver.gecko.driver", "C:\\Users\\leo\\Downloads\\geckodriver.exe");
				driver = new FirefoxDriver();
			}

			else if(browser.equalsIgnoreCase("chrome"))
			{

				System.setProperty("webdriver.chrome.driver", "C:\\Users\\leo\\Downloads\\chromedriver.exe");
				driver = new ChromeDriver();

			}
			else
			{
				System.out.println("Browser is not correct");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}


	@BeforeMethod
	void invokeBrowser()
	{
		driver.manage().window().maximize();
		driver.get("https://s2.demo.opensourcecms.com/orangehrm/symfony/web/index.php/auth/login");
	}

	@Test(dataProvider = "Authentication")
	public void loginHRM(String usrname, String pswd)
	{
		try 
		{
			test = report.createTest("loginHRM with :"+usrname +" and "+pswd);
			test.assignCategory(this.browser);
			test.log(Status.INFO, "starting loginHRM with :"+usrname +" and "+pswd);
			getWebElementAndSendKeys(usrname, "//menu/username");
			getWebElementAndSendKeys(pswd, "//menu/password");
			getWebElement("//menu/login");
			test.log(Status.INFO, "passed loginHRM with :"+usrname +" and "+pswd);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	//added-RK
	@Test(dataProvider = "Authentication")
	public void loginHRM2(String usrname, String pswd)
	{
		try 
		{
			test = report.createTest("loginHRM2 with :"+usrname +" and "+pswd);
			test.assignCategory(this.browser);
			test.log(Status.INFO, "starting loginHRM2 with :"+usrname +" and "+pswd);
			getWebElementAndSendKeys(usrname, "//menu/username");
			getWebElementAndSendKeys(pswd, "//menu/password");
			getWebElement("//menu/login");
			test.log(Status.PASS, "passed loginHRM2 with :"+usrname +" and "+pswd);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@AfterTest
	void quitBrowser()
	{
		test.log(Status.INFO, "Closing the driver");
//		driver.close();
	}

	@AfterSuite
	void generateReport() {
		test.log(Status.INFO, "quitting the driver");
		driver.quit();
		report.flush();
	}
}
