package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class NavbarPage {
    WebDriver driver;
    By homeLink = By.xpath("//a[text()='Home']");
    By shopLink = By.xpath("//a[text()='Shop']");
    By loginLink = By.xpath("//a[text()='Login']");

    public NavbarPage(WebDriver driver) {
        this.driver = driver;
    }
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickHome() {
        waitForSeconds(2);
        driver.findElement(homeLink).click();
    }

    public void clickShop() {
        waitForSeconds(2);
        driver.findElement(shopLink).click();
    }

    public void clickLogin() {
        waitForSeconds(2);
        driver.findElement(loginLink).click();
    }

    public String getCurrentUrl() {
        waitForSeconds(1);
        return driver.getCurrentUrl();
    }
}