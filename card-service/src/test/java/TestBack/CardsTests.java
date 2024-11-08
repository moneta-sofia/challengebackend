package TestBack;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.YearMonth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class CardsTests {

    private String baseUrl = "http://localhost:8087";
    public static int accountId = 1;
    public static String cardNumber;
    public static Integer cardId;


    @Nested
    @Order(1)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class Post {

        @Test
        @Order(1)
        public void createCard_positive() {
            cardNumber = generateCardNumber() ;
            JsonObject request = new JsonObject();
            request.addProperty("name", "Test Test");
            request.addProperty("number", cardNumber);
            request.addProperty("cvc", generateCVV());
            request.addProperty("expirationDate", YearMonth.now().plusYears(2).toString());
            request.addProperty("accountId", accountId);

            given().contentType("application/json")
                    .body(request.toString())
                    .post(baseUrl + "/cards/")
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
                    .post(baseUrl + "/cards/")
                    .then()
                    .statusCode(400);
        }

    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Order(2)
    class Gets {


        @Test
        @Order(1)
        public void GetAll() {
            given()
                    .get(baseUrl + "/cards/")
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThan(1));
        }

        @Test
        @Order(2)
        public void GetAllByAccount() {
            given()
                    .get(baseUrl + "/cards/account/" + accountId)
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThan(1));
        }

        @Test
        @Order(3)
        public void GetByNumber() {
            int cardIdString = given()
                    .get(baseUrl + "/cards/number/" + cardNumber)
                    .then()
                    .statusCode(200)
                    .body("number", equalTo(cardNumber))
                    .log().body()
                    .extract().path("id");
            cardId = cardIdString;
        }


        @Nested
        @Order(4)
        class GetById {

            @Test
            public void positive() {
                given()
                        .get(baseUrl + "/cards/" + cardId + "/account/" + accountId)
                        .then()
                        .log().body()
                        .body("id", equalTo(cardId))
                        .statusCode(200);
            }

            @Test
            public void cardNotFound() {
                given()
                        .get(baseUrl + "/cards/" + 100000 + "/account/" + accountId)
                        .then()
                        .log().body()
                        .statusCode(404);
            }

            @Test
            public void unauthorized() {
                given()
                        .get(baseUrl + "/cards/" + cardId + "/account/" + 1000000)
                        .then()
                        .log().body()
                        .statusCode(401);
            }

        }

        @Test
        @Order(4)
        public void getAllByAccount() {
            given()
                    .get(baseUrl + "/cards/account/" + accountId)
                    .then()
                    .body("accountId[0]", equalTo(accountId))
                    .body("size()", greaterThan(1))
                    .statusCode(200);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Order(3)
    class Delete {

        @Test
        @Order(1)
        public void unauthorized() {
            given()
                    .delete(baseUrl + "/cards/" + cardId + "/account/" + 1000000)
                    .then()
                    .statusCode(401);
        }

        @Test
        @Order(2)
        public void positive() {
            given()
                    .delete(baseUrl + "/cards/" + cardId + "/account/" + accountId)
                    .then()
                    .statusCode(200);
        }

        @Test
        @Order(3)
        public void notFound() {
            given()
                    .delete(baseUrl + "/cards/" + cardId + "/account/" + accountId)
                    .then()
                    .statusCode(404);
        }


    }


//    Handler functions

    public String generateCVV() {
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append((int) (Math.random() * 10));
        }
        return cvv.toString();
    }

    public String generateCardNumber() {
        StringBuilder cardNumberGenerated = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumberGenerated.append((int) (Math.random() * 10));
        }
        return cardNumberGenerated.toString();
    }
}
