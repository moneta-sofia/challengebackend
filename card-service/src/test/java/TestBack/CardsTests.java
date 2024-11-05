package TestBack;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import java.time.YearMonth;

import static io.restassured.RestAssured.given;

public class CardsTests {
    public static String userIDTest = "43783fa8-1c8a-4ba5-aec3-f31cdcbae18a";
    public static Long accountID = 1L;
    public static String cardNumber;


    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class Post {

        @Test
        @Order(1)
        public void createCard_positive() {
//          Generating a new card number
            StringBuilder cardNumberGenerated = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                cardNumberGenerated.append((int) (Math.random() * 10));
            }
            cardNumber = cardNumberGenerated.toString();

            StringBuilder cvv = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                cvv.append((int) (Math.random() * 10));
            }

            JsonObject request = new JsonObject();
            request.addProperty("name", "Test Test");
            request.addProperty("number", cardNumberGenerated.toString());
            request.addProperty("cvc", cvv.toString());
            request.addProperty("expirationDate", YearMonth.now().plusYears(2).toString());
            request.addProperty("accountId", accountID);

            given().contentType("application/json")
                    .body(request.toString())
                    .post("http://localhost:8087/cards/")
                    .then()
                    .statusCode(201);
        }

        @Test
        @Order(2)
        public void createCard_cardInUse() {
            JsonObject request = new JsonObject();
            request.addProperty("name", "Test Test");
            request.addProperty("number", cardNumber);
            request.addProperty("cvc", "333");
            request.addProperty("expirationDate", "2026-12");
            request.addProperty("accountId", "1");

            given().contentType("application/json")
                    .body(request.toString())
                    .post("http://localhost:8087/cards/")
                    .then()
                    .statusCode(400);
        }

    }

    @Nested
    class Gets {

        @Test
        public void GetAll() {
            given().get("http://localhost:8087/cards/").then().statusCode(200);
        }

        @Test
        public void GetAllByAccount() {
            given()
                    .get("http://localhost:8087/cards/account/" + accountID)
                    .then()
                    .statusCode(200);
        }

    }


}
