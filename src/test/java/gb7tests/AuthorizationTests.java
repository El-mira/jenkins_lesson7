package gb7tests;
import gb7.MyAuthorizationDTO;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;
public class AuthorizationTests {
    static Properties properties;
    static String host1;
    @BeforeAll
    static void beforeAll() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("src/test/application.properties"));
        host1 = properties.getProperty("host1","https://restful-booker.herokuapp.com/");
        RestAssured.baseURI = host1;
    }
    @Test
    void AccountInfoTest() {
        MyAuthorizationDTO myAuthorizationDTO = new MyAuthorizationDTO();
        myAuthorizationDTO.setLogin("admin");
        myAuthorizationDTO.setPassword("password123");
        String token = given().log().all().contentType("application/json")
                .when().body(myAuthorizationDTO)
                .post("auth")
                .prettyPeek()
                .then().statusCode(200).extract()
                .response()
                .jsonPath()
                .getString("token");
    }
    @Test
    void AuthorizationNegative1Test() {
        MyAuthorizationDTO myAuthorizationDTO = new MyAuthorizationDTO();
        myAuthorizationDTO.setLogin("");
        myAuthorizationDTO.setPassword("password123");
        given()
                .header("Content-Type", "application/json")
                .body(myAuthorizationDTO)
                .log()
                .uri()
                .when()
                .post("auth")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("reason", CoreMatchers.equalTo("Bad credentials"));
    }
    @Test
    void AuthorizationNegative2Test() {
        MyAuthorizationDTO myAuthorizationDTO = new MyAuthorizationDTO();
        myAuthorizationDTO.setLogin("admin");
        myAuthorizationDTO.setPassword("");
        given()
                .header("Content-Type", "application/json")
                .body(myAuthorizationDTO)
                .log()
                .uri()
                .when()
                .post("auth")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("reason", CoreMatchers.equalTo("Bad credentials"));
    }
    @Test
    void AuthorizationNegative3Test() {
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\" : ,\n" +
                        "    \"password\" : \n" +
                        "}")
                .log()
                .uri()
                .when()
                .post("auth")
                .prettyPeek()
                .then()
                .statusCode(400)
                .body(CoreMatchers.equalTo("Bad Request"));
    }
    @Test
    void AuthorizationNegative4Test() {
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\" : \"admin123\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .log()
                .uri()
                .when()
                .post("auth")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("reason", CoreMatchers.equalTo("Bad credentials"));
    }
    @Test
    void AuthorizationNegative5Test() {
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"word123\"\n" +
                        "}")
                .log()
                .uri()
                .when()
                .post("auth")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("reason", CoreMatchers.equalTo("Bad credentials"));
    }
    @Test
    void AuthorizationNegative9Test() {
        MyAuthorizationDTO myAuthorizationDTO = new MyAuthorizationDTO();
        myAuthorizationDTO.setLogin("");
        myAuthorizationDTO.setPassword("password123");
        given()
                .header("Content-Type", "application/json")
                .body(myAuthorizationDTO)
                .log()
                .uri()
                .when()
                .post("auth")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("reason", CoreMatchers.equalTo("Bad credentials"));
    }
}
