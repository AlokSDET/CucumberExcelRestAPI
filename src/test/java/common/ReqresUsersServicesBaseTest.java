package common;

import static common.Constants.HTTP_OK;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public class ReqresUsersServicesBaseTest extends AbstractReqres {

	protected void verifyUserName(String userName, int expectedResponseCode) throws Exception {
		final Response getUsersResponse = ReqresUsersServices.getUsers(expectedResponseCode);
		
		assertEquals(getUsersResponse.getStatusCode(), expectedResponseCode, "Status code does not match");
		final List<String> listOfUserNames = getUsersResponse.getBody().jsonPath().getList("data.first_name");
		//Verify whether given name appear in response.
		assertTrue(listOfUserNames.contains(userName), userName + " does not appear in response of given API.");
	}

	protected void verifyUserLastName(String userLastName, int expectedResponseCode) throws Exception{
		final Response getUsersResponse = ReqresUsersServices.getUsers(expectedResponseCode);
		final List<String> listOfUserLastNames = getUsersResponse.getBody().jsonPath().getList("data.last_name");
		//Verify whether given last name appear in response.
		assertTrue(listOfUserLastNames.contains(userLastName),
				userLastName + " does not appear in response of given API.");
	}

	protected void verifyUserID(Integer userID, int expectedResponseCode) throws Exception{
		final Response getUsersResponse = ReqresUsersServices.getUsers(expectedResponseCode);
		assertEquals(getUsersResponse.getStatusCode(), expectedResponseCode, "status code does not match.");
		//Verify further details only if status code is 200 ok.
		if (getUsersResponse.getStatusCode() == HTTP_OK) {
			final JSONObject getUsersResponseJsonObj = new JSONObject(getUsersResponse.getBody().asString());
			
			final List<Integer> userIds = new ArrayList<Integer>();
			final JSONArray userDetailsJsonArray = getUsersResponseJsonObj.getJSONArray("data");
			for (int i = 0; i < userDetailsJsonArray.length(); i++) {
				userIds.add(userDetailsJsonArray.getJSONObject(i).getInt("id"));
			}
			assertTrue(userIds.contains(userID), userID + " does not appear in response of API.");
		}
	}
	
	public void verifyUserDetails(Response response, String userDataKey, Object expectedUserData) throws Exception{
		final JSONObject getUsersResponseJsonObj = new JSONObject(response.getBody().asString());
		final List<Object> userIds = new ArrayList<Object>();
		final JSONArray userDetailsJsonArray = getUsersResponseJsonObj.getJSONArray("data");
		for (int i = 0; i < userDetailsJsonArray.length(); i++) {
			userIds.add(userDetailsJsonArray.getJSONObject(i).getString(userDataKey));
		}
		assertTrue(userIds.contains(expectedUserData), expectedUserData + " does not appear in response of API.");
	}

	protected void verifyUserDetailsForQueryParamUsingJava8() throws Exception {
		final Response getUsersResponse = ReqresUsersServices.getUsersWithQueryParam();
		List<Map<String, Object>> userDataMapList = getUsersResponse.getBody().jsonPath().getList("data");

		// List down all the firstName from above response using Java 8
		userDataMapList.forEach(userDataMap -> System.out.println(userDataMap.get("first_name")));

		// List down all the ID's which are greater than 5 using java 8
		List<Object> idList = userDataMapList.stream().filter(userDataMap -> (int) userDataMap.get("id") > 5)
				.map(userDataMap -> userDataMap.get("id")).collect(Collectors.toList());
		idList.forEach(System.out::println); // Using method reference in java 8
	}
	
	/** This method will validate whether given name and ID is present in given response.
	 * @param getUsersResponse : Response
	 * @param firstName :  Name to validate whether present or not
	 * @param id : ID to validate whether present or not
	 * @throws Exception
	 */
	protected void verifyUserDetailsForGivenDetails(Response getUsersResponse, String firstName, Integer id) throws Exception {	
		List<Map<String, Object>> userDataMapList = getUsersResponse.getBody().jsonPath().getList("data");
		Iterator<Map<String, Object>> itr = userDataMapList.iterator();
		List<String> nameList = new LinkedList<>();
		List<Integer> idList = new LinkedList<>();
		while (itr.hasNext()) {
			Map<String, Object> userData = (Map<String, Object>) itr.next();
			nameList.add(userData.get("first_name").toString());
			idList.add((Integer)userData.get("id"));
		}	
		assertTrue(idList.contains(id), "Given " + id + " does not present.");
		assertTrue(nameList.contains(firstName), "Given " + firstName + " does not present.");
	}
}
