package api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertNotNull;

public class TagApiStep {
    public Response response;
    public static final String BASE_URL = "https://dummyapi.io/data/v1";
    public static final String APP_ID = "63a804408eb0cb069b57e43a";

    private void request() {
        response = (Response) given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a");
    }


    @When("user requests all tags")
    public void getAllTags() {
        System.out.println("➡️ Mengirim GET ke: " + BASE_URL + "/tag");

        response = given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .contentType("application/json")
                .get("/tag")
                .then()
                .extract()
                .response();

        System.out.println("📥 Response diterima dengan status: " + response.getStatusCode());
    }

    @Then("response status code should be {int}")
    public void responseShouldContain(int statusCode) {
        if (response == null) {
            throw new AssertionError("❌ ERROR: response is null. Pastikan App ID valid dan endpoint benar.");
        }

        System.out.println("✅ Actual status: " + response.getStatusCode());
        System.out.println("📝 Body: " + response.getBody().asString());

        response.then().statusCode(statusCode);
    }
}
