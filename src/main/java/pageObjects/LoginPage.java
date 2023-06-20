package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.Framework;
import framework.Operations;

public class LoginPage extends Framework {
	
	@FindBy(name = "username")	WebElement txtusername;
	
	@FindBy(name = "password")
	WebElement txtpassword;
	
	@FindBy(xpath = "//button[text()=' Login ']")
	WebElement btnLogin;
	
	public LoginPage()
	{
		PageFactory.initElements(getDriver(), this);
	}
	
	public void LogintoApplication() {
		List<List<String>> data=getDatafromExcel("SampleData", "SalesOrders");
		
		
		// Login to application
		EnterText(txtusername, getProperties("browserName"),Operations.EnterData);
		EnterText(txtpassword, getProperties("Password"),Operations.EnterData);
		// click on login
		ClickWebElement(btnLogin);
	}
}
