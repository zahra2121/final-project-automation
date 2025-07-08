package web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Field locators
    private final Map<String, By> fieldLocators = new HashMap<>() {{
        put("name", By.id("name"));
        put("country", By.id("country"));
        put("city", By.id("city"));
        put("card", By.id("card"));
        put("month", By.id("month"));
        put("year", By.id("year"));
    }};

    // Button locators
    private final By placeOrderButton = By.xpath("//button[text()='Place Order']");
    private final By purchaseButton = By.xpath("//button[text()='Purchase']");
    private final By successPopup = By.className("sweet-alert");
    private final By confirmButton = By.xpath("//button[text()='OK']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickPlaceOrder() {
        WebElement button = waitUntilClickable(placeOrderButton);
        System.out.println("Button found: " + button.isDisplayed());
        if (button.isDisplayed() && button.isEnabled()) {
            jsClick(button);
        } else {
            System.out.println("Button not interactable");
        }
    }


    public void enterCheckoutDetails(String name, String country, String city, String card, String month, String year) {
        fillField("name", name);
        fillField("country", country);
        fillField("city", city);
        fillField("card", card);
        fillField("month", month);
        fillField("year", year);
    }

    public void clickPurchase() {
        jsClick(waitUntilClickable(purchaseButton));
    }

    public String getSuccessMessage() {
        WebElement popup = waitUntilVisible(successPopup);
        return popup.getText();
    }

    public void closeSuccessPopup() {
        jsClick(waitUntilClickable(confirmButton));
    }

    // ---------- Helper Methods ----------



    private void fillField(String fieldName, String value) {
        By locator = fieldLocators.get(fieldName.toLowerCase());
        if (locator == null) throw new IllegalArgumentException("Field not found: " + fieldName);

        WebElement field = waitUntilVisible(locator);
        if (!field.isEnabled()) {
            throw new InvalidElementStateException("Field " + fieldName + " is not enabled.");
        }

        field.clear();

        if (value != null && !value.trim().isEmpty()) {
            field.sendKeys(value);
        }
    }


    private WebElement waitUntilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
