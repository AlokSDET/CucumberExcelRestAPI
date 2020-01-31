package common;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class AbstractReqres implements InterfaceReqres{

	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteSetup() throws Exception {
		// Setup at SuiteLevel: Creating connection with application, launching
		// application, Reporting, making DB connections can be done here.
	}

	protected void beforeClassConfiguration() throws Exception { 
		// This method will be override by implemented class and can be keep
		// under beforeClass annotation for doing class level setup.
	}
	
	protected  void afterClassConfiguration() throws Exception { 
		// This method will be override by implemented class and can be keep
		// under afterClass annotation for doing class level tear down.
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuiteTearDown() throws Exception {
		// Tear Done at Suite level : Closing application, Closing DB connection
		// code can be done here.
	}
}
