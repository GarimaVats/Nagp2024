package stepDefinitions;

import config.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.util.HashMap;
import java.util.Map;
import logger.Log;
import mobile.AppiumManager;
import org.junit.runner.notification.RunListener;

import utils.ReportUtil;
import utils.Screenshot;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Properties;

public class Hooks extends RunListener {

    public static Properties configuration;
    public String directoryPath;
    Screenshot screenshot;
    ReportUtil reportUtil;
    File[] file;
    public static Map<String, String> testData;
    public static Map<String, String> generatedOutput;


    public Hooks() {
        screenshot = new Screenshot();
        reportUtil = new ReportUtil();
        testData = new HashMap<>();
        generatedOutput = new HashMap<>();
    }

    @Before()
    public void beforeAppLaunch(Scenario scenario) throws MalformedURLException {

        Log.startTestCase(scenario.getName());

        // Initializing configuration file
        configuration = new Configuration().initializeConfig();

        // Launching the Mobile App
        AppiumManager.instance.appLaunch(configuration);

        if (configuration.getProperty("PLATFORM_NAME").equalsIgnoreCase("iOS")) {
            if (!AppiumManager.instance.getAppState(configuration.getProperty("BUNDLE_ID"))
                    .toString()
                    .equalsIgnoreCase(("RUNNING_IN_FOREGROUND"))) {
                AppiumManager.instance.appLaunch();
            }
        }
        // Directory Creation for Saving Screenshot
        directoryPath = screenshot.createDirectory();

    }

    @After
    public void closingApplication(Scenario scenario) {
//     Capturing Screenshot & Saving in the directory
        file = screenshot.takesMobileScreenshot();

        // Attaching Screenshot in Allure Report
        reportUtil.addAllureAttachment("Screen", file);

        // Closing driver instance
        if(configuration.getProperty("PLATFORM_NAME").equalsIgnoreCase("iOS"))
            AppiumManager.instance.appTerminate(configuration.getProperty("BUNDLE_ID"));
        else
            AppiumManager.instance.appClose();

        // Generating Word file for all the captured screenshot
        reportUtil.embedScreenshotsIntoWord(directoryPath, scenario.getName() + " => " + scenario.getStatus());
        Log.endTestCase(scenario.getName() + " => " + scenario.getStatus());

    }

    @AfterStep
    public void screenShotAfterEachStep() {
        file = screenshot.takesMobileScreenshot();
    }
}
