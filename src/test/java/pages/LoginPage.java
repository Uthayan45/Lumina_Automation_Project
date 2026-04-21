package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    private final WebDriver driver;

    private final By signInButton = By.xpath("//button[normalize-space()='Sign In']");
    private final By emailField = By.cssSelector("input[type='email'], input[name='email']");
    private final By passwordField = By.cssSelector("input[type='password'], input[name='password']");
    private final By forgotPasswordLink = By.xpath("//*[contains(text(),'Forgot')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isSignInBtn() {
        return driver.findElement(signInButton).isDisplayed();
    }

    public void enterEmail(String email) {
        WebElement emailElement = driver.findElement(emailField);
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public String getEnteredEmail() {
        return driver.findElement(emailField).getAttribute("value");
    }

    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public String getEnteredPassword() {
        return driver.findElement(passwordField).getAttribute("value");
    }

    public boolean isForgotPassword() {
        return driver.findElement(forgotPasswordLink).isDisplayed();
    }

    public void clickSignIn() {
        driver.findElement(signInButton).click();
    }

    public boolean isOnLoginPage() {
        String currentUrl = driver.getCurrentUrl().toLowerCase();
        return currentUrl.contains("login") || driver.findElements(signInButton).size() > 0;
    }

    public void doLogin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }
}