package web.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hook {
    private static WebDriver driver;

    @Before
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless"); // For CI CD running

        // Setup chromedriver via WebDriverManager
        WebDriverManager.chromedriver().setup();

        System.out.println("Initializing WebDriver...");
        driver = new ChromeDriver(options);
        System.out.println("WebDriver initialized successfully.");
    }

    @After
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }

    public static void attachment(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }

    public static WebDriver getDriver() {
        try {
            if (driver == null) {
                setUp();
            }
            driver.getTitle(); // simple harmless call to check session
        } catch (NoSuchSessionException e) {
            setUp();
        } catch (Exception e) {
            // Other exceptions can also indicate driver issues
            setUp();
        }

        return driver;
    }

    public boolean isSessionActive(WebDriver driver) {
        try {
            if (driver == null) {
                return false;
            }
            driver.getTitle(); // simple harmless call to check session
            return true;
        } catch (NoSuchSessionException e) {
            return false;
        } catch (Exception e) {
            // Other exceptions can also indicate driver issues
            return false;
        }
    }
}

