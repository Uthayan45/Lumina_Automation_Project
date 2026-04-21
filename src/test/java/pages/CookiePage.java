package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CookiePage {

    WebDriver driver;

    By banner = By.id("cookie-banner");
    By accept = By.id("accept-cookies");

    public CookiePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean bannerDisplayed() {
        List<WebElement> list = driver.findElements(banner);
        return !list.isEmpty() && list.get(0).isDisplayed();
    }

    public void clickAccept() {
        List<WebElement> list = driver.findElements(accept);
        if (!list.isEmpty() && list.get(0).isDisplayed()) {
            list.get(0).click();
        }
    }

    public boolean bannerHidden() {
        List<WebElement> list = driver.findElements(banner);
        return list.isEmpty() || !list.get(0).isDisplayed();
    }
}