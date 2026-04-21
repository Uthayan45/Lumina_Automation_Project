package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Home1Page {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By welcomeHeading = By.xpath("//*[contains(text(),'Welcome to Lumina')]");
    private final By shopNowButton = By.id("shopNowBtn");

    public Home1Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isHomePageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickShopNow() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(shopNowButton));
        button.click();
    }
}