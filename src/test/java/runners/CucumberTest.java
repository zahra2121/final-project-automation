package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"web.stepdefinitions", "web.utils"},
        plugin = {"pretty", "html:target/web-report.html", "json:target/web-report.json"},
        tags = "@web",
        monochrome = true
)
public class CucumberTest {}
