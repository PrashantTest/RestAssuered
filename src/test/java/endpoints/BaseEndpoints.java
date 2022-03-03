package endpoints;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseEndpoints {
	public static final int GET_SUCCESS_STATUS_CODE = 200;
	public static final int POST_SUCCESS_STATUS_CODE = 201;
	public static final int PUT_SUCCESS_STATUS_CODE = 200;
	public static final int DELETE_SUCCESS_STATUS_CODE = 204;
	public static final int GET_REQUEST = 0;
	public static final int POST_REQUEST = 1;
	public static final int DELETE_REQUEST = 2;
	public static final int PUT_REQUEST = 3;

	protected final String base_url_employee ="http://localhost:8080/api/v1/";

	public void verifyResponseStatusValue(Response response, int expectedCode) {

		assertThat(response.getStatusCode(), is(expectedCode));
	}

	public String getBaseUrl() {
		return this.base_url_employee;
	}
	public RequestSpecification getRequestWithJSONHeaders() {
		RequestSpecification r = RestAssured.given();
		r.header("Content-Type", "application/json");
		return r;
	}

	public RequestSpecification getRequestWithXMLHeaders() {
		RequestSpecification r = RestAssured.given();
		r.header("Content-Type", "application/xml");
		return r;
	}
	protected JSONObject createJSONPayload(Object pojo) {
		return new JSONObject(pojo);
	}
	public Response sendRequest(RequestSpecification request, int requestType, String url, Object pojo,String param) {
		Response response = null;
		if (null != pojo) {
			String payload = pojo.toString();
			request.body(payload);
		}
		// need to add a switch based on request type
		switch (requestType) {
		case BaseEndpoints.GET_REQUEST:
			if (null == request) {
				response = RestAssured.when().get(url);
			} else {
				response = request.get(url);
			}
			break;
		case BaseEndpoints.POST_REQUEST:
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			break;
		case BaseEndpoints.DELETE_REQUEST:
			if (null == request) {
				response = RestAssured.when().delete(url);
			} else {
				response = request.delete(url);
			}
			break;
		case BaseEndpoints.PUT_REQUEST:
			if (null == request) {
				response = RestAssured.when().put(url);
			} else {
				response = request.put(url);
			}
			break;
		default:
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			response = request.post(url);
			break;
		}

		return response;
	}
}
