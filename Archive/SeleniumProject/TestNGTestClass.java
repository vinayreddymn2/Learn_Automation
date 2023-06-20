package Automation.SeleniumProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNGTestClass {
	WebDriver driver;
	
	@Test
	public void LeaveTabTest() {
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
		driver.findElement(By.xpath("//span[text()='Leave']")).click();
	}
	
	@Test
	public void RecruitmentTabTest() {
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
		driver.findElement(By.xpath("//span[text()='Recruitment']")).click();
	}
	
	@Test
	public void PerformanceTabTest() {
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
		driver.findElement(By.xpath("//span[text()='Performance']")).click();
	}
	
	
	public void ValidateTitle(String expectedTitle) {
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

}
