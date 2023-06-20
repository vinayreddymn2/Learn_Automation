package pageObjects;

import framework.Framework;

public class GlobalPage extends Framework{

	public GlobalPage() {
		
	}
	
	
	public void navigateToPage(String strPageName)
	{
		clickHyperlink(strPageName);
	}
}
