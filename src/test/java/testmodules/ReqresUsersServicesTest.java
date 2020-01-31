package testmodules;

import static common.Constants.HTTP_OK;
import static common.Constants.HTTP_CREATED;
import static common.Constants.HTTP_NO_CONTENT;
import static common.Constants.HTTP_NOT_FOUND;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.ReqresUsersServices;
import common.ReqresUsersServicesBaseTest;
import io.restassured.response.Response;
import utilities.FileUtilities;
import utilities.TestUtilities;

public class ReqresUsersServicesTest extends ReqresUsersServicesBaseTest {

	public static final String NAME = "name";

	public static final String JOB = "job";

	public static final String ID = "id";
	
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

	@Test(description = "Execute 'https://reqres.in/api/users' API to validate whether George is appearing in response.")
	public void verifyGeorgePresence() throws Exception {
		super.verifyUserName("George", HTTP_OK);
	}

	@Test(description = "Execute 'https://reqres.in/api/users' API to validate whether ID 2 is appearing in response.")
	public void verifyJanetID() throws Exception {
		super.verifyUserID(2, HTTP_OK);
	}

	@Test(description = "Execute 'https://reqres.in/api/users' API to validate whether last name  Wong is appearing in response.")
	public void verifyEmmaLastName() throws Exception {
		final Response getUsersResponse = ReqresUsersServices.getUsers(HTTP_OK);
		super.verifyUserDetails(getUsersResponse, "last_name", "Wong");
	}

	@Test(description = "Execute 'https://reqres.in/api/users' API to create new user using static JSON Object.")
	public void createNewUserTestByJsonObject() throws Exception {
		final JSONObject newUserJsonObj = TestUtilities.createJSONObject(NAME, "morpheus", JOB, "leader");
		// Create new user "morpheus"
		ReqresUsersServices.createNewUser(newUserJsonObj, HTTP_CREATED);
		// Get user details by get service
		final Response getUsersResponse = ReqresUsersServices.getUsers(HTTP_OK);

		// Since API is not reflecting the newly created users, hence below
		// assertions would fail.

		// Verify whether "morpheus" is added successfully
		super.verifyUserDetails(getUsersResponse, NAME, newUserJsonObj.get(NAME));
		// Verify whether morpheus's job 'leader' is added successfully
		super.verifyUserDetails(getUsersResponse, JOB, newUserJsonObj.get(JOB));
	}

	@Test(description = "Execute 'https://reqres.in/api/users' API to create new user using input JSON File.")
	public void createNewUserTestByJsonFile() throws Exception {
		String newUserJsonString = FileUtilities.getJSONFileString(ReqresUsersServicesTest.class, "template.user.json");
		JSONObject newUserJsonObj = new JSONObject(newUserJsonString);
		// Create new user "morpheus"
		ReqresUsersServices.createNewUser(newUserJsonObj, HTTP_CREATED);
		// Get user details by get service
		final Response getUsersResponse = ReqresUsersServices.getUsers(HTTP_OK);

		// Since API is not reflecting the newly created users , hence below
		// assertions would fail.

		// Verify whether "morpheus" is added successfully
		super.verifyUserDetails(getUsersResponse, NAME, newUserJsonObj.get(NAME));
		// Verify whether morpheus's job 'leader' is added successfully
		super.verifyUserDetails(getUsersResponse, JOB, newUserJsonObj.get(JOB));
	}

	@Test(description = "Execute 'https://reqres.in/api/users' API to create new user using static JSON Object and validate that new ID"
			+ "is created for newly created User.")
	public void createNewUserTestAndValidateResponse() throws Exception {
		final String name = "morpheus";
		final String job = "leader";
		final JSONObject newUserJsonObj = TestUtilities.createJSONObject(NAME, name, JOB, job);
		// Create new user "morpheus"
		final Response response = ReqresUsersServices.createNewUser(newUserJsonObj, HTTP_CREATED);
		assertNotNull(response.getBody().jsonPath().getString(ID), "new user ID generation is not successfull.");
		assertEquals(response.getBody().jsonPath().getString(NAME), name, "new user name is not added successfully.");
		assertEquals(response.getBody().jsonPath().getString(JOB), job, "new user job is not added successfully.");
	}

	@Test(description = "Execute 'https://reqres.in/api/users/2' API to update existing user with some data.")
	public void updateExistingUserTest() throws Exception {
		final String name = "morpheus";
		final String job = "zion resident";
		final JSONObject userDetailsToUpdateJsonObj = TestUtilities.createJSONObject(NAME, name, JOB, job);
		Response response = ReqresUsersServices.updateExistingUserDetails(userDetailsToUpdateJsonObj, 2, HTTP_OK);
		assertEquals(response.getBody().jsonPath().getString(NAME), name, "new user name is not updated successfully.");
		assertEquals(response.getBody().jsonPath().getString(JOB), job, "new user job is not updated successfully.");
	}

	@Test(description = "Execute 'https://reqres.in/api/users/2' API to delete existing user.")
	public void deleteExistingUserByIdTest() throws Exception {
		Response response = ReqresUsersServices.deleteExistingUserByID(2);
		assertEquals(response.getStatusCode(), HTTP_NO_CONTENT, "Status code does not match.");
	}

	@Test(description = "Execute 'https://reqres.in/api/users?page=2' API to get user details using query Param parameter"
			+ "and list details using java 8")
	public void getUserWithQueryParamTest() throws Exception {
		super.verifyUserDetailsForQueryParamUsingJava8();
	}

	@Test(description = "Execute 'https://reqres.in/api/users?page=2' API to get user details using query Param parameter"
			+ "and verify whether first name as 'Eve' and its ID 4 appears in response.")
	public void getUserWithQueryParamTestAndVerify() throws Exception {
		final Response getUsersResponse = ReqresUsersServices.getUsersWithQueryParam();
		super.verifyUserDetailsForGivenDetails(getUsersResponse, "Eve", 4);
	}

	@Test(description = "Execute 'https://reqres.in/api/users/23' API to validate that 23 ID does not present "
			+ "and return 404 not found error..")
	public void verifyNotExistedID() throws Exception {
		// Status code is not matching :Seems some issue in API, Ideally if 23
		// ID does not present then it should return 404.
		super.verifyUserID(23, HTTP_NOT_FOUND);
	}
}
