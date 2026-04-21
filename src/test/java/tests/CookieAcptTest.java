package tests;

import base.BaseTest;
import base.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CookiePage;

@Listeners(TestListener.class)
public class CookieAcptTest extends BaseTest {

    @Test
    public void cookieTest() {
        CookiePage cookie = new CookiePage(driver);

        Assert.assertTrue(
                cookie.bannerDisplayed(),
                "Cookie banner is not displayed"
        );

        cookie.clickAccept();
        waitShort();

        Assert.assertTrue(
                cookie.bannerHidden(),
                "Cookie banner is not hidden after clicking Accept"
        );
    }
}