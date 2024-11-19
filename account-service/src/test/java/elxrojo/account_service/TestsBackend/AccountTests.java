//package elxrojo.account_service.TestsBackend;
//
//
//import com.google.gson.JsonObject;
//import org.junit.jupiter.api.*;
//
//import java.time.YearMonth;
//import java.util.UUID;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//@TestClassOrder(ClassOrderer.OrderAnnotation.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class AccountTests {
//
//    private String baseUrl = "http://localhost:8085/accounts/";
//    static public String token;
//    static public Integer accountId = 1;
//    static public Integer transactionId;
//    static public Integer cardId;
//    static public String  cardNumber;
//    static public String userId = "5e855825-0402-401c-8e77-b709ff18aa0e"; //Change it each time the BDD is cleaned
//
//
//    @BeforeAll
//    public static void setup() {
//        token = AuthHelper.getAuthToken("test1@gmail.com", "123456789Lol");
//    }
//
//    @Nested
//    @Order(1)
//    class AccountMethods {
//
//
//        @Nested
//        @Order(1)
//        class getAccountByUser {
//
//            @Test
//            public void positive() {
//                given().get(baseUrl + userId)
//                        .then()
//                        .statusCode(200)
//                        .body("userId", equalTo(userId));
//            }
//
//            @Test
//            public void negative_notFound() {
//                String nonExistentUserId = UUID.randomUUID().toString();
//
//                given().get(baseUrl + nonExistentUserId)
//                        .then()
//                        .statusCode(404)
//                        .body("details", equalTo("Account not found"));
//            }
//        }
//
//        @Nested
//        @Order(2)
//        class updateAccount {
//
//            @Test
//            public void positive() {
//                JsonObject accountUpdate = new JsonObject();
//                accountUpdate.addProperty("balance", 11.4);
//
//                given().contentType("application/json")
//                        .body(accountUpdate.toString())
//                        .put(baseUrl + accountId)
//                        .then()
//                        .statusCode(200)
//                        .body("balance", equalTo(11.4F));
//            }
//
//            @Test
//            public void accountNotFound() {
//                JsonObject accountUpdate = new JsonObject();
//                accountUpdate.addProperty("balance", 11.4);
//
//                given().contentType("application/json")
//                        .body(accountUpdate.toString())
//                        .put(baseUrl + "213786334224342129")
//                        .then()
//                        .statusCode(404)
//                        .body("details", equalTo("Account not found"));
//            }
//
//            @Test
//            public void cannotChangeAccountID() {
//                JsonObject accountUpdate = new JsonObject();
//                accountUpdate.addProperty("id", 10000);
//
//                given().contentType("application/json")
//                        .body(accountUpdate.toString())
//                        .put(baseUrl + accountId)
//                        .then()
//                        .statusCode(400)
//                        .body("details", equalTo("Cannot change the account id!"));
//            }
//
//            @Test
//            public void cannotChangeUserID() {
//                JsonObject accountUpdate = new JsonObject();
//                accountUpdate.addProperty("userId", 10000);
//
//                given().contentType("application/json")
//                        .body(accountUpdate.toString())
//                        .put(baseUrl + accountId)
//                        .then()
//                        .statusCode(400)
//                        .body("details", equalTo("Cannot change the user id!"));
//            }
//
//        }
//
//        @Nested
//        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//        @Order(3)
//        class transactionTest {
//
//            @Nested
//            @Order(1)
//            class createTransaction {
//                @Test
//                public void positive() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .post(baseUrl + userId + "/transferences?amount=1000")
//                            .then()
//                            .statusCode(201);
//                }
//
//                @Test
//                public void unauthorized() {
//                    given()
//                            .header("Authorization", "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJZSnFwbjFqWkQ3Q3FjWEw5dEpSNmFmNzhWY2RFYzc2YW1vZHNGbDh6bG04In0.eyJleHAiOjE3MzAyNjI3NzEsImlhdCI6MTczMDI2MjQ3MSwianRpIjoiZTI2Njc2NTItNDdiOS00YjZjLWIwMzEtMjRkZTA5ZGViN2JmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9CYWNrZW5kQ2hhbGxlbmdlIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjkyZmE4OGYwLWNhYzktNDMwNC1hNDU0LTQ3N2U5NDllY2E0MSIsInR5cCI6IkJlYXJlciIsImF6cCI6IkZyb250LUJhbmsiLCJzaWQiOiI3YWE0ZGZjNy04M2I5LTQ2OTEtYTIzYi1mODZjNzBjYWYxZmIiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vY2hhbGxlbmdlYmFja2VuZC1mcm9udC52ZXJjZWwuYXBwLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWJhY2tlbmRjaGFsbGVuZ2UiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJUZXN0TW9kaWZpZWQgVGVzdDEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0MUBnbWFpbC5jb20iLCJnaXZlbl9uYW1lIjoiVGVzdE1vZGlmaWVkIiwiZmFtaWx5X25hbWUiOiJUZXN0MSIsImVtYWlsIjoidGVzdDFAZ21haWwuY29tIn0.3EvhEiWMGapMuxxISI9yllnL0Tovvewu5mCU-eOEm1J4sY-1mdgl5xww9QNeDwmZaj5FD9KKVM6Mu5cB8JV4malEov-lJAKHxTTy7i-OK94MB39KA3uqKTKXl8E4rdxLrdCfXeQwqpIz9ddEsJ1h73QE3k7eHIpaeqerAmuJZQdYke9w39x5EqZuB8pVKjRHf-bALOaOtoceoDQK8F2QA_ugdDBoUWsTML54GCCLbQBpGtnwA2uVk96HQKNJYaz_3L5BAqrO7IQIby5wRGOIBQCYNJAaUgQjd7g-gUjHCbpDzRdrdDCJu634Pd0XpwfenHHNOx2Y941unTHqZAOMGQ")
//                            .post(baseUrl + userId + "/transferences?amount=1000&transactionType=2")
//                            .then()
//                            .statusCode(403)
//                            .body("details", equalTo("Without permission to do this action"));
//                }
//
//                @Test
//                public void notFound() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .post(baseUrl + "90878979798" + "/transferences?amount=1000&transactionType=2")
//                            .then()
//                            .statusCode(403)
//                            .body("details", equalTo("Without permission to do this action"));
//                }
//            }
//
//            @Nested
//            @Order(2)
//            class getTransactionsByUser {
//
//                @Test
//                public void positive() {
//                    transactionId = given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + userId + "/activity")
//                            .then()
//                            .statusCode(200)
//                            .extract().path("id[0]");
//                }
//
//                @Test
//                public void positiveWithLimit() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + userId + "/activity?limit=1")
//                            .then()
//                            .statusCode(200)
//                            .body("size()", equalTo(1));
//                }
//
//            }
//
//            @Nested
//            @Order(3)
//            class getTransactionByUser {
//
//                @Test
//                public void positive() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + userId + "/activity/" + transactionId)
//                            .then()
//                            .statusCode(200)
//                            .body(notNullValue());
//                }
//
//                @Test
//                public void activityNotFound() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + userId + "/activity/" + "7631762373209")
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("That activity doesn't exist!"));
//                }
//                @Test
//                public void userNotFound() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + "823746247382" + "/activity/" + transactionId)
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("Account not found"));
//                }
//
//            }
//
//            @Nested
//            @Order(4)
//            class getLatestDestinations {
//
//                @Test
//                public void positive() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + userId + "/transferences")
//                            .then()
//                            .statusCode(200)
//                            .body("size()", greaterThanOrEqualTo(1));
//                }
//
//                @Test
//                public void withouthPermissions() {
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .get(baseUrl + "5e855825-0402-401c-8e77-b709ff1zzz0e" + "/transferences")
//                            .then()
//                            .statusCode(403)
//                            .body("size()", greaterThanOrEqualTo(1));
//                }
//            }
//
//        }
//
//
//        @Nested
//        @Order(4)
//        @TestClassOrder(ClassOrderer.OrderAnnotation.class)
//        class cardsTest {
//
//            @Nested
//            @Order(1)
//            @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//            class createCard {
//
//                @Order(1)
//                @Test
//                public void createCard_positive() {
//                    JsonObject cardRequest = new JsonObject();
//                    cardRequest.addProperty("name", "John Doe");
//                    cardNumber = generateCardNumber();
//                    cardRequest.addProperty("number", cardNumber);
//                    cardRequest.addProperty("cvc", "123");
//                    cardRequest.addProperty("expirationDate", YearMonth.now().plusYears(2).toString());
//
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .contentType("application/json")
//                            .body(cardRequest.toString())
//                            .post(baseUrl + userId + "/card")
//                            .then()
//                            .statusCode(201);
//                }
//
//                @Test
//                @Order(2)
//                public void createCard_cardAlreadyInUse() {
//                    JsonObject cardRequest = new JsonObject();
//                    cardRequest.addProperty("name", "John Doe");
//                    cardRequest.addProperty("number", cardNumber);
//                    cardRequest.addProperty("cvc", "123");
//                    cardRequest.addProperty("expirationDate", YearMonth.now().plusYears(2).toString());
//
//                    given()
//                            .header("Authorization", "Bearer " + token)
//                            .contentType("application/json")
//                            .body(cardRequest.toString())
//                            .post(baseUrl + userId + "/card")
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("Card already in use"));
//                }
//            }
//
//            @Nested
//            @Order(2)
//            class getAllCards {
//                @Test
//                public void positive() {
//                    cardId = given()
//                            .get(baseUrl + userId + "/card")
//                            .then()
//                            .statusCode(200)
//                            .body("size()", greaterThan(1))
//                            .extract().path("[-1].id");
//                    System.out.println(cardId);
//                }
//            }
//
//            @Nested
//            @Order(3)
//            class getCardById {
//
//                @Test
//                public void positive() {
//                    given()
//                            .get(baseUrl + userId + "/cards/" + cardId)
//                            .then()
//                            .statusCode(200)
//                            .body("number", equalTo(cardNumber));
//                }
//
//                @Test
//                public void userNotFound() {
//                    given()
//                            .get(baseUrl + "98345798354" + "/cards/" + cardId)
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("Account not found"));
//                }
//
//                @Test
//                public void cardNotFound() {
//                    given()
//                            .get(baseUrl + userId + "/cards/" + "98345798354")
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("Card not found with that id"));
//                }
//            }
//
//            @Nested
//            @Order(4)
//            class deleteCard {
//
//                @Test
//                public void positive() {
//                    given()
//                            .delete(baseUrl + userId + "/cards/" + cardId)
//                            .then()
//                            .statusCode(200);
//                }
//
//                @Test
//                public void userNotFound() {
//                    given()
//                            .delete(baseUrl + "98345798354" + "/cards/" + cardId)
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("Account not found"));
//                }
//
//                @Test
//                public void cardNotFound() {
//                    given()
//                            .get(baseUrl + userId + "/cards/" + "98345798354")
//                            .then()
//                            .statusCode(404)
//                            .body("details", equalTo("Card not found with that id"));
//                }
//            }
//        }
//    }
//    public String generateCardNumber() {
//
//        StringBuilder cardNumberGenerated = new StringBuilder();
//        for (int i = 0; i < 16; i++) {
//            cardNumberGenerated.append((int) (Math.random() * 10));
//        }
//        return cardNumberGenerated.toString();
//    }
//
//}
