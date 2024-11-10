package elxrojo.user_service.TestBack;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class UserTests {

    private String baseUrl = "http://localhost:8081/users/";
    static public Integer dni;
    static public Integer phone;
    static public String email;
    static public String token;


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Order(1)
    class createUser {

        @Test
        @Order(1)
        public void positive() {
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);

            JsonObject userCreated = new JsonObject();
            userCreated.addProperty("firstName", "Test");
            userCreated.addProperty("lastName", uniqueId);
            dni = generateDni();
            userCreated.addProperty("dni", dni);
            email = "test" + uniqueId + "@gmail.com";
            userCreated.addProperty("email", email);
            phone = generatePhone();
            userCreated.addProperty("phone", phone);
            userCreated.addProperty("password", "123456789Lol");

            token = given().contentType("application/json")
                    .body(userCreated.toString())
                    .post(baseUrl)
                    .then()
                    .log().body()
                    .statusCode(201)
                    .body("accessToken", notNullValue())
                    .extract().path("accessToken");
        }

        @Test
        @Order(2)
        public void existingEmail() {
            JsonObject userCreated = new JsonObject();
            userCreated.addProperty("firstName", "Test");
            userCreated.addProperty("lastName", "FailedTest");
            Integer generatedDni = generateDni();
            userCreated.addProperty("dni", generatedDni);
            userCreated.addProperty("email", email);
            Integer generatedPhone = generatePhone();
            userCreated.addProperty("phone", generatedPhone);
            userCreated.addProperty("password", "123456789Lol");

            given().contentType("application/json")
                    .body(userCreated.toString())
                    .post(baseUrl)
                    .then()
                    .statusCode(400)
                    .body("details", equalTo("This email already exists!"));
        }

        @Test
        @Order(3)
        public void existingDNI() {

            JsonObject userCreated = new JsonObject();
            userCreated.addProperty("firstName", "Test");
            userCreated.addProperty("lastName", "FailedTest");
            userCreated.addProperty("dni", dni);
            userCreated.addProperty("email", "failedTest@gmail.com");
            Integer generatedPhone = generatePhone();
            userCreated.addProperty("phone", generatedPhone);
            userCreated.addProperty("password", "123456789Lol");

            given().contentType("application/json")
                    .body(userCreated.toString())
                    .post(baseUrl)
                    .then()
                    .statusCode(400)
                    .body("details", equalTo("This DNI already exists!"));
        }

        @Test
        @Order(4)
        public void existingPhone() {
            String uniqueId = UUID.randomUUID().toString().substring(0, 8);

            JsonObject userCreated = new JsonObject();
            userCreated.addProperty("firstName", "Test");
            userCreated.addProperty("lastName", "FailedTest");
            Integer generatedDni = generateDni();
            userCreated.addProperty("dni", generatedDni);
            userCreated.addProperty("email", "failedTest@gmail.com");
            userCreated.addProperty("phone", phone);
            userCreated.addProperty("password", "123456789Lol");

            given().contentType("application/json")
                    .body(userCreated.toString())
                    .post(baseUrl)
                    .then()
                    .statusCode(400)
                    .body("details", equalTo("This phone already exists!"));
        }
    }

    @Nested
    @Order(2)
    class login {

        @Test
        public void positive() {
            JsonObject dataLogin = new JsonObject();
            dataLogin.addProperty("email", email);
            dataLogin.addProperty("password", "123456789Lol");

            token = given().contentType("application/json")
                    .body(dataLogin.toString())
                    .post(baseUrl + "login")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("token", notNullValue())
                    .extract().path("token");
        }

        @Test
        public void invalidEmail() {
            JsonObject dataLogin = new JsonObject();
            dataLogin.addProperty("email", "asddaasddassdaasdadsdas@correo.com");
            dataLogin.addProperty("password", "123456789Lol");

            given().contentType("application/json")
                    .body(dataLogin.toString())
                    .post(baseUrl + "login")
                    .then()
                    .log().body()
                    .statusCode(404)
                    .body("details", equalTo("Non-existent user :/"));
        }
        @Test
        public void invalidPassword() {
            JsonObject dataLogin = new JsonObject();
            dataLogin.addProperty("email", email);
            dataLogin.addProperty("password", "jkashdaskjhdaskjhdas782782");

            given().contentType("application/json")
                    .body(dataLogin.toString())
                    .post(baseUrl + "login")
                    .then()
                    .log().body()
                    .statusCode(401)
                    .body("details", equalTo("Incorrect password :/"));
        }
        @Test
        public void incompleteLogin() {
            JsonObject dataLogin = new JsonObject();
            dataLogin.addProperty("email", "");
            dataLogin.addProperty("password", "");

            given().contentType("application/json")
                    .body(dataLogin.toString())
                    .post(baseUrl + "login")
                    .then()
                    .log().body()
                    .statusCode(400)
                    .body("details", equalTo("Incomplete login information"));
        }
    }


    //    Handler functions

    public Integer generateDni() {
        StringBuilder dniGenerated = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            dniGenerated.append((int) (Math.random() * 10));
        }
        return Integer.parseInt(String.valueOf(dniGenerated));
    }

    public Integer generatePhone() {
        StringBuilder phoneGenerated = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            phoneGenerated.append((int) (Math.random() * 10));
        }
        return Integer.parseInt(String.valueOf(phoneGenerated));
    }


}
