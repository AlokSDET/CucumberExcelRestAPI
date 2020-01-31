package common;

import static common.Constants.REQUESTSPECIFICATION;
import static common.Constants.BASE_URL;
import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import io.restassured.response.Response;

public class ReqresUsersServices {

	public static final String USERS = "users";
	
	public static Response getUserDetailsByID(String userId, int expectedResponseCode) throws Exception {
		return given().spec(REQUESTSPECIFICATION).expect().statusCode(expectedResponseCode).when()
				.get(BASE_URL + USERS + "/" + userId);
	}

	public static Response createNewUser(JSONObject userDetailsJsonObj, int expectedResponseCode) throws Exception {
		return given().spec(REQUESTSPECIFICATION).body(userDetailsJsonObj.toString()).expect()
				.statusCode(expectedResponseCode).when().post(BASE_URL + USERS);
	}

	public static Response updateExistingUserDetails(JSONObject userDetailsToUpdateJsonObj, Integer endPoint,
			int expectedResponseCode) throws Exception {
		return given().spec(REQUESTSPECIFICATION).body(userDetailsToUpdateJsonObj.toString()).expect()
				.statusCode(expectedResponseCode).when().put(BASE_URL + USERS + "/" + endPoint);
	}

	public static Response deleteExistingUserByID(Integer userIDToDelete) throws Exception {
		return given().spec(REQUESTSPECIFICATION).when().delete(BASE_URL + USERS + "/" + userIDToDelete);
	}

	public static Response getUsers(int expectedResponseCode) throws Exception {
		return given().spec(REQUESTSPECIFICATION).expect().statusCode(expectedResponseCode).when()
				.get(BASE_URL + USERS);
	}
	
	public static Response getUsersWithQueryParam() throws Exception {
		return given().spec(REQUESTSPECIFICATION).queryParam("page", 2).when()
				.get(BASE_URL + USERS);
	}

	public static Response getResourceDetailsByID(Integer resourceID, int expectedResponseCode) throws Exception {
		return given().spec(REQUESTSPECIFICATION).expect().statusCode(expectedResponseCode).when()
				.get(BASE_URL + "unknown" + "/" + resourceID);
	}

}
