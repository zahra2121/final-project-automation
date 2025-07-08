package web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By cartButton = By.id("cartur");
    private final By loginButton = By.id("login2");
    private final By welcomeMessage = By.id("nameofuser");
    private final By productList = By.cssSelector(".card-title a");
    private final By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");
    private final By placeOrderButton = By.xpath("//button[@class='btn btn-success']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigasi
    public void openHomePage() {
        driver.get("https://www.demoblaze.com/");
    }

    public void openURL(String url) {
        driver.get(url);
    }

    // Aksi umum
    public void goToCart() {
        clickWithFallback(cartButton);
    }

    public void clickLoginButton() {
        clickWithFallback(loginButton);
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).getText();
    }

    public boolean isUserLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMessage)).isDisplayed();
    }

    // Produk
    public void selectProduct(String productName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(productList));
        List<WebElement> products = driver.findElements(productList);

        for (WebElement product : products) {
            if (product.getText().equalsIgnoreCase(productName)) {
                clickWithFallback(product);
                return;
            }
        }

        throw new RuntimeException("Produk tidak ditemukan: " + productName);
    }

    public void addToCart() {
        clickWithFallback(addToCartButton);
    }

    // Checkout
    public void proceedToCheckout() {
        clickWithFallback(placeOrderButton);
    }

    // Helper klik dengan fallback JavaScript
    private void clickWithFallback(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            scrollToElement(element);
            element.click();
        } catch (TimeoutException | ElementClickInterceptedException e) {
            System.out.println("⚠️ Fallback click via JavaScript: " + locator);
            WebElement element = driver.findElement(locator);
            jsClick(element);
        }
    }

    private void clickWithFallback(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            scrollToElement(element);
            element.click();
        } catch (Exception e) {
            System.out.println("⚠️ Gagal klik elemen langsung, fallback ke JS click");
            jsClick(element);
        }
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
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
}
