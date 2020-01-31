package stepdefinitions;

import static common.Constants.HTTP_CREATED;
import static common.Constants.HTTP_OK;
import static common.Constants.REQUESTSPECIFICATIONTOKEN;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import common.ReqresUsersServices;
import common.UserDetailsPojo;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import utilities.ExcelDataToDataTable;

public class ReqresUser {

	public Response response;

	public Map<String, Object> dataMap;

	@Given("^Get the response of unknown resource on end point 2 by Get Verbs.$")
	public void get_the_response_of_unknown_resource_on_end_point_2_by_Get_Verbs() throws Exception {
		response = ReqresUsersServices.getResourceDetailsByID(2, HTTP_OK);
	}

	@When("^Extract the response and store in variables.$")
	public void extract_the_response_and_store_in_variables() throws Exception {
		dataMap = response.getBody().jsonPath().getMap("data");
	}

	@Then("^Validate whether \"(.*)\" name is appearing in Response.$")
	public void validate_whether_name_is_appearing_in_Response(String expectedName) throws Exception {
		assertEquals(dataMap.get("name"), expectedName, "Expected Name does not appear in response.");
	}

	@Given("^Hit the post api \"(.*)\" using \"(.*)\"  token \"(.*)\" with below user details and verify that it returns \"(.*)\" status code\\.$")
	public void hit_the_post_api_using_token_with_below_user_details_and_verify_that_it_returns_status_code(
			String endpoint, String tokenType, String accessToken, String expectedStatusCode, DataTable dt)
			throws Throwable {
		validateCreateUsers(dt, endpoint);
	}

	@Given("^Hit the post api \"(.*)\" using \"(.*)\"  token \"(.*)\" with user details stored in excel at \"(.*)\" and sheet no (.*) . Also verify that it returns \"(.*)\" status code\\.$")
	public void hit_the_post_api_using_token_with_user_details_stored_in_excel_at_and_verify_that_it_returns_status_code(
			String endpoint, String tokenType, String accessToken, String excelPath, int sheetNo,
			String expectedStatusCode) throws Throwable {
		File file = new File(excelPath);
		excelPath = file.getAbsolutePath();
		ExcelDataToDataTable exclDataToDt = new ExcelDataToDataTable();
		DataTable dt = exclDataToDt.transform(excelPath);
		validateCreateUsers(dt, endpoint);
	}

	private void validateCreateUsers(DataTable dt, String endpoint) {
		List<UserDetailsPojo> userDetailsList = dt.asList(UserDetailsPojo.class);
		String randomNoForEmail = String.valueOf((int) new Random().nextInt());
		for (UserDetailsPojo userDetails : userDetailsList) {
			JSONObject jobject = new JSONObject();
			jobject.put("first_name", userDetails.getFirst_name());
			jobject.put("last_name", userDetails.getLast_name());
			jobject.put("gender", userDetails.getGender());
			String emailArray[] = userDetails.getEmail().split("@");
			String email = emailArray[0].concat(randomNoForEmail).concat("@").concat(emailArray[1]).trim();
			jobject.put("email", email);
			jobject.put("status", userDetails.getStatus());
			Response response = given().spec(REQUESTSPECIFICATIONTOKEN).body(jobject.toString()).when().post(endpoint);
			assertEquals(response.getBody().jsonPath().getInt("_meta.code"), HTTP_CREATED);
		}
	}

}
