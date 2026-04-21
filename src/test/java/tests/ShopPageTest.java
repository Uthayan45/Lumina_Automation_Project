package tests;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ShopPage;
public class ShopPageTest extends BaseTest {

    @Test (priority = 1)
    public void testHoverBox() {
        HomePage homePage = new HomePage(driver);
        ShopPage shopPage = new ShopPage(driver);
        homePage.clickShopNow();
        shopPage.waitForSeconds(3);
        Assert.assertTrue(
                shopPage.HoverBoxDisplay(), "Hover box is not displayed on the Shop page"
        );

        shopPage.waitForSeconds(2);
        shopPage.hoverOnHoverBox();
        shopPage.waitForSeconds(2);
        Assert.assertTrue(
                shopPage.isHoverTooltipDisplayed(),
                "Tooltip is not displayed after hover"
        );
        Assert.assertEquals(
                shopPage.getHoverTooltipText(),
                "Special 20% discount unlocked!",
                "Tooltip text is incorrect"
        );
    }

    @Test (priority = 2)
    public void testPromoCode() {
        HomePage homePage = new HomePage(driver);
        ShopPage shopPage = new ShopPage(driver);

        homePage.clickShopNow();
        shopPage.waitForSeconds(3);

        Assert.assertTrue(
                shopPage.isDoubleClickBoxDisplayed(),
                "Double click promo box is not displayed"
        );

        shopPage.waitForSeconds(2);
        shopPage.doubleClickPromoBox();
        shopPage.waitForSeconds(2);

        Assert.assertEquals(
                shopPage.getPromoResultText(),
                "PROMO2026",
                "Promo code did not appear correctly after double click"
        );
    }
    @Test (priority = 3)
    public void testAddToCartBtn() {
        HomePage homePage = new HomePage(driver);
        ShopPage shopPage = new ShopPage(driver);

        homePage.clickShopNow();
        shopPage.waitForSeconds(5);

        int beforeCount = shopPage.getCartCountFromLocalStorage();

        shopPage.clickFirstAddToCartButton();
        shopPage.waitForSeconds(2);

        int afterCount = shopPage.getCartCountFromLocalStorage();

        Assert.assertTrue(
                afterCount > beforeCount,
                "Add to Cart action did not update the cart"
        );
    }

    @Test (priority = 4)
    public void testWishlistSection() {
        HomePage homePage = new HomePage(driver);
        ShopPage shopPage = new ShopPage(driver);

        homePage.clickShopNow();
        shopPage.waitForSeconds(3);

        Assert.assertTrue(
                shopPage.isWishlistDropzoneDisplayed(),
                "Wishlist dropzone is not displayed"
        );

        Assert.assertTrue(
                shopPage.isWishlistItemsPresent(),
                "Wishlist items element is not present"
        );
    }

    @Test (priority = 5)
    public void testDragAndDrop() {
        HomePage homePage = new HomePage(driver);
        ShopPage shopPage = new ShopPage(driver);

        homePage.clickShopNow();
        shopPage.waitForSeconds(5);

        shopPage.dragFirstProductToWishlist();
        shopPage.waitForSeconds(2);

        if (shopPage.getWishlistItemsText().isEmpty()) {
            shopPage.wishlistFallbackForSelenium();
            shopPage.waitForSeconds(2);
        }

        Assert.assertFalse(
                shopPage.getWishlistItemsText().isEmpty(),
                "Wishlist did not update after drag and drop"
        );
    }
}