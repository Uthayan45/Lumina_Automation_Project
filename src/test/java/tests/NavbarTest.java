package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NavbarPage;
public class NavbarTest extends BaseTest {

    @Test (priority = 1)
    public void testHomeNav() {
        NavbarPage navbarPage = new NavbarPage(driver);
        navbarPage.waitForSeconds(1);
        navbarPage.clickHome();
        navbarPage.waitForSeconds(1);
        Assert.assertTrue(
                navbarPage.getCurrentUrl().contains("index.html"), "Home click pannina index.html ku pogala"
        );
        navbarPage.waitForSeconds(2);
    }

    @Test(priority = 2)
    public void testShopNav() {
        NavbarPage navbarPage = new NavbarPage(driver);
        navbarPage.waitForSeconds(1);
        navbarPage.clickShop();
        navbarPage.waitForSeconds(1);
        Assert.assertTrue(
                navbarPage.getCurrentUrl().contains("shop.html"), "Shop click is not working shop.html");
        navbarPage.waitForSeconds(1);
    }

    @Test (priority = 3)
    public void testLoginNav() {

        NavbarPage navbarPage = new NavbarPage(driver);
        navbarPage.waitForSeconds(2);
        navbarPage.clickLogin();
        navbarPage.waitForSeconds(3);
        Assert.assertTrue(
                navbarPage.getCurrentUrl().contains("login.html"), "Login click is not working the  login.html"
        );
        navbarPage.waitForSeconds(1);
    }
}