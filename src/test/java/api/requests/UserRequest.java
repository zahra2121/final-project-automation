package api.requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequest {
    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "63a804408eb0cb069b57e43a";

    public static Response getUserById(String id) {
        return given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .get("/user/" + id);
    }

    public static Response createUser(String firstName, String lastName, String email) {
        String body = String.format("{\"firstName\":\"%s\", \"lastName\":\"%s\", \"email\":\"%s\"}", firstName, lastName, email);
        return given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .contentType("application/json")
                .body(body)
                .post("/user/create");
    }

    public static Response updateUser(String id, String firstName) {
        String body = String.format("{\"firstName\":\"%s\"}", firstName);
        return given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .contentType("application/json")
                .body(body)
                .put("/user/" + id);
    }

    public static Response deleteUser(String id) {
        return given()
                .baseUri("https://dummyapi.io/data/v1")
                .header("app-id", "63a804408eb0cb069b57e43a")
                .delete("/user/" + id);
    }
}
