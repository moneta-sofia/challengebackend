package elxrojo.transaction_service.TestBack;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class TransactionTests {
    static private String baseUrl = "http://localhost:8086/transactions/";
    static public Integer accountId = 1;
    static public Integer transactionId;


    @Test
    @Order(1)
    public void createTransaction() {
        given().post(baseUrl + "create?amount=1000&transactionType=2&origin=3903064887278333808094&name=John+Doe&destination=0563448657741817913221&accountId=" + accountId)
                .then()
                .log().body()
                .statusCode(201);
    }

    @Nested
    @Order(2)
    class getTransactionsByAccount {

        @Test
        public void positive() {
            transactionId = given().get(baseUrl + accountId)
                    .then()
                    .statusCode(200)
                    .body("size()", greaterThanOrEqualTo(1))
                    .extract().path("id[0]");
        }

        @Test
        public void invalidAccountId() {
            given().get(baseUrl)
                    .then()
                    .statusCode(500);
        }

        @Test
        public void positiveWithLimit() {
            given().get(baseUrl + accountId + "?limit=1")
                    .then()
                    .statusCode(200)
                    .body("size()", equalTo(1));
        }
    }

    @Nested
    @Order(3)
    class getTransactionByAccount {

        @Test
        public void positive() {
            given()
                    .get(baseUrl + transactionId + "/account/" + accountId)
                    .then()
                    .statusCode(200)
                    .log().body()
                    .body("id", equalTo(transactionId));
        }

        @Test
        public void invalidAccountId() {
            given().get(baseUrl + 100000000 + "/account/" + accountId)
                    .then()
                    .statusCode(404);
        }

        @Test
        public void unauthorized() {
            given().get(baseUrl + transactionId + "/account/" + 5)
                    .then()
                    .statusCode(401);
        }
    }
}
