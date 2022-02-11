package gb7;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public class MyBookingDTO {
        @JsonProperty("firstname")
        private String first_Name;

        @JsonProperty("lastname")
        private String last_Name;
        private String totalprice;
        private String depositpaid;
        private String bookingdates;
        private String additionalneeds;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @lombok.Data
        public static class bookingdates {

            private String checkin;
            private String checkout;
        }
}
