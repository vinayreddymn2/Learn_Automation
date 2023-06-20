package Automation.SeleniumProject;

import org.testng.annotations.*;

public class TestNGAnnotations {

	@BeforeSuite( groups = {"TestRun1","TestRun"})
	public void BeforeSuite() {
		System.out.println("Before Suite");
	}
	
	@BeforeTest( groups = {"TestRun1","TestRun"})
	public void BeforeTest() {
		System.out.println("Before Test");
	}

	@BeforeClass( groups = {"TestRun1","TestRun"})
	public void BeforeClass() {
		System.out.println("Before Class");
	}

	@BeforeMethod( groups = {"TestRun1","TestRun"})
	public void BeforeMethod() {
		System.out.println("Before Method");
	}
	
	@Test(priority = 0 , alwaysRun = true, groups = {"TestRun"})
	public void ATestCase() {
		System.out.println("A Testcase ");
	}
	
	@Test(priority = 1,alwaysRun = true, groups = {"TestRun1"})
	public void TestCase3() {
		System.out.println("Testcase 3");
	}
		
	@Test(priority = 2,alwaysRun = false, groups = {"TestRun"})
	public void TestCase4() {
		System.out.println("Testcase 4");
	}

	@Test(priority = 3,alwaysRun = false, groups = {"TestRun1"})
	public void TestCase1() {
		System.out.println("Testcase 1");
	}

	@Test(priority = 4,alwaysRun = false, groups = {"TestRun"})
	public void TestCase2() {
		System.out.println("Testcase 2");
	}

	
	

	@AfterMethod( groups = {"TestRun1","TestRun"})
	public void AfterMethod() {
		System.out.println("After Method");
	}

	@AfterClass( groups = {"TestRun1","TestRun"})
	public void AfterClass() {
		System.out.println("After Class");
	}

	@AfterTest( groups = {"TestRun1","TestRun"})
	public void AfterTest() {
		System.out.println("After Test");
	}
	
	@AfterSuite( groups = {"TestRun1","TestRun"})
	public void AfterSuite() {
		System.out.println("After Suite");
	}

	@BeforeGroups( groups = {"TestRun1","TestRun"})
	public void BeforeGroups() {
		System.out.println("Before Groups");
	}

	@AfterGroups( groups = {"TestRun1","TestRun"})
	public void AfterGroups() {
		System.out.println("After Groups");
	}

}
