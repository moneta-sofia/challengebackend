package elxrojo.account_service.TestsBackend;


import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountTests {

    private String baseUrl = "http://localhost:8085/accounts/";
    static public String token;
    static public Integer accountId = 1;
    static public String userId = "43783fa8-1c8a-4ba5-aec3-f31cdcbae18a"; //Change it each time the BDD is cleaned
    static public String cvu;




    @BeforeAll
    public static void setup() {
        token = AuthHelper.getAuthToken("test1@gmail.com", "123456789Lol");
    }

    @Nested
    @Order(1)
    class AccountMethods {


        @Nested
        @Order(1)
        class getAccountByUser {

            @Test
            public void positive() {
                given().get(baseUrl + userId)
                        .then()
                        .statusCode(200)
                        .body("userId", equalTo(userId));
            }

            @Test
            public void negative_notFound() {
                String nonExistentUserId = UUID.randomUUID().toString();

                given().get(baseUrl + nonExistentUserId)
                        .then()
                        .statusCode(404)
                        .body("details", equalTo("Account not found"));
            }
        }

        @Nested
        @Order(2)
        class updateAccount {

            @Test
            public void positive() {
                JsonObject accountUpdate = new JsonObject();
                accountUpdate.addProperty("balance", 11.4);

                given().contentType("application/json")
                        .body(accountUpdate.toString())
                        .put(baseUrl + accountId)
                        .then()
                        .statusCode(200)
                        .body("balance", equalTo(11.4));
            }

            @Test
            public void accountNotFound() {
                JsonObject accountUpdate = new JsonObject();
                accountUpdate.addProperty("balance", 11.4);

                given().contentType("application/json")
                        .body(accountUpdate.toString())
                        .put(baseUrl + "21378633422434212783")
                        .then()
                        .statusCode(404)
                        .body("details", equalTo("Account not found"));
            }

            @Test
            public void cannotChangeAccountID() {
                JsonObject accountUpdate = new JsonObject();
                accountUpdate.addProperty("id", 10000);

                given().contentType("application/json")
                        .body(accountUpdate.toString())
                        .put(baseUrl + accountId)
                        .then()
                        .statusCode(400)
                        .body("details", equalTo("Cannot change the account id!"));
            }

            @Test
            public void cannotChangeUserID() {
                JsonObject accountUpdate = new JsonObject();
                accountUpdate.addProperty("accountId", 10000);

                given().contentType("application/json")
                        .body(accountUpdate.toString())
                        .put(baseUrl + accountId)
                        .then()
                        .statusCode(400)
                        .body("details", equalTo("Cannot change the user id!"));
            }

        }

        @Nested
        @Order(3)
        class transactionTest{

            @Nested
            class createTransaction {
                @Test
                public void positive() {
                    given().contentType("application/json")
                            .header("Authorization", "Bearer " + token)
                            .queryParam("amount", 1000)
                            .queryParam("transactionType", 2)
                            .queryParam("destination", "0563448657741817913221")
                            .post(baseUrl + userId + "/transferences")
                            .then()
                            .statusCode(201);

                }
                @Test
                public void unauthorized() {
                    given().contentType("application/json")
                            .header("Authorization", "Bearer " + "invalidToken")
                            .queryParam("amount", 1000)
                            .queryParam("transactionType", 2)
                            .queryParam("destination", "0563448657741817913221")
                            .post(baseUrl + userId + "/transferences")
                            .then()
                            .statusCode(403)
                            .body("details", equalTo("Without permission to do this action"));
                }
            }



        }



            @Nested
        @Order(4)
        class cardsTest{

        }
    }

}
