package elxrojo.user_service.TestBack;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class UserTests {

    private String baseUrl = "http://localhost:8081/users/";
    static public Integer dni;
    static public Integer phone;

    @Nested
    @Order(1)
    @TestClassOrder(ClassOrderer.OrderAnnotation.class)
    class createUser{

        @Test
        @Order(1)
        public void positive(){

            JsonObject userCreated = new JsonObject();
            userCreated.addProperty("firstName", "Test");
            userCreated.addProperty("lastName", "Test");
            dni = generateDni();
            userCreated.addProperty("dni", dni);
            userCreated.addProperty("email", "test1@gmail.com");
            phone = generatePhone();
            userCreated.addProperty("phone", phone);
            userCreated.addProperty("password", "123456789Lol");

            given().contentType("application/json")
                    .body(userCreated.toString())
                    .post(baseUrl)
                    .then()
                    .log().body()
                    .statusCode(201)
                    .body("token", notNullValue());
        }


    }



//    Handler functions

    public Integer generateDni() {
        StringBuilder dniGenerated = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            dniGenerated.append((int) (Math.random() * 10));
        }
        return Integer.parseInt(String.valueOf(dniGenerated)) ;
    }

    public Integer generatePhone() {
        StringBuilder phoneGenerated = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            phoneGenerated.append((int) (Math.random() * 10));
        }
        return Integer.parseInt(String.valueOf(phoneGenerated)) ;
    }


}
