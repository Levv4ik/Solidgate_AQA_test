package tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BaseSeleniumPage;


import java.time.Duration;

abstract public class BaseSeleniumTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        BaseSeleniumPage.setDriver(driver);
        BaseSeleniumPage.setWebdriverWait(wait);
    }


    @After
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}