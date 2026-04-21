package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
public class Cart1Page {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By cartTableBody = By.id("cart-tbody");
    private final By cartRows = By.cssSelector("#cart-tbody tr");
    private final By removeButtons = By.cssSelector(".remove-btn");
    private final By checkoutButton = By.id("checkout-btn");
    // optional summary locators
    private final By subtotal = By.id("summary-subtotal");
    private final By total = By.id("summary-total");
    private final By tax = By.id("summary-tax");
    public Cart1Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    public boolean isCartPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTableBody)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isProductAddedInCart() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartTableBody));
            List<WebElement> rows = driver.findElements(cartRows);
            return !rows.isEmpty() && !driver.findElement(cartTableBody).getText().contains("Your cart is empty");
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isProductNamePresent(String productName) {
        try {
            return driver.findElement(cartTableBody).getText().contains(productName);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickProceedToCheckout() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(checkoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }
}