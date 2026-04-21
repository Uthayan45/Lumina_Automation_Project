package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    By shopNowBtn = By.id("shopNowBtn");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickShopNow() {
        waitForSeconds(2);
        driver.findElement(shopNowBtn).click();
    }
}