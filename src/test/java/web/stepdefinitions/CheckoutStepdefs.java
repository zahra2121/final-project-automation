package web.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import web.pages.CheckoutData;
import web.pages.CheckoutPage;
import web.pages.HomePage;
import web.utils.Hook;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class CheckoutStepdefs {

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private CheckoutPage checkoutPage;
    private static final int DEFAULT_TIMEOUT = 10;

    // ------------------ STEP DEFINITIONS ------------------

    @Given("User membuka halaman Demoblaze")
    public void openHomePage() {
        setupDriver();
        homePage.openHomePage();
        waitForVisibility(By.xpath("//*[@id='navbarExample']/ul/li[1]/a"));
    }

    @When("User menambahkan produk {string} ke keranjang")
    public void addProductToCart(String productName) {
        log("📦 Menambahkan produk ke keranjang: " + productName);
        homePage.selectProduct(productName);
        homePage.addToCart();
        handleAlertIfPresent();
    }

    @When("User pergi ke halaman cart")
    public void goToCartPage() {
        homePage.goToCart();
    }

    @When("User membuka form checkout")
    public void openCheckoutForm() {
        checkoutPage.clickPlaceOrder();
        waitForVisibility(By.id("orderModal"));
    }

    @When("User mengisi form checkout dengan data berikut:")
    public void checkoutWithData(DataTable data) {
        //openCheckoutForm();
        fillCheckoutForm(data);
    }

    @When("User mengisi form checkout tanpa klik Place Order dengan data berikut:")
    public void fillFormWithoutPlaceOrder(DataTable data) {
        fillCheckoutForm(data);
    }

    @When("User menekan tombol Purchase berdasarkan data validitas")
    public void clickPurchaseBasedOnValidity() {
        log("🛒 Menentukan apakah perlu menekan tombol Purchase...");

        if (isFormDataValid()) {
            log("✅ Data valid. Menekan tombol Purchase.");
            checkoutPage.clickPurchase();
        } else {
            log("❗ Data tidak valid. Tidak menekan tombol Purchase.");
        }
    }

    // di CheckoutSteps.java, cari method verifyMessageBasedOnSuccess dan ubah anotasinya jadi:

    @Then("Akan muncul pesan {string} sesuai hasil {word}")
    public void verifyMessageBasedOnSuccess(String expectedMessage, String isSuccessStr) {
        boolean isSuccess = Boolean.parseBoolean(isSuccessStr);
        if (isSuccess) {
            verifySuccessMessage(expectedMessage);
        } else {
            verifyNoPopupOnInvalid(expectedMessage);  // <— pastikan dipakai di sini
        }
    }



    @Then("Order berhasil diproses dan muncul pesan sukses {string}")
    public void verifySuccessfulOrder(String expectedMessage) {
        verifySuccessMessage(expectedMessage);
    }

    @Then("User menutup pop-up konfirmasi pembelian")
    public void closeSuccessPopup() {
        try {
            waitForVisibility(By.xpath("//div[contains(@class, 'sweet-alert')]//h2[contains(text(), 'Thank you for your purchase!')]"), DEFAULT_TIMEOUT + 5);
            waitForClickable(By.xpath("//button[contains(@class, 'confirm') and text()='OK']")).click();
            log("✅ Tombol OK diklik.");
        } catch (Exception e) {
            log("❌ ERROR: Gagal menutup pop-up konfirmasi - " + e.getMessage());
            captureScreenshot("popup_error.png");
            throw e;
        }
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
        CheckoutData.clear();
    }

    // ------------------ HELPER METHODS ------------------

    private void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = Hook.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        homePage = new HomePage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    private void fillCheckoutForm(DataTable data) {
        Map<String, String> formData = data.asMaps(String.class, String.class).get(0);
        CheckoutData.setFormData(
                formData.get("name"),
                formData.get("country"),
                formData.get("city"),
                formData.get("card"),
                formData.get("month"),
                formData.get("year")
        );

        log("📝 Mengisi form checkout:");
        logCheckoutData();

        checkoutPage.enterCheckoutDetails(
                CheckoutData.getName(),
                CheckoutData.getCountry(),
                CheckoutData.getCity(),
                CheckoutData.getCard(),
                CheckoutData.getMonth(),
                CheckoutData.getYear()
        );
    }

    private boolean isFormDataValid() {
        return isFilled(CheckoutData.getName()) && isFilled(CheckoutData.getCard());
    }

    private boolean isFilled(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private void verifySuccessMessage(String expectedMessage) {
        try {
            WebElement successElement = waitForVisibility(By.xpath("//div[contains(@class, 'sweet-alert')]//h2"), DEFAULT_TIMEOUT + 5);
            String actualMessage = successElement.getText();
            log("✅ Pesan sukses muncul: " + actualMessage);
            Assertions.assertTrue(actualMessage.contains(expectedMessage), "Pesan sukses tidak sesuai!");
        } catch (TimeoutException e) {
            log("❌ ERROR: Pesan sukses tidak muncul.");
            captureScreenshot("checkout_success_error.png");
            throw e;
        }
    }

    private void verifyNoPopupOnInvalid(String expectedMessage) {
        try {
            waitForVisibility(By.xpath("//div[contains(@class, 'sweet-alert')]//h2"), 5);
            captureScreenshot("error_popup_muncul_padahal_invalid.png");
            Assertions.fail("❌ Pop-up muncul padahal input tidak valid.");
        } catch (TimeoutException e) {
            log("✅ Tidak ada pop-up muncul, sesuai ekspektasi untuk input invalid.");
        }
    }

    private void handleAlertIfPresent() {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            log("🔔 Alert muncul: " + alert.getText());
            alert.accept();
            log("✅ Alert ditutup.");
        } catch (TimeoutException e) {
            log("⚠️ Tidak ada alert yang muncul setelah klik Add to Cart.");
        }
    }

    private WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForVisibility(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void captureScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File dir = new File("screenshots/");
            if (!dir.exists()) dir.mkdirs();
            FileUtils.copyFile(screenshot, new File(dir, fileName));
            log("📸 Screenshot disimpan: screenshots/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logCheckoutData() {
        log("   - Nama    : " + CheckoutData.getName());
        log("   - Negara  : " + CheckoutData.getCountry());
        log("   - Kota    : " + CheckoutData.getCity());
        log("   - Kartu   : " + CheckoutData.getCard());
        log("   - Bulan   : " + CheckoutData.getMonth());
        log("   - Tahun   : " + CheckoutData.getYear());
    }

    private void log(String message) {
        System.out.println(message);
    }
}
