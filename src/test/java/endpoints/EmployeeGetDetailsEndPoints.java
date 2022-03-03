package endpoints;

import com.test.utils.LoadData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

public class EmployeeGetDetailsEndPoints extends BaseEndpoints {
    Response response;
    private final String EMPLOYEE_ENDPOINT_PATH = "employees/";
    LoadData load=new LoadData();

    public String getPath() {
        return this.EMPLOYEE_ENDPOINT_PATH;
    }

    public Response addPetWithBody(RequestSpecification request, String body) {
        String url = getBaseUrl() + this.getPath();
        request.body(body);
        return sendRequest(request, BaseEndpoints.POST_REQUEST, url, null, null);
    }

    public Response createOrder(RequestSpecification request, String empData) throws IOException {
        String url = getBaseUrl() + this.getPath();
        return sendRequest(request, BaseEndpoints.POST_REQUEST, url, empData, null);

    }

    public Response updateRequest(RequestSpecification request, String empData, String param) {
        String url = getBaseUrl() + this.getPath() + param;
        response = sendRequest(request, BaseEndpoints.PUT_REQUEST, url, empData, param);
        return sendRequest(request, BaseEndpoints.PUT_REQUEST, url, empData, param);

    }

    public Response deleteRequest(RequestSpecification request, String param) {
        String url = getBaseUrl() + this.getPath() + param;
        return response = sendRequest(request, BaseEndpoints.DELETE_REQUEST, url, null, null);

    }
    public Response getRequestwByID(RequestSpecification request, String param) {
        String url = getBaseUrl() + this.getPath() + param;
        return response = sendRequest(request, BaseEndpoints.GET_REQUEST, url, null, null);

    }
}
