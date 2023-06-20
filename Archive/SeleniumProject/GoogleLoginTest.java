package Automation.SeleniumProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleLoginTest {

	WebDriver driver;

	@BeforeTest()
	public void BeforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
	}

	@Test
	public void LeaveTabTest() {
		SearchText("https://www.google.com/", "LeaveTabTest");
	}

	@Test
	public void RecruitmentTabTest() {
		SearchText("https://www.google.com/", "RecruitmentTabTest");
	}

	@Test
	public void PerformanceTabTest() {
		SearchText("https://www.google.com/", "PerformanceTabTest");
	}

	public void SearchText(String AppURL,String searchText) {
		try {
			// launch application URL
			driver.get(AppURL);
			driver.findElement(By.xpath("//*[@id=\"APjFqb\"]")).sendKeys(searchText);
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest()
	public void AfterTest() {
		driver.quit();
	}

}
