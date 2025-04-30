package utils;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignatureGenerator {

    public static String generateSignature(String publicKey, String jsonString, String secretKey) {
        String text = publicKey + jsonString + publicKey;
        byte[] hashedBytes = Hashing.hmacSha512(secretKey.getBytes())
                .hashString(text, StandardCharsets.UTF_8).toString().getBytes();
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public static void main(String[] args) {
        String publicKey = "api_pk_5adb8521_6ab0_4e89_bb97_876d9258ee37";
        String jsonString ="{ \"order\": { \"order_id\": \"923bb4e6-4a5f-41ec-81fb-28eb8a152e56\", \"amount\": 1020, \"currency\": \"EUR\", \"order_description\": \"Premium package\", \"order_items\": \"item 1 x 10, item 2 x 30\", \"order_date\": \"2024-12-21 11:21:30\", \"order_number\": 9, \"type\": \"auth\", \"settle_interval\": 0, \"force3ds\": false, \"google_pay_allowed_auth_methods\": [ \"PAN_ONLY\" ], \"customer_date_of_birth\": \"1988-11-21\", \"customer_email\": \"example@example.com\", \"customer_first_name\": \"Nikola\", \"customer_last_name\": \"Tesla\", \"customer_phone\": \"+10111111111\", \"traffic_source\": \"facebook\", \"transaction_source\": \"main_menu\", \"purchase_country\": \"USA\", \"geo_country\": \"USA\", \"geo_city\": \"New Castle\", \"language\": \"pt\", \"website\": \"https://solidgate.com\", \"order_metadata\": { \"coupon_code\": \"NY2024\", \"partner_id\": \"123989\" }, \"success_url\": \"http://merchant.example/success\", \"fail_url\": \"http://merchant.example/fail\" }, \"page_customization\": { \"public_name\": \"Public Name\", \"order_title\": \"Order Title\", \"order_description\": \"Premium package\", \"payment_methods\": [ \"paypal\" ], \"button_font_color\": \"#FFFFFF\", \"button_color\": \"#00816A\", \"font_name\": \"Open Sans\", \"is_cardholder_visible\": true, \"terms_url\": \"https://solidgate.com/terms\", \"back_url\": \"https://solidgate.com\" } }";
        String secretKey = "api_sk_4afd7964_d2f3_4452_8692_cba4737b37c3";
        String signature = generateSignature(publicKey, jsonString, secretKey);
        System.out.println("Base64 Signature: " + signature);
    }
}

