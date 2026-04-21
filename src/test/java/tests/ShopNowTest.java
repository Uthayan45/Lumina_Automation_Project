package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ShopPage;

public class ShopNowTest extends BaseTest {

    @Test
    public void testShopNowBtn(){

        HomePage homePage = new HomePage(driver);
        ShopPage shopPage = new ShopPage(driver);

        homePage.clickShopNow();

        System.out.println("Current URL: " + driver.getCurrentUrl());

        Assert.assertTrue(shopPage.ShopPageOpen(), "Shop page open aagala");

    }

}