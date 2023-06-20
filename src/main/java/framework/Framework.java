package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.util.Strings;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.OperaDriverManager;

public class Framework {
	private static WebDriver driver;
	private Properties props;
	private HashMap<String, String> Config;
	public static long DEFAULT_TIMEOUT_SECONDS = 6;
	public static long DEFAULT_POLLING_INTERVAL_MILLISECONDS = 1500;
	public static Integer intLoggingLevel = 0;
	
	public Framework() {
		setConfigFileAndLoadValues();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public String getProperties(String strKeyValue) {
		return Config.get(strKeyValue);
	}

	/**
	 * This method is created to enter value
	 * 
	 * @param wbe
	 * @param strValue
	 */
	public void EnterText(WebElement wbe, String strValue, Operations operation) {
		try {
			if (operation.equals(Operations.EnterData)) {
				wbe.sendKeys(strValue);
			} else if (operation.equals(Operations.VerifyData)) {
				Assert.assertEquals(wbe.getText(), strValue);
			}
		} catch (Exception e) {
			System.out.println("Exception at " + e);
		}
	}

	/**
	 * This method is created to click on web element
	 * 
	 * @param wbe
	 */
	public void ClickWebElement(WebElement wbe) {
		try {
			wbe.click();
		} catch (Exception e) {
			System.out.println("Exception at " + e);
		}
	}

	/**
	 * This Method will Setup and initialize Specified browser
	 */
	public void setUpDriver(BrowserType browser) {
		try {
			switch (browser) {
			case Chrome:
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case Firefoxdriver:
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case MicrosoftEdge:
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			case Opera:
				WebDriverManager.operadriver().setup();
				driver = (WebDriver) new OperaDriverManager();
				break;
			case Safari:
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();
				break;
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method will Setup and initialize Specified browser at property file
	 */
	public void setUpDriver() {
		try {
			String browser = Config.get("browserName");
			switch (browser) {
			case "Chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				break;
			case "Firefoxdriver":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case "MicrosoftEdge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				break;
			case "Opera":
				WebDriverManager.operadriver().setup();
				driver = (WebDriver) new OperaDriverManager();
				break;
			case "Safari":
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();
				break;
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method is used to launch application in specified browser
	 * 
	 */
	public void LaunchApplication() {
		try {
			driver.get(Config.get("URL"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is created to Clear and enter value
	 * 
	 * @param wbe
	 * @param strValue
	 */
	public static void ClearAndEnterText(WebElement wbe, String strValue) {
		try {
			wbe.click();
			wbe.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			wbe.sendKeys(Keys.BACK_SPACE);
			wbe.sendKeys(strValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This Method is created to Click Hyperlink Dynamically based on text
	 * 
	 * @param strText
	 */
	public void clickHyperlink(String strText) {
		try {
			driver.findElement(By.xpath("//*[text()='" + strText + "']")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This Method is created to validate Title of the page
	 * 
	 * @param expectedTitle
	 */
	public static void ValidateTitle(String expectedTitle) {
		try {
			String ActualTitle = getDriver().getTitle();
			if (ActualTitle.equals(expectedTitle)) {
				System.out.println("Expected Page Found " + ActualTitle);
			} else {
				System.out.println("Incorrect Page Found" + "Actual Title : " + ActualTitle + "Expected Title : " + expectedTitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is created to select value from drop down
	 * 
	 * @param wbe
	 * @param strValue
	 */
	public static void SelectDropdown(WebElement wbe, String strValue) {
		try {
			// clicking on dropdown list
			wbe.click();
			List<WebElement> options = driver.findElements(By.xpath("//*[@role='option']"));
			for (WebElement option : options) {
				System.out.println("Drop down values" + option.getText());
				if (option.getText().equals(strValue)) {
					option.click();
					return;
				}
			}
			System.out.println("Option Not available");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is created to select value from filter web list drop down
	 * 
	 * @param wbe
	 * @param strValue
	 */
	public static void SelectFilterWeblist(WebElement wbe, String strValue) {
		try {
			// clicking on dropdown list
			wbe.click();
			Thread.sleep(5000);
			wbe.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			wbe.sendKeys(Keys.BACK_SPACE);
			wbe.sendKeys(strValue);// Peter Mac Anderson
			Thread.sleep(5000);
			// driver.findElement(By.xpath("//*[@role='option']")).click();
			List<WebElement> options = driver.findElements(By.xpath("//*[@role='option']"));
			for (WebElement option : options) {
				if (option.getText().equals(strValue)) {
					option.click();
					return;
				}
			}
			System.out.println("Option Not available");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadProperty(String propertyName) {
		try {
			props = new Properties();
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator +"src" + File.separator + "main" + File.separator +  "resources" + File.separator + propertyName + ".properties");
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setConfigFileAndLoadValues() {
		loadProperty("config");
		HashMap<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> entry : props.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		Config = map;
	}

	public HashMap<String, String> getPropertyAsHashMap(String propertyName) {
		loadProperty(propertyName);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> entry : props.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}

	/**
	 * EnterText executes sendKeys() to set the value of the element to the input
	 * value. Waits until the element is clickable before setting the value.
	 * 
	 * @param webElement       - element to enter text into
	 * @param input            - value of text going into element
	 * @param timeOutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void EnterText(WebElement webElement, String input, long timeOutInSeconds) {
		enterText(webElement, input, timeOutInSeconds);
	}

	public static void enterText(WebElement webElement, String input) {
		enterText(webElement, input, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void enterText(WebElement webElement, String input, long timeOutInSeconds) {
		if (input != null && input.equalsIgnoreCase("IGNORE"))
			return;
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
			clear(webElement);
			inputText(webElement, input);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void clear(WebElement webElement) {
		Actions builder = new Actions(getDriver());
		Action selectAllDelete;
		selectAllDelete = builder.keyDown(webElement, Keys.CONTROL).sendKeys(webElement, "a").sendKeys(webElement, Keys.BACK_SPACE).keyUp(webElement, Keys.CONTROL).build();
		selectAllDelete.perform();
	}

	private static void inputText(WebElement webElement, String input) {
		Actions builder = new Actions(getDriver());
		Action focusAndEnterText;
		builder.moveToElement(webElement).build().perform();
		focusAndEnterText = builder.sendKeys(webElement, input).build();
		focusAndEnterText.perform();
	}

	/**
	 * EnterTextJs executes javascript to set the value of the element to the input
	 * value. Waits until the element is clickable before setting the value.
	 * 
	 * @param webElement       - element to enter text into
	 * @param input            - value of text going into element
	 * @param timeOutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void EnterTextJs(WebElement webElement, String input, long timeOutInSeconds) {
		enterTextJs(webElement, input, timeOutInSeconds);
	}

	public static void enterTextJs(WebElement webElement, String input) {
		enterTextJs(webElement, input, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void enterTextJs(WebElement webElement, String input, long timeOutInSeconds) {
		if (input != null && input.equalsIgnoreCase("IGNORE"))
			return;
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].value='" + input + "'", webElement);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * SelectRadioButton clicks a specified radio button. Waits until the element is
	 * clickable.
	 * 
	 * @param webElement       - specific radio button element to click, not a
	 *                         container of radio buttons
	 * @param timeoutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void SelectRadioButton(WebElement webElement, long timeoutInSeconds) {
		selectRadioButton(webElement, timeoutInSeconds);
	}

	/**
	 * selectRadioButton clicks a specified radio button. Waits until default time
	 * out for the element to be clickable.
	 * 
	 * @param webElement
	 */
	public static void selectRadioButton(WebElement webElement) {
		selectRadioButton(webElement, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void selectRadioButton(WebElement webElement, long timeoutInSeconds) {
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
			webElement.click();
		} catch (Exception e) {
		}
	}

	/**
	 * selectFromDropdownByIndex will select the value from a dropdown matching the
	 * provided index.
	 * 
	 * @param webElement - The webelement for the dropdown box
	 * @param index      - The index for the value in which you want selected
	 */
	public static void selectFromDropdownByIndex(WebElement webElement, int index) {
		selectFromDropdownByIndex(webElement, index, DEFAULT_TIMEOUT_SECONDS);
	}

	/**
	 * selectFromDropdownByIndex will select the value from a dropdown matching the
	 * provided index.
	 * 
	 * @param webElement       - The webelement for the dropdown box
	 * @param index            - The index for the value in which you want selected
	 * @param timeoutInSeconds - wait time for dropdown options to populate
	 */
	public static void selectFromDropdownByIndex(WebElement webElement, int index, long timeoutInSeconds) {
		selectFromDropdown(new Object() {}.getClass().getEnclosingMethod().getName(), webElement, index, null, null, timeoutInSeconds);
	}

	/**
	 * selectFromDropdownByValue will select the value from a dropdown matching the
	 * provided string value.
	 * 
	 * @param webElement - The webelement for the dropdown box
	 * @param value      - The string value in which you want selected from the
	 *                   dropdown
	 */
	public static void selectFromDropdownByValue(WebElement webElement, String value) {
		selectFromDropdownByValue(webElement, value, DEFAULT_TIMEOUT_SECONDS);
	}

	/**
	 * selectFromDropdownByValue will select the value from a dropdown matching the
	 * provided string value.
	 * 
	 * @param webElement       - The webelement for the dropdown box
	 * @param value            - The string value in which you want selected from
	 *                         the dropdown
	 * @param timeoutInSeconds - wait time for dropdown options to populate
	 */
	public static void selectFromDropdownByValue(WebElement webElement, String value, long timeoutInSeconds) {
		selectFromDropdown(new Object() {}.getClass().getEnclosingMethod().getName(), webElement, null, value, null, timeoutInSeconds);
	}

	/**
	 * selectFromDropdownByVisibleText will select the value from a dropdown
	 * matching the provided visible text.
	 * 
	 * @param webElement  - The webelement for the dropdown box
	 * @param visibleText - The text that is displayed in the UI within the dropdown
	 *                    that you want selected
	 */
	public static void selectFromDropdownByVisibleText(WebElement webElement, String visibleText) {
		selectFromDropdownByVisibleText(webElement, visibleText, DEFAULT_TIMEOUT_SECONDS);
	}

	/**
	 * selectFromDropdownByVisibleText will select the value from a dropdown
	 * matching the provided visible text.
	 * 
	 * @param webElement       - The webelement for the dropdown box
	 * @param visibleText      - The text that is displayed in the UI within the
	 *                         dropdown that you want selected
	 * @param timeoutInSeconds - wait time for dropdown options to populate
	 */
	public static void selectFromDropdownByVisibleText(WebElement webElement, String visibleText, long timeoutInSeconds) {
		selectFromDropdown(new Object() {}.getClass().getEnclosingMethod().getName(), webElement, null, null, visibleText, timeoutInSeconds);
	}

	private static void selectFromDropdown(String methodName, WebElement webElement, Integer index, String value, String visibleText, long timeoutInSeconds) {
		String reportText = "";
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(webElement));
			Select dropdown = new Select(webElement);
			if (!Strings.isNullOrEmpty(value)) {
				dropdown.selectByValue(value);
				reportText = ("Value:" + value);
			} else if (!Strings.isNullOrEmpty(visibleText)) {
				dropdown.selectByVisibleText(visibleText);
				reportText = ("Visible Text:" + visibleText);
			} else {
				dropdown.selectByIndex(index);
				reportText = ("Index:" + index);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * selectFromDropdown selects an option from a dropdown based the provided text
	 * value. Waits until the element is clickable.
	 * 
	 * @param webElement       - element representing the dropdown
	 * @param visibleText      - text to select from the dropdown
	 * @param timeOutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void SelectFromDropdown(WebElement webElement, String visibleText, long timeoutInSeconds) {
		selectFromDropdown(webElement, visibleText, timeoutInSeconds);
	}

	/**
	 * @param webElement
	 * @param visibleText
	 */
	public static void selectFromDropdown(WebElement webElement, String visibleText) {
		selectFromDropdown(webElement, visibleText, DEFAULT_TIMEOUT_SECONDS);
	}

	/**
	 * @param webElement
	 * @param visibleText
	 * @param timeoutInSeconds
	 */
	public static void selectFromDropdown(WebElement webElement, String visibleText, long timeoutInSeconds) {
		selectFromDropdown((new Object() {}.getClass().getEnclosingMethod().getName()), webElement, visibleText, -1, timeoutInSeconds);
	}

	/**
	 * selectFromDropdown selects an option from a dropdown based on the index of
	 * the option in the dropdown list. Waits until the element is clickable.
	 * 
	 * @param webElement       - element representing the dropdown
	 * @param index            - index of the option in the dropdown list
	 * @param timeoutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void SelectFromDropdown(WebElement webElement, int index, long timeoutInSeconds) {
		selectFromDropdown(webElement, index, timeoutInSeconds);
	}

	/**
	 * @param webElement
	 * @param index
	 */
	public static void selectFromDropdown(WebElement webElement, int index) {
		selectFromDropdown(webElement, index, DEFAULT_TIMEOUT_SECONDS);
	}

	/**
	 * @deprecated Use new "selectFromDropdownBy*"
	 * @param webElement
	 * @param index
	 * @param timeoutInSeconds
	 */
	public static void selectFromDropdown(WebElement webElement, int index, long timeoutInSeconds) {
		selectFromDropdown((new Object() {}.getClass().getEnclosingMethod().getName()), webElement, null, index, timeoutInSeconds);
	}

	private static void selectFromDropdown(String methodName, WebElement webElement, String visibleText, int index, long timeoutInSeconds) {
		if (visibleText != null && visibleText.equalsIgnoreCase("IGNORE"))
			return;
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(webElement));
			Select dropdown = new Select(webElement);
			if (visibleText != null) {
				waitUntilSelectOptionsPopulated(dropdown, visibleText, timeoutInSeconds);
				dropdown.selectByVisibleText(visibleText);
			} else {
				dropdown.selectByIndex(index);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * SelectCheckbox clicks the specified checkbox. Waits until the element is
	 * clickable.
	 * 
	 * @param webElement       - checkbox element to click
	 * @param timeoutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public static void SelectCheckbox(WebElement webElement, long timeoutInSeconds) {
		selectCheckbox(webElement, timeoutInSeconds);
	}

	public static void selectCheckbox(WebElement webElement) {
		selectCheckbox(webElement, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void selectCheckbox(WebElement webElement, long timeoutInSeconds) {
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
			webElement.click();
		} catch (Exception e) {
		}
	}

	/**
	 * hoverOverWebElement is used to hover over an element without clicking. Waits
	 * until the element is clickable before setting focus on the element.
	 * 
	 * @param webElement       - element to hover over
	 * @param timeOutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void HoverOverWebElement(WebElement webElement, long timeOutInSeconds) {
		hoverOverWebElement(webElement, timeOutInSeconds);
	}

	public static void hoverOverWebElement(WebElement webElement) {
		hoverOverWebElement(webElement, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void hoverOverWebElement(WebElement webElement, long timeOutInSeconds) {
		String methodName = (new Object() {
		}.getClass().getEnclosingMethod().getName());
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
			new Actions(getDriver()).moveToElement(webElement).perform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * ClickWebElement focuses on and clicks the specified element. Waits until the
	 * element is clickable.
	 * 
	 * @param webElement       - element to click
	 * @param timeoutInSeconds - number of seconds to wait until the element is
	 *                         clickable
	 */
	public void ClickWebElement(WebElement webElement, long timeoutInSeconds) {
		clickWebElement(webElement, timeoutInSeconds);
	}

	public static void clickWebElement(WebElement webElement) {
		clickWebElement(webElement, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void clickWebElement(WebElement webElement, long timeoutInSeconds) {
		clickWebElement(webElement, timeoutInSeconds, DEFAULT_POLLING_INTERVAL_MILLISECONDS);
	}

	/**
	 * ClickWebElement focuses on and clicks the specified element. Waits until the
	 * element is clickable. Allows overriding polling interval.
	 * 
	 * @param webElement                    - element to click
	 * @param timeoutInSeconds              - number of seconds to wait until the
	 *                                      element is clickable
	 * @param pollingIntervalInMilliseconds - number of milliseconds in between
	 *                                      checking if element is condition
	 */
	public static void ClickWebElement(WebElement webElement, long timeoutInSeconds, long pollingIntervalInMilliseconds) {
		clickWebElement(webElement, timeoutInSeconds, pollingIntervalInMilliseconds);
	}

	public static void clickWebElement(WebElement webElement, long timeoutInSeconds, long pollingIntervalInMilliseconds) {
		clickWebElement(webElement, timeoutInSeconds, pollingIntervalInMilliseconds, true);
	}

	/**
	 * ClickWebElement focuses on and clicks the specified element. Waits until the
	 * element is clickable. Allows overriding polling interval and setting focus.
	 * 
	 * @param webElement                    - element to click
	 * @param timeoutInSeconds              - number of seconds to wait until the
	 *                                      element is clickable
	 * @param pollingIntervalInMilliseconds - number of milliseconds in between
	 *                                      checking if element is condition
	 * @param setFocus                      - set to false if you do not want focus
	 *                                      set before clicking
	 */
	public static void ClickWebElement(WebElement webElement, boolean setFocus) {
		clickWebElement(webElement, DEFAULT_TIMEOUT_SECONDS, DEFAULT_POLLING_INTERVAL_MILLISECONDS, setFocus);
	}

	public static void ClickWebElement(WebElement webElement, long timeoutInSeconds, long pollingIntervalInMilliseconds, boolean setFocus) {
		clickWebElement(webElement, timeoutInSeconds, pollingIntervalInMilliseconds, setFocus);
	}

	public static void clickWebElement(WebElement webElement, long timeoutInSeconds, long pollingIntervalInMilliseconds, boolean setFocus) {
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		String objectName = "";
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(pollingIntervalInMilliseconds)).ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			if (setFocus) {
				Actions act = new Actions(getDriver());
				act.moveToElement(webElement).build().perform();
			}
			objectName = getObjectName(webElement);
			webElement.click();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * GetTextFromElement returns the text from an element after waiting for the
	 * element to be visible.
	 * 
	 * @param webElement
	 * @param timeoutInSeconds
	 * @return text from element
	 */
	public String GetTextFromElement(WebElement webElement, long timeoutInSeconds) {
		return getTextFromElement(webElement, timeoutInSeconds);
	}

	public static String getTextFromElement(WebElement webElement) {
		return getTextFromElement(webElement, DEFAULT_TIMEOUT_SECONDS);
	}

	public static String getTextFromElement(WebElement webElement, long timeoutInSeconds) {
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		String elementText = "";
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.visibilityOf(webElement));
			elementText = webElement.getText();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return elementText;
	}

	/**
	 * VerifyTextInWebElement returns true/false if expectedText found in webElement
	 * 
	 * @param webElement
	 * @param expectedText
	 * @param timeoutInSeconds
	 * @return
	 */
	public Boolean VerifyTextInWebElement(WebElement webElement, String expectedText, long timeoutInSeconds) {
		return verifyTextInWebElement(webElement, expectedText, timeoutInSeconds);
	}

	public Boolean verifyTextInWebElement(WebElement webElement, String expectedText, long timeoutInSeconds) {
		String methodName = (new Object() {
		}.getClass().getEnclosingMethod().getName());
		Boolean success = false;
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.textToBePresentInElement(webElement, expectedText));
			success = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return success;
	}

	public static Boolean doesElementByLocatorExist(By locator, Boolean shouldExist, long timeoutInSeconds) {
		String methodName = (new Object() {
		}.getClass().getEnclosingMethod().getName());
		Boolean elementExists = false;
		String expectedResult = "should exist: " + String.valueOf(shouldExist);
		try {
			WebElement webElement = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).ignoring(NoSuchElementException.class).ignoring(TimeoutException.class).until(ExpectedConditions.presenceOfElementLocated(locator));
			elementExists = webElement != null;
		} catch (NoSuchElementException ex) {
		} catch (TimeoutException ex) {
		} finally {
			if (!elementExists) {
			}
		}
		return elementExists;
	}

	/**
	 * GetElement uses a fluent wait to find an element by the supplied locator.
	 * Useful with a dynamic element that PageFactory can't locate.
	 * 
	 * @param locator
	 * @param timeoutInSeconds
	 * @return
	 */
	public static WebElement GetElement(By locator, long timeoutInSeconds) {
		return getElement(locator, timeoutInSeconds);
	}

	public static WebElement getElement(By locator) {
		return getElement(locator, DEFAULT_TIMEOUT_SECONDS);
	}

	public static WebElement getElement(By locator, long timeoutInSeconds) {
		String methodName = (new Object() {
		}.getClass().getEnclosingMethod().getName());
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
			return wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});
		} catch (Exception ex) {
		}
		return null;
	}

	/**
	 * GetElements uses a fluent wait to find a list of elements by the supplied
	 * locator. Useful with a dynamic element that PageFactory can't locate.
	 * 
	 * @param locator
	 * @param timeoutInSeconds
	 * @return
	 */
	public static List<WebElement> getElements(By locator) {
		return getElements(locator, DEFAULT_TIMEOUT_SECONDS);
	}

	public List<WebElement> GetElements(By locator, long timeoutInSeconds) {
		return getElements(locator, timeoutInSeconds);
	}

	public static List<WebElement> getElements(By locator, long timeoutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		return wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(locator);
			}
		});
	}

	public WebElement getFirstButtonInFirstItemOfUnorderedList(WebElement unorderedList, long timeoutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement button = null;
				List<WebElement> listItems = unorderedList.findElements(By.tagName("li"));
				for (WebElement webElement : listItems) {
					button = webElement.findElement(By.xpath("//a[@role = 'button']"));
					if (button != null)
						return button;
				}
				return button;
			}
		});
	}

	public Boolean waitUntilListItemsPopulated(WebElement unorderedList, long timeoutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		return wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				List<WebElement> listItems = unorderedList.findElements(By.tagName("li"));
				return listItems.size() > 1;
			}
		});
	}

	/**
	 * GetRows uses a fluent wait to find a list of
	 * <tr>
	 * elements in given table
	 * 
	 * @param table
	 * @param timeoutInSeconds
	 * @return
	 */
	public List<WebElement> GetRows(WebElement table, long timeoutInSeconds) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		return wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return table.findElements(By.tagName("tr"));
			}
		});
	}

	/**
	 * isElementVisible uses explicit wait to determine if webElement isDisplayed()
	 * 
	 * @param webElement
	 * @param timeoutInSeconds
	 * @return boolean
	 */
	public Boolean isElementVisible(WebElement webElement, long timeoutInSeconds) {
		Boolean elementVisible = false;
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.visibilityOf(webElement));
			elementVisible = true;
		} catch (Exception ex) {
		}
		return elementVisible;
	}

	/**
	 * isElementClickable uses explicit wait to determine if webElement
	 * isDisplayed() and isEnabled()
	 * 
	 * @param webElement
	 * @param timeoutInSeconds
	 * @return boolean
	 */
	public Boolean isElementClickable(WebElement webElement, long timeoutInSeconds) {
		Boolean elementClickable = false;
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.elementToBeClickable(webElement));
			elementClickable = true;
		} catch (Exception ex) {
		}
		return elementClickable;
	}

	/**
	 * waitForAjaxToFinish executes javascript to determine if any jQuery is still
	 * firing
	 * 
	 * @param timeoutInSeconds
	 */
	public void WaitForAjaxToFinish(long timeoutInSeconds) {
		waitForAjaxToFinish(timeoutInSeconds);
	}

	public void waitForAjaxToFinish(long timeoutInSeconds) {
		Boolean isJqueryUsed = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return (typeof(jQuery) != 'undefined')");
		if (isJqueryUsed) {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(jsValueToBe("return document.readyState", "complete"));
		}
	}

	/**
	 * waitForReadyState executes javascript to determine if the page has been
	 * loaded
	 * 
	 * @param timeoutInSeconds
	 */
	public void WaitForReadyState(long timeoutInSeconds) {
		waitForReadyState(timeoutInSeconds);
	}

	public static void waitForReadyState(long timeoutInSeconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(jsValueToBe("return document.readyState", "complete"));
	}

	private static ExpectedCondition<Boolean> jsValueToBe(final String javaScript, final String expectedText) {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					Object value = ((JavascriptExecutor) driver).executeScript(javaScript);
					return value.equals(expectedText);
				} catch (WebDriverException e) {
					return false;
				}
			}

			@Override
			public String toString() {
				return String.format("js %s executed and value to be %s", javaScript, expectedText);
			}
		};
	}

	public static void waitUntilAnyText(WebElement element) {
		waitUntilAnyText(element, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void waitUntilAnyText(WebElement element, long timeoutInSeconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until((ExpectedCondition<Boolean>) driver -> element.getText().length() != 0);
	}

	/**
	 * WaitUntilPageTitle waits for a specific page title
	 * 
	 * @param expectedTitle
	 * @param timeoutInSeconds
	 */
	public void WaitUntilPageTitle(String expectedTitle, long timeoutInSeconds) {
		waitUntilPageTitle(expectedTitle, timeoutInSeconds);
	}

	public static void waitUntilPageTitle(String expectedTitle, long timeoutInSeconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.titleIs(expectedTitle));
	}

	/**
	 * WaitUntilUrl waits for a specific url
	 * 
	 * @param expectedUrl
	 * @param timeoutInSeconds
	 */
	public static void waitUntilUrl(String expectedUrl, long timeoutInSeconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.urlToBe(expectedUrl));
	}

	/**
	 * WaitUntilStaleElement finds an element by supplied locator and waits until it
	 * is no longer in the DOM
	 * 
	 * @param locator
	 * @param timeoutInSeconds
	 */
	public static void waitUntilStaleElement(By locator, long timeoutInSeconds) {
		WebElement webElement = getDriver().findElement(locator);
		if (webElement != null) {
			waitUntilStaleElement(webElement, timeoutInSeconds);
		}
	}

	/**
	 * WaitUntilStaleElement waits until the supplied element is no longer in the
	 * DOM
	 * 
	 * @param webElement
	 * @param timeoutInSeconds
	 */
	public static void waitUntilStaleElement(WebElement webElement, long timeoutInSeconds) {
		if (webElement != null) {
			try {
				new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.stalenessOf(webElement));
			} catch (Exception NoSuchElementException) {
				return;
			}
		}
	}

	/**
	 * WaitUntilAttributeValue waits until the element's supplied attribute matches
	 * the supplied value.
	 * 
	 * @param webElement
	 * @param attributeName
	 * @param expectedAttributeValue
	 * @param timeoutInSeconds
	 */
	public void WaitUntilAttributeValue(WebElement webElement, String attributeName, String expectedAttributeValue, long timeoutInSeconds) {
		waitUntilAttributeValue(webElement, attributeName, expectedAttributeValue, timeoutInSeconds);
	}

	public void waitUntilAttributeValue(WebElement webElement, String attributeName, String expectedAttributeValue, long timeoutInSeconds) {
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		String objectName = getObjectName(webElement);
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.attributeToBe(webElement, attributeName, expectedAttributeValue));
		} catch (Exception e) {
		}
	}

	/**
	 * waitUntilAttributeValueNotPresent waits until the value of the attribute
	 * supplied is no longer found
	 * 
	 * @param webElement
	 * @param attribute
	 * @param timeoutInSeconds
	 */
	public void WaitUntilAttributeValueNotPresent(WebElement webElement, String attribute, long timeoutInSeconds) {
		waitUntilAttributeValueNotPresent(webElement, attribute, timeoutInSeconds, DEFAULT_POLLING_INTERVAL_MILLISECONDS);
	}

	public void WaitUntilAttributeValueNotPresent(WebElement webElement, String attribute, long timeoutInSeconds, long sleepInMilliseconds) {
		waitUntilAttributeValueNotPresent(webElement, attribute, timeoutInSeconds, sleepInMilliseconds);
	}

	public static void waitUntilAttributeValueNotPresent(WebElement webElement, String attribute, long timeoutInSeconds, long sleepInMilliseconds) {
		String methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		String objectName = getObjectName(webElement);
		try {
			new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.attributeToBe(webElement, attribute, ""));
		} catch (Exception e) {
		}
	}

	public static void waitUntilSelectOptionsPopulated(final Select select, String optionText) {
		waitUntilSelectOptionsPopulated(select, optionText, DEFAULT_TIMEOUT_SECONDS);
	}

	public static void waitUntilSelectOptionsPopulated(final Select select, String optionText, long timeoutInSeconds) {
		new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).pollingEvery(Duration.ofMillis(DEFAULT_POLLING_INTERVAL_MILLISECONDS)).until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver d) {
				return (select.getOptions().size() > 1 || select.getFirstSelectedOption().getText().equals(optionText));
			}
		});
	}

	public static void waitForElement(WebElement webElement) {
		WaitFor(webElement, DEFAULT_TIMEOUT_SECONDS);
	}

	/**
	 * This method should be used to wait for certain element to become visible /
	 * load
	 */
	public void WaitFor(WebElement webElement) {
		WaitFor(webElement, 15);
	}

	public static void WaitFor(WebElement webElement, long timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.visibilityOf(webElement));
		} catch (Exception e) {
			if (intLoggingLevel == 1) {
				e.printStackTrace();
			}
		}
	}

	public static WebElement waitUntilWebElement(By locator) {
		return waitUntilWebElement(locator, DEFAULT_TIMEOUT_SECONDS);
	}

	public WebElement WaitUntilWebElement(By locator, long timeoutInSeconds) {
		return waitUntilWebElement(locator, timeoutInSeconds);
	}

	/**
	 * waitUntilWebElement uses fluent wait to return a webElement found by supplied
	 * locator
	 * 
	 * @param locator
	 * @param timeoutInSeconds
	 * @return
	 */
	public static WebElement waitUntilWebElement(By locator, long timeoutInSeconds) {
		String methodName = (new Object() {}.getClass().getEnclosingMethod().getName());
		String objectName = locator.toString();
		WebElement webElement = null;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(timeoutInSeconds)).ignoring(NoSuchElementException.class);
			webElement = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return webElement;
	}

	/**
	 * waitUntilWebElements uses fluent wait to return a list of webElements found
	 * by supplied locator
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> waitUntilWebElements(By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).ignoring(NoSuchElementException.class);
		List<WebElement> webElements = wait.until(new Function<WebDriver, List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(locator);
			}
		});
		return webElements;
	}

	public static void waitUntilElementIsNotFound(By locator, long timeoutInSeconds) {
		String methodName = (new Object() {
		}.getClass().getEnclosingMethod().getName());
		String objectName = locator.toString();
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
			wait.until(ExpectedConditions.numberOfElementsToBe(locator, 0));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * getObjectName looks at various element attributes to find a name for the
	 * supplied element
	 * 
	 * @param webElement
	 * @return
	 */
	private static String getObjectName(WebElement webElement) {
		try {
			if (webElement != null) {
				if (webElement.getAttribute("id") != null && !webElement.getAttribute("id").equals(""))
					return webElement.getAttribute("id");
				if (webElement.getAttribute("name") != null && !webElement.getAttribute("name").equals(""))
					return webElement.getAttribute("name");
				if (webElement.getAttribute("innerText") != null && !webElement.getAttribute("innerText").equals(""))
					return webElement.getAttribute("innerText");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "webElement";
	}

	/**
	 * Scrolls vertically and/or horizontally to a given point starting from top
	 * left position of page (0, 0).
	 * 
	 * @param xPixels horizontal pixel to scroll to (+: scroll left, -: scroll
	 *                right)
	 * @param yPixels vertical pixel to scroll to (+: scroll down, -: scroll up)
	 */
	public void scrollToDirectionally(int xPixels, int yPixels) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("window.scrollTo([" + xPixels + "], [" + yPixels + "])", "");
		} catch (Exception e) {
		}
	}

	/**
	 * Scrolls vertically and/or horizontally depending on user input starting from
	 * top left of current position on page.
	 * 
	 * @param xPixels horizontal pixels to scroll by (+: scroll left, -: scroll
	 *                right)
	 * @param yPixels vertical pixels to scroll by (+: scroll down, -: scroll up)
	 */
	public void scrollByDirectionally(int xPixels, int yPixels) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("window.scrollBy([" + xPixels + "], [" + yPixels + "])", "");
		} catch (Exception e) {
		}
	}

	/**
	 * Scrolls an element into view
	 * 
	 * @param element WebElement to scroll into view
	 */
	public void scrollToElement(WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
		}
	}
	
	public List<List<String>> getDatafromExcel(String excelName,String sheetName)
	{
		List<List<String>> listOfDataRows = new ArrayList<List<String>>();
		try {
			String excelPath=System.getProperty("user.dir") + File.separator +"src" + File.separator + "main" + File.separator +  "resources" + File.separator + excelName + ".xlsx";
			FileInputStream fileInputStream = new FileInputStream(excelPath.trim());
			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet=workbook.getSheet(sheetName);
			listOfDataRows=getDatafromExcel(sheet);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfDataRows;
	}
	
	
	private List<List<String>> getDatafromExcel(Sheet sheet) {
		List<List<String>> listOfDataRows = new ArrayList<List<String>>();

		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();

		if (lastRow > 0) {
			for (int i = firstRow; i < lastRow + 1; i++) {
				Row row = sheet.getRow(i);
				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				List<String> rowDataList = new ArrayList<String>();

				for (int j = firstCellNum; j < lastCellNum; j++) {
					Cell cell = row.getCell(j);
					CellType cellType = cell.getCellType();

					if (cellType == CellType.NUMERIC) {
						double numberValue = cell.getNumericCellValue();

						String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();

						rowDataList.add(stringCellValue);
					} else if (cellType == CellType.STRING) {
						String cellValue = cell.getStringCellValue();
						rowDataList.add(cellValue);
					} else if (cellType == CellType.BOOLEAN) {
						boolean numberValue = cell.getBooleanCellValue();

						String stringCellValue = String.valueOf(numberValue);

						rowDataList.add(stringCellValue);
					} else if (cellType == CellType.BLANK) {
						rowDataList.add("");
					}
				}

				listOfDataRows.add(rowDataList);
			}
		}
		return listOfDataRows;
	}
	
	
	
	
}
