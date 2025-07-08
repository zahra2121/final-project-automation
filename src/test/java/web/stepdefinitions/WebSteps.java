package web.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import web.pages.HomePage;
import web.utils.Hook;

import java.time.Duration;

public class WebSteps {
    private static WebDriver driver;
    private static HomePage homePage;

    @Given("user membuka halaman utama Demoblaze")
    public void user_membuka_halaman_utama_demoblaze() {
        driver = Hook.getDriver();
        homePage = new HomePage(driver);
        homePage.openURL("https://www.demoblaze.com/");
    }
}
