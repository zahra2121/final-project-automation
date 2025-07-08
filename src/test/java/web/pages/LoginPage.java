package web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // ✅ Locators
    private final By usernameField = By.id("loginusername");
    private final By passwordField = By.id("loginpassword");
    private final By loginButton = By.xpath("//button[text()='Log in']");
    private final By logoutButton = By.id("logout2");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ✅ Input username dan password
    public void enterUsername(String username) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        input.clear();
        input.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        input.clear();
        input.sendKeys(password);
    }

    // ✅ Klik tombol login
    public void clickLoginButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            button.click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            System.out.println("⚠️ Gagal klik tombol login, coba pakai JavaScript.");
            WebElement button = driver.findElement(loginButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    // ✅ Cek apakah login berhasil (tombol logout muncul)
    public boolean isLoginSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("❌ Login gagal atau timeout: Tombol logout tidak muncul.");
            return false;
        }
    }

    // ✅ Ambil pesan error dari alert popup (jika login gagal)
    public String getErrorMessage() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (TimeoutException e) {
            return "No alert found.";
        }
    }

    public String getAlertMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String message = alert.getText();
            alert.accept();
            return message;
        } catch (Exception e) {
            return "No alert displayed";
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}

