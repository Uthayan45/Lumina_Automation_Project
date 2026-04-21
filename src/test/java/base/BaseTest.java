package base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
public class BaseTest {
    public static WebDriver driver;
    protected WebDriverWait wait;
    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("file:///C:/Users/uthay/Downloads/ECommerceAutomation-main/ECommerceAutomation-main/Ecom/src/resources/index.html");
    }
    protected void waitShort() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @AfterSuite
    public void createPdfReport() {
        ScreenshotUtil.generatePdfFromScreenshots("Lumina_Automation_Report");
    }
}