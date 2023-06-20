package smokeTestScripts;

import org.testng.annotations.Test;

import framework.BrowserType;
import framework.Framework;
import pageObjects.AdminPage;
import pageObjects.GlobalPage;
import pageObjects.LoginPage;

public class SmokeTestCase2 {
	
	@Test
	public void VerifyLoginTestcase() {
		//initializing driver and setup
		Framework fw=new Framework();
		fw.setUpDriver(BrowserType.MicrosoftEdge);
		fw.LaunchApplication();
		//Login to application
		LoginPage login=new LoginPage();
		login.LogintoApplication();
		//Navigating to Admin Page
		GlobalPage global=new GlobalPage();
		global.navigateToPage("Admin");
		//Performing search operation
		AdminPage admin=new AdminPage();
		admin.SearchSystemUsers("Dominic.Chase", "Admin", "", "", "Search");
		
	}

}
