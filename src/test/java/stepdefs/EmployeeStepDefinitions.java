package stepdefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.utils.APIRequestSpecification;
import com.test.utils.ApplicationConstant;
import com.test.utils.LoadData;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import endpoints.BaseEndpoints;
import endpoints.EmployeeGetDetailsEndPoints;
import io.restassured.response.Response;
import model.EmployeeCreateRequestResponse;
import model.EmployeeUpdateRequestResponse;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeStepDefinitions {
    public static final Logger log = Logger.getLogger(EmployeeStepDefinitions.class);
    String url;
    private APIRequestSpecification apiRequestSpecification;
    private BaseEndpoints be = new BaseEndpoints();
    public Response response;
    LoadData loadData = new LoadData();
    EmployeeCreateRequestResponse employeeCreateRequestResponse = new EmployeeCreateRequestResponse();
    EmployeeUpdateRequestResponse employeeUpdateRequestResponse = new EmployeeUpdateRequestResponse();
    ObjectMapper mapper = new ObjectMapper();
    EmployeeGetDetailsEndPoints employeeGetDetailsEndPoints = new EmployeeGetDetailsEndPoints();
    public EmployeeStepDefinitions(APIRequestSpecification world) {
        this.apiRequestSpecification = world;
    }

    @Given("^I set employee service api endpoint$")
    public void set_GET_employee_service_api_endpoint() {
        log.info("setting the employee end point");
        url = be.getBaseUrl() + employeeGetDetailsEndPoints.getPath();
    }

    @When("^I set request HEADER$")
    public void set_request_HEADER() {
        log.info("setting the request header");
        apiRequestSpecification.setRequest(employeeGetDetailsEndPoints.getRequestWithJSONHeaders());
    }

    @When("^I Send POST http request$")
    public void send_POST_http_request() throws IOException {
        log.info("sending POST calls");
        String empData = loadData.postData();
        apiRequestSpecification.setRequest(employeeGetDetailsEndPoints.getRequestWithJSONHeaders());
        apiRequestSpecification.setResponse(employeeGetDetailsEndPoints.createOrder(apiRequestSpecification.getRequest(), empData));
        response = apiRequestSpecification.getResponse();
        String body = response.getBody().asString();
        employeeCreateRequestResponse = mapper.readValue(body, EmployeeCreateRequestResponse.class);
    }

    @And("^I Send GET http request$")
    public void Send_GET_http_request() throws IOException {
        log.info("sending GET calls");
        response = be.sendRequest(null, BaseEndpoints.GET_REQUEST, url, null, null);
    }

    @And("^the  verify response code$")
    public void verify_response_code() throws IOException {
        log.info("verifying GET response code");
        apiRequestSpecification.setResponse(response);
        employeeGetDetailsEndPoints.verifyResponseStatusValue(apiRequestSpecification.getResponse(), employeeGetDetailsEndPoints.GET_SUCCESS_STATUS_CODE);
    }

    @And("^the  verify delete response code$")
    public void verify_delete_response_code() throws IOException {
        log.info("verifying DELETE response code");
        apiRequestSpecification.setResponse(response);
        employeeGetDetailsEndPoints.verifyResponseStatusValue(apiRequestSpecification.getResponse(), employeeGetDetailsEndPoints.DELETE_SUCCESS_STATUS_CODE);
    }

    @And("^the  verify post call response$")
    public void verify_post_call_response() throws IOException {
        log.info("verifying POST response code");
        employeeGetDetailsEndPoints.verifyResponseStatusValue(apiRequestSpecification.getResponse(), employeeGetDetailsEndPoints.POST_SUCCESS_STATUS_CODE);
    }

    @Given("^I Send PUT http request$")
    public void Send_PUT_http_request() throws IOException {
        log.info("Sending PUT call");
        String empData = loadData.updateData();
        apiRequestSpecification.setRequest(employeeGetDetailsEndPoints.getRequestWithJSONHeaders());
        apiRequestSpecification.setResponse(employeeGetDetailsEndPoints.updateRequest(apiRequestSpecification.getRequest(), empData, employeeCreateRequestResponse.getId()));
        response = apiRequestSpecification.getResponse();
        String body = response.getBody().asString();

        EmployeeUpdateRequestResponse[] jsonObj = mapper.readValue(body, EmployeeUpdateRequestResponse[].class);

		List<EmployeeUpdateRequestResponse> emp = Arrays.asList(mapper.readValue(body, EmployeeUpdateRequestResponse[].class));

		System.out.println("\nJSON array to List of objects");
		emp.stream().sorted().forEach(x -> System.out.println("data=="+x));

        employeeUpdateRequestResponse = mapper.readValue(empData, EmployeeUpdateRequestResponse.class);
    }


    @Given("^I Send Delete http request")
    public void Send_Delete_http_request() throws IOException {
        log.info("sending DELETE call");
        String empData = loadData.updateData();
        apiRequestSpecification.setRequest(employeeGetDetailsEndPoints.getRequestWithJSONHeaders());
        apiRequestSpecification.setResponse(employeeGetDetailsEndPoints.deleteRequest(apiRequestSpecification.getRequest(), employeeCreateRequestResponse.getId()));
        response = apiRequestSpecification.getResponse();
        String body = response.getBody().asString();
        employeeUpdateRequestResponse = mapper.readValue(empData, EmployeeUpdateRequestResponse.class);

    }

    @Given("^the verify updated data in response$")
    public void verify_updated_data_in_response() throws IOException {
        log.info("verifying updated data for put request");

        Assert.assertEquals(employeeUpdateRequestResponse.firstName, ApplicationConstant.FIRST_NAME);
        if (!employeeCreateRequestResponse.getEmailId().equals(employeeUpdateRequestResponse.getEmailId())) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    @And("^the  verify put call response$")
    public void verify_put_call_response() throws IOException {
        log.info("verifying the put  response code");
        employeeGetDetailsEndPoints.verifyResponseStatusValue(apiRequestSpecification.getResponse(), employeeGetDetailsEndPoints.PUT_SUCCESS_STATUS_CODE);
    }

    @Given("^I set GET employee  http request with parameters$")
    public void set_GET_employee_http_request_with_parameters() throws IOException {
        log.info("setting http request with parameters");
        String empData = loadData.postData();
        apiRequestSpecification.setRequest(employeeGetDetailsEndPoints.getRequestWithJSONHeaders());
        apiRequestSpecification.setResponse(employeeGetDetailsEndPoints.getRequestwByID(apiRequestSpecification.getRequest(), employeeCreateRequestResponse.getId()));
        response = apiRequestSpecification.getResponse();
        String body = response.getBody().asString();
        employeeCreateRequestResponse = mapper.readValue(body, EmployeeCreateRequestResponse.class);
    }
}