package elxrojo.account_service.TestsBackend;


import com.google.gson.JsonObject;
import static io.restassured.RestAssured.given;

public class AuthHelper {
    private static final String USER_BASE_URL = "http://localhost:8081/users/";

    public static String getAuthToken(String email, String password) {
        JsonObject loginData = new JsonObject();
        loginData.addProperty("email", email);
        loginData.addProperty("password", password);

        return given().contentType("application/json")
                .body(loginData.toString())
                .post(USER_BASE_URL + "login")
                .then()
                .statusCode(200)
                .extract().path("token");
    }
}