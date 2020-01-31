package common;

import static common.Constants.BASE_URL;
import static common.Constants.REQUESTSPECIFICATION;
import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;

import io.restassured.response.Response;

public class ReqresRegisterServices {

	public static final String REGISTER = "register";

	public static Response register(JSONObject registrationDetailsJsonObj) throws JSONException {
		return given().spec(REQUESTSPECIFICATION).body(registrationDetailsJsonObj.toString()).when()
				.get(BASE_URL + REGISTER);
	}
}
