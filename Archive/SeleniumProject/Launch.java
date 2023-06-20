package Automation.SeleniumProject;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Launch {

	public static void main(String[] args) {
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\vinay\\Downloads\\chromedriver_win32\\chromedriver.exe");

		try {
			
			WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
			driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
			
//			 Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
//				        .withTimeout(Duration.ofSeconds(30))
//				        .pollingEvery(Duration.ofSeconds(5))
//				        .ignoring(NoSuchElementException.class);
//			 fluentWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("sasaasdsa")));
//			 
			// driver.findElement(By.xpath("sasaasdsa")).click();
			 Actions action=new Actions(driver);
			 Robot robot=new Robot();
			 
//			 //wait apply to only targeted element
//			WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(60));
//			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(""))));
			
			
			String title=driver.getTitle();
			System.out.println(title);
			//Thread.sleep(5000);
			driver.findElement(By.name("username")).sendKeys("Admin");
			File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File destinationpath=new File("C:\\Users\\vinay\\Downloads\\OrangeHRM.png");
			FileUtils.copyFile(file, destinationpath);
			driver.findElement(By.name("password")).sendKeys("admin123");
			
			driver.findElement(By.xpath("//button[text()=' Login ']")).click();
//			try {
//				WebElement actionsbutton=driver.findElement(By.xpath("//p[text()='My Actions']"));
//				if(actionsbutton.isDisplayed()) {
//					System.out.println("Yes actions button available and we are successfully logged in to application");
//				}
//			} catch (NoSuchElementException e) {
//				System.out.println("Login failed!!!!!!!!!!!");
//			}
//			for (String window : driver.getWindowHandles()) {
//				driver.switchTo().window(window);
//				
//			}
			
			
//			driver.navigate().refresh();
			//Navigated to buzz menu
			driver.findElement(By.xpath("//span[text()='Buzz']")).click();
			//clicking on share photos button
			driver.findElement(By.xpath("//button[text()=' Share Photos']")).click();
			driver.findElement(By.xpath("//*[@class='orangehrm-photo-input']")).click();
			Thread.sleep(2000);
			// copying File path to Clipboard
		    StringSelection str = new StringSelection("C:\\Users\\vinay\\Downloads\\TestImage.jpg");
		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
		     // press Contol+V for pasting
		     robot.keyPress(KeyEvent.VK_CONTROL);
		     robot.keyPress(KeyEvent.VK_V);
		 
		    // release Contol+V for pasting
		     robot.keyRelease(KeyEvent.VK_CONTROL);
		     robot.keyRelease(KeyEvent.VK_V);
		 
		    // for pressing and releasing Enter
		     robot.keyPress(KeyEvent.VK_ENTER);
		     robot.keyRelease(KeyEvent.VK_ENTER);
		   
		     
		     try {
					List<WebElement> imageslstWbe=driver.findElements(By.xpath("//div[@class='orangehrm-buzz-post-body']//img"));
					for(WebElement wbe : imageslstWbe) {
						((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", wbe);
						Thread.sleep(2000);
					}
				} catch (NoSuchElementException e) {
					System.out.println("Login failed!!!!!!!!!!!");
				}
		   
		  
//			action.sendKeys(Keys.ENTER);
			//Sending input address to elemnt
			//driver.findElement(By.xpath("//*[@class='orangehrm-photo-input']//input")).sendKeys("C:\\Users\\vinay\\Downloads\\TestImage.jpg");
			//click on submit
			driver.findElement(By.xpath("//button[text()=' Share ']")).click();
			//driver.findElement(By.xpath("//span[text()='Admin']")).sendKeys(Keys.chord(Keys.CONTROL,Keys.RETURN));
			
			//driver.quit();
			//driver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//driver.close();

	}

}
