package Automation.SeleniumProject;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
	static WebDriver driver;
	@Test
	public void mainmethod() {
		
		try {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
			//launch application URL
			driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
			
			//Verify whether login page available
			ValidateTitle("OrangeHRM");
			//Login to application
			driver.findElement(By.name("username")).sendKeys("Admin");
			driver.findElement(By.name("password")).sendKeys("admin123");
			//click on login
			driver.findElement(By.xpath("//button[text()=' Login ']")).click();
			//verify whether login successfully
			ValidateTitle("OrangeHRM");
			
			//Navigated to 	 menu
			//driver.findElement(By.xpath("//*[text()='Employee Name']/../..//input")).clear()
			SelectFilterWeblist(driver.findElement(By.xpath("//*[text()='Employee Name']/../..//input")), "Peter Mac Anderson");
			WebElement firstName=driver.findElement(By.name("firstName"));
			ClearAndEnterText(firstName, "firstName");
			WebElement middleName=driver.findElement(By.name("middleName"));
			ClearAndEnterText(middleName, "middleName");
			WebElement lastName=driver.findElement(By.name("lastName"));
			ClearAndEnterText(lastName, "lastName");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void ClearAndEnterText(WebElement wbe,String strValue)
	{
		wbe.click();
		wbe.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		wbe.sendKeys(Keys.BACK_SPACE);
		wbe.sendKeys(strValue);
	}
	
	public static void ValidateTitle(String expectedTitle) {
		try {
			String ActualTitle=driver.getTitle();
			if(ActualTitle.equals(expectedTitle))
			{
				System.out.println("Expected Page Found "+ActualTitle);
			}else {
				System.out.println("Incorrect Page Found"+"Actual Title : "+ActualTitle+"Expected Title : "+expectedTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void SelectDropdown(WebElement wbe,String strValue)
	{
		try {
			//clicking on dropdown list
			wbe.click();
			List<WebElement> options=driver.findElements(By.xpath("//*[@role='option']"));

			for(WebElement option : options)
			{
				System.out.println("Drop down values"+option.getText());
				if(option.getText().equals(strValue)) {
					option.click();
					return;
				}
			}
			System.out.println("Option Not available");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void SelectFilterWeblist(WebElement wbe,String strValue)
	{
		try {
			//clicking on dropdown list
			wbe.click();
			Thread.sleep(5000);
			wbe.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			wbe.sendKeys(Keys.BACK_SPACE);
			wbe.sendKeys(strValue);//Peter Mac Anderson
			Thread.sleep(5000);
			//driver.findElement(By.xpath("//*[@role='option']")).click();
			List<WebElement> options=driver.findElements(By.xpath("//*[@role='option']"));
			for(WebElement option : options)
			{
				if(option.getText().equals(strValue)) {
					option.click();
					return;
				}
			}
			System.out.println("Option Not available");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
