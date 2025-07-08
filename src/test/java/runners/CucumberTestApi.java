package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = {"api.stepdefinitions", "api.utils"},
        plugin = {"pretty", "html:target/api-report.html", "json:target/api-report.json"},
        tags = "@api",
        monochrome = true
)
public class CucumberTestApi {}
