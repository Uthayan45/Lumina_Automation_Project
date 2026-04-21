package tests;

import base.BaseTest;
import base.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void pageSetup() {
        driver.get("file:///C:/Users/uthay/Downloads/ECommerceAutomation-main/ECommerceAutomation-main/Ecom/src/resources/login.html");
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void testSignInBtn() {
        Assert.assertTrue(loginPage.isSignInBtn(), "Sign In button is not displayed");
        ScreenshotUtil.captureScreenshot(driver, "testSignInBtn_PASS");
        System.out.println("1. Sign In button display test passed");
    }

    @Test(priority = 2)
    public void testEnterEmail() {
        loginPage.enterEmail("uthayan@gmail.com");
        Assert.assertEquals(loginPage.getEnteredEmail(), "uthayan@gmail.com", "Email not entered properly");
        ScreenshotUtil.captureScreenshot(driver, "testEnterEmail_PASS");
        System.out.println("2. Enter email test passed");
    }

    @Test(priority = 3)
    public void testEnterPass() {
        loginPage.enterPassword("lumina2026");
        Assert.assertEquals(loginPage.getEnteredPassword(), "lumina2026", "Password not entered properly");
        ScreenshotUtil.captureScreenshot(driver, "testEnterPass_PASS");
        System.out.println("3. Enter password test passed");
    }

    @Test(priority = 4)
    public void testForgotPass() {
        Assert.assertTrue(loginPage.isForgotPassword(), "Forgot password link is not displayed");
        ScreenshotUtil.captureScreenshot(driver, "testForgotPass_PASS");
        System.out.println("4. Forgot password link test passed");
    }

    @Test(priority = 5)
    public void testLoginEmptyEmail() {
        loginPage.enterEmail("");
        loginPage.enterPassword("lumina2026");
        loginPage.clickSignIn();
        waitShort();

        ScreenshotUtil.captureScreenshot(driver, "testLoginEmptyEmail");

        Assert.assertTrue(loginPage.isOnLoginPage(), "Should stay on login page when email is empty");
        System.out.println("5. Empty email test passed");
    }

    @Test(priority = 6)
    public void testLoginEmptyPass() {
        loginPage.enterEmail("uthayan@example.com");
        loginPage.enterPassword("");
        loginPage.clickSignIn();
        waitShort();

        ScreenshotUtil.captureScreenshot(driver, "testLoginEmptyPass");

        Assert.assertTrue(loginPage.isOnLoginPage(), "Should stay on login page when password is empty");
        System.out.println("6. Empty password test passed");
    }

    @Test(priority = 7)
    public void testLoginInvalid() {
        loginPage.enterEmail("wrong@example.com");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickSignIn();
        waitShort();

        ScreenshotUtil.captureScreenshot(driver, "testLoginInvalid");

        Assert.assertTrue(loginPage.isOnLoginPage(), "Should stay on login page for invalid credentials");
        System.out.println("7. Invalid credentials test passed");
    }

    @Test(priority = 8)
    public void testLoginFun() {
        loginPage.enterEmail("uthayan@example.com");
        loginPage.enterPassword("lumina2026");
        loginPage.clickSignIn();
        waitShort();

        ScreenshotUtil.captureScreenshot(driver, "testLoginFun");

        String currentURL = driver.getCurrentUrl();
        Assert.assertFalse(currentURL.contains("login"), "Login failed - still on login page");
        System.out.println("8. Login functionality test passed");
    }
}