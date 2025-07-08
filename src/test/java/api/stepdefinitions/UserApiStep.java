package api.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;

public class UserApiStep {
    public Response response;
    public static final String BASE_URL = "https://dummyapi.io/data/v1";
    public static final String APP_ID = "63a804408eb0cb069b57e43a";


    @Given("I set base API endpoint")
    public void iSetBaseAPIEndpoint() {
        baseURI = "https://dummyapi.io/data/v1";
    }

    @When("I send GET request to user by ID {string}")
    public void iSendGETRequestToUserByID(String id) {
        response = given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .get("/user/" + id)
                .then()
                .extract()
                .response();

    }

    @And("response should contain {string}")
    public void responseShouldContain(String field) {
        assertNotNull("Response is null", response);
        response.then().body("$", hasKey(field));
    }

    @When("I send POST request to create user with firstName {string}, lastName {string}, email {string}")
    public void iSendPOSTRequestToCreateUserWithFirstNameLastNameEmail(String firstName, String lastName, String email) {
        String body = String.format("{\"firstName\":\"%s\", \"lastName\":\"%s\", \"email\":\"%s\"}", firstName, lastName, email);
        response = given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .contentType("application/json")
                .body(body)
                .post("/user/create")
                .then()
                .extract()
                .response();
    }

    @When("I send PUT request to user ID {string} with name {string}")
    public void iSendPUTRequestToUserIDWithName(String id, String firstName) {
        String body = String.format("{\"firstName\":\"%s\"}", firstName);
        response = given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .contentType("application/json")
                .body(body)
                .put("/user/" + id)
                .then()
                .extract()
                .response();
    }

    @When("I send DELETE request to user by ID {string}")
    public void iSendDELETERequestToUserByID(String id) {
        response = given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .delete("/user/" + id)
                .then()
                .extract()
                .response();
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        if (response == null) {
            throw new AssertionError("❌ ERROR: response is null. Pastikan App ID valid dan endpoint benar.");
        }

        System.out.println("✅ Actual status: " + response.getStatusCode());
        System.out.println("📝 Body: " + response.getBody().asString());

        response.then().statusCode(statusCode);
    }
}
