package tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BaseSeleniumPage;
import pages.PaymentPage;
import pages.PaymentStatusPage;
import tests.test_data.PaymentRequest;
import tests.test_data.PaymentRequestBodyTemplates;
import utils.SignatureGenerator;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class PaymentTest extends BaseSeleniumTest {

    static String publicKey = System.getenv("PUBLIC_KEY");
    static String secretKey = System.getenv("SECRET_KEY");

    static String signatureString;
    public static String paymentPageUrl;
    public static String randomOrderId = UUID.randomUUID().toString();
    public static int orderPrice = 800;
    public static String orderCurrency = "EUR";

    @BeforeClass
    public static void Before() {

        PaymentRequest requestTemplate = PaymentRequestBodyTemplates.getDefaultPaymentRequest();

        String paymentRequestBody = requestTemplate.toBuilder()
                .order(requestTemplate.getOrder().toBuilder()
                        .amount(orderPrice)
                        .order_id(randomOrderId)
                        .currency(orderCurrency)
                        .build())
                .build().toString();

        signatureString = SignatureGenerator.generateSignature(publicKey, paymentRequestBody, secretKey);

        Response response = given()
                .baseUri("https://payment-page.solidgate.com")
                .header("Content-Type", "application/json")
                .header("merchant", publicKey)
                .header("signature", signatureString)
                .body(paymentRequestBody)
                .when()
                .post("/api/v1/init")
                .then()
                .extract()
                .response();

        paymentPageUrl = response.jsonPath().getString("url");
        BaseSeleniumPage.setPaymentUrl(paymentPageUrl);
        System.out.println("Payment Page URL: " + paymentPageUrl);
    }

    public static String validCardNumber = "4067429974719265";
    public static String expireDate = "12/34";
    public static String cvv = "666";
    public static String cardHolder = "Ioan Test";
    public static String successURL = "http://merchant.example/success";


    @Description("Positive payment scenario")
    @Test
    public void positiveTest() {
        PaymentPage paymentPage = new PaymentPage();
        int priceFromOrderPage = (int) paymentPage.getPrice();
        String currencyFromOrderPage = paymentPage.getCurrency();


        Assert.assertEquals(orderPrice, priceFromOrderPage);
        Assert.assertEquals(orderCurrency, currencyFromOrderPage);


        PaymentStatusPage paymentStatusPage = new PaymentPage().payWithCard(validCardNumber, expireDate, cvv, cardHolder);

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(paymentPageUrl)));

        Assert.assertEquals(successURL, driver.getCurrentUrl());
        Assert.assertEquals("Subscription activated!", paymentStatusPage.getStatusTitle());

        String orderIdJson = "{ \"order_id\": \"" + randomOrderId + "\" }";

        signatureString = SignatureGenerator.generateSignature(publicKey, orderIdJson, secretKey);
        Response response = given()
                .baseUri("https://pay.solidgate.com")
                .log().all()
                .header("Content-Type", "application/json")
                .header("merchant", publicKey)
                .header("signature", signatureString)
                .body(orderIdJson)
                .when()
                .post("/api/v1/status")
                .then()
                .log().all()
                .extract()
                .response();

        int priceFromOrderStatus = response.jsonPath().getInt("order.amount");
        String currencyFromOrderStatus = response.jsonPath().getString("order.currency");

        Map<String, Map<String, Object>> transactions = response.jsonPath().getMap("transactions");
        String dynamicTransactionId = transactions.keySet().iterator().next();
        String status = transactions.get(dynamicTransactionId).get("status").toString();


        Assert.assertEquals(priceFromOrderPage, priceFromOrderStatus);
        Assert.assertEquals(currencyFromOrderPage, currencyFromOrderStatus);
        Assert.assertEquals("success", status);

    }
}
