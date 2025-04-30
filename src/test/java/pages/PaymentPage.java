package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class PaymentPage extends BaseSeleniumPage {

    @FindBy(xpath = "//button[@data-testid='submit']")
    private WebElement payButton;

    @FindBy(id = "ccnumber")
    private WebElement cardNumberInputField;

    @FindBy(name = "cardExpiryDate")
    private WebElement cardExpireDateInputField;

    @FindBy(id = "cvv2")
    private WebElement cvv2InputField;

    @FindBy(xpath = "//div[@data-testid='terms-checkbox']")
    private WebElement termsCheckBox;

    @FindBy(xpath = "//input[@data-testid='cardHolder']")
    private WebElement cardHolderInputField;

    @FindBy(xpath = "//div[@data-testid='price_major']")
    private WebElement priceTitle;

    public PaymentPage() {
        driver.get(paymentPageUrl);
        PageFactory.initElements(driver, this);
    }

    public PaymentStatusPage payWithCard(String cardNumber, String cardExpireDate, String cardCVV2, String cardHolder) {
        cardNumberInputField.sendKeys(cardNumber);
        cardExpireDateInputField.sendKeys(cardExpireDate);
        cvv2InputField.sendKeys(cardCVV2);
        if (!driver.findElements(By.xpath("//input[@data-testid='cardHolder']")).isEmpty())
            cardHolderInputField.sendKeys(cardHolder);
        if (!termsCheckBox.getDomAttribute("aria-checked").equals("true"))
            termsCheckBox.click();
        payButton.click();
        return new PaymentStatusPage();
    }

    public double getPrice() {
        return Double.parseDouble(priceTitle.getText().replaceAll("[^0-9,]", "").replace(",", "."));
    }

    public String getCurrency() {
        return priceTitle.getText().replaceAll("[^a-zA-Z]", "").trim();
    }
}
