package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseSeleniumPage {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String paymentPageUrl;
    public static void setDriver(WebDriver webDriver){
        driver = webDriver;
    }

    public static void setPaymentUrl(String url){
        paymentPageUrl = url;
    }

    public static void setWebdriverWait(WebDriverWait webdriverWait){
        wait = webdriverWait;
    }
}
