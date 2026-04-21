package tests;

import base.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.Cart1Page;
import pages.Checkout1Page;
import pages.Home1Page;
import pages.Login1Page;
import pages.Shop1Page;

import java.time.Duration;

public class FlowTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("file:///C:/Users/uthay/Downloads/ECommerceAutomation-main/ECommerceAutomation-main/Ecom/src/resources/login.html");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void createPdfReport() {
        ScreenshotUtil.generatePdfFromScreenshots("FlowTest_Report");
    }

    private void waitShort() {
        sleep(1500);
    }

    private void waitMedium() {
        sleep(3000);
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(priority = 1)
    private void doLogin() {
        Login1Page loginPage = new Login1Page(driver);

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page not displayed");
        waitShort();

        loginPage.doLogin("uthayan@gmail.com", "lumina2026");
        waitMedium();
    }
    @Test(priority = 2)
    private void goToHomeAfterLogin() {
        doLogin();

        Home1Page homePage = new Home1Page(driver);
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page not displayed after login");
        waitShort();
    }
    @Test(priority = 3)
    private void goToShopPage() {
        goToHomeAfterLogin();

        Home1Page homePage = new Home1Page(driver);
        homePage.clickShopNow();
        waitMedium();

        Shop1Page shopPage = new Shop1Page(driver);
        Assert.assertTrue(shopPage.isShopPageDisplayed(), "Shop page not displayed");
    }
    @Test(priority = 4)
    private String addProductAndOpenCart() {
        goToShopPage();

        Shop1Page shopPage = new Shop1Page(driver);
        Assert.assertTrue(shopPage.isFirstProductDisplayed(), "First product not displayed");

        String productName = shopPage.getFirstProductName();
        shopPage.clickFirstAddToCart();
        waitMedium();

        int cartCount = shopPage.getCartCountFromStorage();
        Assert.assertTrue(cartCount > 0, "Product not added to cart");

        shopPage.clickCart();
        waitMedium();

        Cart1Page cartPage = new Cart1Page(driver);
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page not displayed");

        return productName;
    }
    @Test(priority = 5)
    private void goToCheckoutPage() {
        String productName = addProductAndOpenCart();

        Cart1Page cartPage = new Cart1Page(driver);
        Assert.assertTrue(cartPage.isProductAddedInCart(), "Product not added in cart");
        Assert.assertTrue(cartPage.isProductNamePresent(productName), "Correct product name not shown in cart");

        cartPage.clickProceedToCheckout();
        waitMedium();

        Checkout1Page checkoutPage = new Checkout1Page(driver);
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Checkout page not displayed");
    }

    @Test(priority = 6)
    public void testPlaceOrder() {
        goToCheckoutPage();

        Checkout1Page checkoutPage = new Checkout1Page(driver);

        checkoutPage.enterEmail("uthayan@gmail.com");
        System.out.println("Email entered");

        checkoutPage.enterPaymentDetails("4111111111111111", "12/30", "123");
        System.out.println("Payment details entered");

        ScreenshotUtil.captureScreenshot(driver, "Checkout_Page_Filled");

        checkoutPage.clickValidateCard();
        System.out.println("Validate clicked");

        sleep(2000);

        boolean isValidated = checkoutPage.isCardValidated();
        System.out.println("Card validated: " + isValidated);
        Assert.assertTrue(isValidated, "Card validation failed");

        ScreenshotUtil.captureScreenshot(driver, "Card_Validated_Page");

        System.out.println("About to click place order...");
        checkoutPage.clickPlaceOrder();
        System.out.println("Place order clicked");

        sleep(3000);

        boolean isSuccess = checkoutPage.isSuccessPageOpened();
        System.out.println("Success page opened: " + isSuccess);

        ScreenshotUtil.captureScreenshot(driver, "Order_Success_Page");

        Assert.assertTrue(isSuccess, "Success page not opened after clicking Place Order");
    }
}