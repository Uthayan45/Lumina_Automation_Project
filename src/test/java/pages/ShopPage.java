package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
public class ShopPage {
    WebDriver driver;
    By hoverBox = By.id("hover-box");
    By hoverTooltip = By.id("hover-tooltip");
    By doubleClickBox = By.id("double-click-box");
    By promoResult = By.id("promo-result");
    By shopGrid = By.id("shop-grid");
    By productCards = By.cssSelector(".product-card");
    By productNames = By.cssSelector(".product-title");
    By addToCartButtons = By.cssSelector(".add-to-cart-btn");
    By wishlistDropzone = By.id("wishlist-dropzone");
    By wishlistItems = By.id("wishlist-items");

    public ShopPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean ShopPageOpen() {
        return driver.getCurrentUrl().contains("shop.html");
    }

    public boolean HoverBoxDisplay() {
        return driver.findElement(hoverBox).isDisplayed();
    }

    public void hoverOnHoverBox() {
        WebElement element = driver.findElement(hoverBox);
        new Actions(driver).moveToElement(element).perform();
    }

    public boolean isHoverTooltipDisplayed() {
        return driver.findElement(hoverTooltip).isDisplayed();
    }

    public String getHoverTooltipText() {
        return driver.findElement(hoverTooltip).getText().trim();
    }

    public boolean isDoubleClickBoxDisplayed() {
        return driver.findElement(doubleClickBox).isDisplayed();
    }

    public void doubleClickPromoBox() {
        WebElement element = driver.findElement(doubleClickBox);
        new Actions(driver).doubleClick(element).perform();
    }

    public String getPromoResultText() {
        return driver.findElement(promoResult).getText().trim();
    }


    public void clickFirstAddToCartButton() {
        driver.findElements(addToCartButtons).get(0).click();
    }

    public int getCartCountFromLocalStorage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeScript(
                "let cart = JSON.parse(localStorage.getItem('lumina_cart')) || [];" +
                        "return cart.reduce((sum, item) => sum + item.quantity, 0);"
        );
        return ((Long) result).intValue();
    }

    public boolean isWishlistDropzoneDisplayed() {
        return driver.findElement(wishlistDropzone).isDisplayed();
    }

    public boolean isWishlistItemsPresent() {
        return driver.findElements(wishlistItems).size() > 0;
    }

    public String getWishlistItemsText() {
        return driver.findElement(wishlistItems).getText().trim();
    }

    public void dragFirstProductToWishlist() {
        WebElement product = driver.findElements(productCards).get(0);
        WebElement dropzone = driver.findElement(wishlistDropzone);
        new Actions(driver).dragAndDrop(product, dropzone).perform();
    }

    public void wishlistFallbackForSelenium() {
        WebElement dropzone = driver.findElement(wishlistDropzone);
        new Actions(driver).moveToElement(dropzone).click().perform();
    }
}