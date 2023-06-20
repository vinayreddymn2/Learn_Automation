package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import framework.Framework;

public class AdminPage extends Framework {

	@FindBy(xpath = "//*[text()='Username']/../..//input")
	private WebElement txtuserName;
	
	@FindBy(xpath = "//*[text()='User Role']/../..//*[@class='oxd-select-text-input']")
	private WebElement drpUserRole;
	
	@FindBy(xpath = "//*[text()=' Search ']")
	private WebElement btnSearch;
	
	@FindBy(how=How.ID , using="//*[text()=' Search ']")
	WebElement btnid;
	
	public AdminPage() {
		PageFactory.initElements(getDriver(), this);
	}

	/**
	 * This Method is created to enter search system users details
	 * @param strUserName
	 * @param strUserRole
	 * @param strEmployeeName
	 * @param strStatus
	 * @param operation
	 */
	public void SearchSystemUsers(String strUserName, String strUserRole, String strEmployeeName, String strStatus,
			String operation) {
		ClearAndEnterText(txtuserName, strUserName);
		SelectDropdown(drpUserRole,strUserRole);
		if (operation.equalsIgnoreCase("Search")) {
			ClickWebElement(btnSearch);
		}
	}

}
