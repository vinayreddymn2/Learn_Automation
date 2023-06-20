package RegressionTestCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCase4 {
	
	WebDriver driver;

	public void BeforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
	}

	@Test
	public void TestCaseMethod4() {
		BeforeTest();
		SearchText("https://www.google.com/", "TestCase4");
	}
	
	public void SearchText(String AppURL,String searchText) {
		try {
			// launch application URL
			driver.get(AppURL);
			driver.findElement(By.xpath("//*[@id=\"APjFqb\"]")).sendKeys(searchText);
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			Thread.sleep(2000);
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
