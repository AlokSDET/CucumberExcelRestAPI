package testmodules;

import static common.Constants.HTTP_BAD_REQUEST;
import static org.testng.Assert.assertEquals;

import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.ReqresLoginServices;
import common.ReqresLoginServicesBaseTest;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utilities.TestUtilities;

public class ReqresLoginServicesTest extends ReqresLoginServicesBaseTest{

	@Override
	@BeforeClass
	protected void beforeClassConfiguration() throws Exception {
		// Since we don't have anything as of now which can be part of
		// prerequisites of this class , hence leaving this method empty.
	}

	@Override
	@AfterClass
	protected void afterClassConfiguration() throws Exception {
		// Since we don't have anything as of now which can be part of tear down
		// of this class , hence leaving this method empty.
	}
	
	@Test(description = "Execute 'https://reqres.in/api/login' API to validate that Login is not successfull "
			+ " for invalid syntax body and returns 400 bad syntax error with error details.")
	public void verifyUnSuccessfullLogin() throws Exception {
		final JSONObject loginJsonObj = TestUtilities.createJSONObject("email", "peter@klaven");
		final Response response = ReqresLoginServices.login(loginJsonObj);
		// Getting all the headers and printing them.
		Headers headersList = response.getHeaders();
		for (Header header : headersList) {
			if (header.getName().toString().equals("Content-Type")) {
				assertEquals(header.getValue().toString(), "application/json; charset=utf-8", "header type does not match.");
			}
		}
		// Since API is returning 200 hence failing this test case, ideally it should return 400 as we are passing invalid syntax.
		assertEquals(response.getStatusCode(), HTTP_BAD_REQUEST, "Status code does not matches.");
	}
}
