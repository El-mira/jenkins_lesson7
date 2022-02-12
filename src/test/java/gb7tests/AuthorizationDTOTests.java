package gb7tests;
import gb7.MyAuthorizationDTO;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
public class AuthorizationDTOTests {
    @Test
    void authpositivetest() {
        MyAuthorizationDTO myAuthorizationDTO = new MyAuthorizationDTO();
        myAuthorizationDTO.setLogin("admin");
        myAuthorizationDTO.setPassword("password123");
        String token = given().log().all().contentType("application/json")
                .when().body(myAuthorizationDTO)
                .request("POST", "https://restful-booker.herokuapp.com/auth")
                .prettyPeek()
                .then().statusCode(200).extract()
                .response()
                .jsonPath()
                .getString("token");
    }
    @Test
    void authnegative1test() {
        MyAuthorizationDTO myAuthorizationDTO = new MyAuthorizationDTO();
        myAuthorizationDTO.setLogin("");
        myAuthorizationDTO.setPassword("password123");
        String token = given().log().all().contentType("application/json")
                .when().body(myAuthorizationDTO)
                .request("POST", "https://restful-booker.herokuapp.com/auth")
                .prettyPeek()
                .then().statusCode(200)
                .body("reason", CoreMatchers.equalTo("Bad credentials"))
                .extract()
                .response()
                .jsonPath()
                .getString("token");
    }
}
