package common;

import static common.Constants.REQUESTSPECIFICATION;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Constants  {
	
	public static final String EMPTY_STRING = "";

	// HTTP RESPONSE CODES CONSTANTS
	public static final int HTTP_INTERNAL_SERVER_ERROR = 500;

	public static final int HTTP_NOT_FOUND = 404;

	public static final int HTTP_BAD_REQUEST = 400;

	public static final int HTTP_METHOD_NOT_ALLOWED = 405;

	public static final int HTTP_NOT_ACCEPTABLE = 406;

	public static final int HTTP_OK = 200;

	public static final int HTTP_CREATED = 201;

	public static final int HTTP_NO_CONTENT = 204;

	// Request Specification constants can be created here as per Header Type.
	public static final RequestSpecification REQUESTSPECIFICATION = new RequestSpecBuilder()
			.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").build();


	public static final RequestSpecification REQUESTSPECIFICATIONTOKEN = new RequestSpecBuilder()
			.addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer X-YJbVxrlRM9JubL8ZoUBbKNuZoOZVmVsU_g").build();
	
	public static final RequestSpecification REQUESTSPECIFICATIONFORXML = new RequestSpecBuilder()
			.addHeader("Accept", "text/xml").addHeader("Content-Type", "text/xml").build();
	
	public static final String BASE_URL = InterfaceReqres.buildServiceURL();
}
