package Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/feature/"},
        plugin = {"pretty",
                "progress:target/cucumber-reports/Cucumber.xml",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
        glue = {"stepDefinitions"},
        tags = "@Nagp",
        dryRun = false,
        stepNotifications = true,
        monochrome = true)
public class TestRunner {

}