package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentStatusPage extends BaseSeleniumPage {
    @FindBy(xpath = "//h1[@data-testid='status-title']")
    private WebElement statusTitle;

    @FindBy(xpath = "//div[@data-testid='price_major']")
    private WebElement priceTitle;

    public PaymentStatusPage() {
        PageFactory.initElements(driver, this);
    }

    public String getStatusTitle() {
        return statusTitle.getText();
    }

    public double getPrice() {
        return Double.parseDouble(priceTitle.getText().replaceAll("[^0-9,]", "").replace(",", "."));
    }

    public String getCurrency() {
        return priceTitle.getText().replaceAll("[^a-zA-Z]", "").trim();
    }
}
