package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Shop1Page {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By shopGrid = By.id("shop-grid");
    private final By addToCartButtons = By.cssSelector(".add-to-cart-btn");
    private final By firstProductTitle = By.cssSelector(".product-card .product-title");
    private final By cartLink = By.xpath("//a[contains(@href,'cart.html') or normalize-space()='Cart']");
    private final By toastContainer = By.id("toast-container");

    public Shop1Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isShopPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(shopGrid)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstProductDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductTitle))
                .getText().trim();
    }

    public void clickFirstAddToCart() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);

        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    public boolean isToastDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(toastContainer)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCart() {
        WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(cartLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cart);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cart);
        }
    }

    public int getCartCountFromStorage() {
        Object value = ((JavascriptExecutor) driver).executeScript(
                "const cart = JSON.parse(localStorage.getItem('lumina_cart')) || [];" +
                        "return cart.reduce((sum, item) => sum + item.quantity, 0);"
        );
        return Integer.parseInt(String.valueOf(value));
    }
}