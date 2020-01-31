package common;

import static common.Constants.BASE_URL;
import static common.Constants.REQUESTSPECIFICATION;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;

import io.restassured.response.Response;

public class ReqresLoginServices {

	public static Response login(JSONObject loginDetailsJsonObj) throws JSONException {
		return given().spec(REQUESTSPECIFICATION).body(loginDetailsJsonObj.toString()).when()
				.get(BASE_URL + "login");
	}
}
