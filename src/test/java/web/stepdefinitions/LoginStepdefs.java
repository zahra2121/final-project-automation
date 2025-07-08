package web.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.pages.HomePage;
import web.pages.LoginPage;
import web.utils.Hook;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class LoginStepdefs {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    public LoginStepdefs() {
        this.driver = Hook.getDriver();
        this.homePage = new HomePage(driver);
    }

    @When("user mengklik menu Log in")
    public void user_mengklik_menu_log_in() {
        homePage.clickLoginButton();
        loginPage = new LoginPage(driver);
    }

    @When("user memasukkan username {string} dan password {string}")
    public void user_memasukkan_username_dan_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("user menekan tombol Log in")
    public void user_menekan_tombol_log_in() {
        loginPage.clickLoginButton();
    }

    @Then("sistem menampilkan halaman utama")
    public void sistem_menampilkan_halaman_utama() {
        Assert.assertTrue("Login gagal, tombol Logout tidak ditemukan!", loginPage.isLoginSuccessful());
    }

    @Then("sistem menampilkan pesan {string}")
    public void sistem_menampilkan_pesan(String expectedMessage) {
        String actualAlert = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            actualAlert = alert.getText();
            alert.accept();
            System.out.println("✅ Alert muncul dengan pesan: " + actualAlert);
        } catch (NoAlertPresentException | TimeoutException e) {
            actualAlert = "No alert displayed";
            captureScreenshot("alert_not_found.png");
            System.out.println("❌ Alert tidak muncul.");
        }

        Assertions.assertEquals(expectedMessage, actualAlert,
                "\n❌ Perbandingan alert gagal:"
                        + "\n  - Diharapkan: " + expectedMessage
                        + "\n  - Aktual    : " + actualAlert);
    }

    private void captureScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File dir = new File("screenshots/");
            if (!dir.exists()) dir.mkdirs();
            FileUtils.copyFile(screenshot, new File(dir, fileName));
            System.out.println("📸 Screenshot disimpan: screenshots/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
