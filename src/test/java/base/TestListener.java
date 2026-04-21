package base;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
public class TestListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName() + "_PASS";
        ScreenshotUtil.captureScreenshot(BaseTest.driver, testName);
    }
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName() + "_FAIL";
        ScreenshotUtil.captureScreenshot(BaseTest.driver, testName);
    }
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName() + "_SKIP";
        ScreenshotUtil.captureScreenshot(BaseTest.driver, testName);
    }
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("All test execution completed.");
    }
}