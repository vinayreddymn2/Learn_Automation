package RegressionTestCases;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCase1 {
	
	WebDriver driver;

	
	public void BeforeTest() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
//		options.addArguments("incognito");
//		options.addArguments("version");
		options.addArguments("--headless=new");
		
		driver = new ChromeDriver(options);
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
	}
	
	@DataProvider (name="ourSampleInputData")
	public Object[][] dpmethod()
	{
		return new Object[][] {
			{1,"TestCase1","https://www.google.com/"}
			};
	}

	@Test (dataProvider = "ourSampleInputData")
	public void TestCaseMethod1(int i,String val,String val2) {
		BeforeTest();
		SearchText(val2, val);
	}
		
	public void SearchText(String AppURL,String searchText) {
		try {
			// launch application URL
			driver.get(AppURL);
			driver.findElement(By.xpath("//*[@id=\"APjFqb\"]")).sendKeys(searchText);
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File destinationpath=new File("C:\\Users\\vinay\\Downloads\\googlesearch.png");
			FileUtils.copyFile(file, destinationpath);
			Thread.sleep(2000);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
