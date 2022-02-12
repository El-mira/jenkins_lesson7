package gb7tests;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
public class BookingPositiveTests {
    @Test
    void bookingPositiveTest() {
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstname\" : \"Альберт\",\n" +
                        "    \"lastname\" : \"Ноббс\",\n" +
                        "    \"totalprice\" : 155,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2022-01-25\",\n" +
                        "        \"checkout\" : \"2022-02-03\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .log()
                .method()
                .log()
                .uri()
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("booking.bookingdates.checkin", CoreMatchers.equalTo("2022-01-25"))
                .body("booking.firstname", CoreMatchers.equalTo("Альберт"));
    }
    @Test
    void getBookingTest() {
        given()
                .header("Content-Type", "application/json")
                .log()
                .method()
                .log()
                .uri()
                .expect()
                .when()
                .get("https://restful-booker.herokuapp.com/booking")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void bookingNegative7Test() {
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstname\" : \"Альберт\" ,\n" +
                        "    \"lastname\" : \"Ноббс\",\n" +
                        "    \"totalprice\" : 155,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2022-01-35\",\n" +
                        "        \"checkout\" : \"2022-02-03\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body(CoreMatchers.equalTo("Invalid date"));
    }
}
