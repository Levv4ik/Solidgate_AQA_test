package tests.test_data;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequest {
    private Order order;
    private PageCustomization page_customization;

    public String toJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Перетворюємо об'єкт в JSON строку
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // В разі помилки повертаємо null
        }
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        private String order_id;
        private int amount;
        private String currency;
        private String order_description;
        private String order_items;
        private String order_date;
        private int order_number;
        private String type;
        private int settle_interval;
        private boolean force3ds;
        private List<String> google_pay_allowed_auth_methods;
        private String customer_date_of_birth;
        private String customer_email;
        private String customer_first_name;
        private String customer_last_name;
        private String customer_phone;
        private String traffic_source;
        private String transaction_source;
        private String purchase_country;
        private String geo_country;
        private String geo_city;
        private String language;
        private String website;
        private OrderMetadata order_metadata;
        private String success_url;
        private String fail_url;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderMetadata {
        private String coupon_code;
        private String partner_id;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageCustomization {
        private String public_name;
        private String order_title;
        private String order_description;
        private List<String> payment_methods;
        private String button_font_color;
        private String button_color;
        private String font_name;
        private boolean is_cardholder_visible;
        private String terms_url;
        private String back_url;
    }
}

