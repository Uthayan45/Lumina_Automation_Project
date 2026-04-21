package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login1Page {

    private final WebDriver driver;

    private final By pageTitle = By.xpath("//*[contains(text(),'Welcome Back')]");
    private final By emailField = By.cssSelector("input[type='email'], input[name='email']");
    private final By passwordField = By.cssSelector("input[type='password'], input[name='password']");
    private final By signInButton = By.xpath("//button[normalize-space()='Sign In'] | //*[normalize-space()='Sign In']");
    private final By forgotLink = By.xpath("//*[contains(text(),'Forgot')]");
    private final By createOneLink = By.xpath("//*[contains(text(),'Create one')]");

    public Login1Page(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoginPageDisplayed() {
        return driver.findElement(pageTitle).isDisplayed();
    }

    public boolean isEmailFieldDisplayed() {
        return driver.findElement(emailField).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return driver.findElement(passwordField).isDisplayed();
    }

    public boolean isSignInButtonDisplayed() {
        return driver.findElement(signInButton).isDisplayed();
    }

    public boolean isForgotLinkDisplayed() {
        return driver.findElement(forgotLink).isDisplayed();
    }

    public boolean isCreateOneLinkDisplayed() {
        return driver.findElement(createOneLink).isDisplayed();
    }

    public void enterEmail(String email) {
        WebElement element = driver.findElement(emailField);
        element.clear();
        element.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement element = driver.findElement(passwordField);
        element.clear();
        element.sendKeys(password);
    }

    public void clickSignIn() {
        driver.findElement(signInButton).click();
    }

    public void doLogin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
    }
}