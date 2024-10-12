package mobile;

import static mobile.AppiumHelper.driverInit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumManager {

    public static final AppiumManager instance = new AppiumManager();
    public AppiumDriver<MobileElement> driver;
    public String host;

    public void appLaunch(Properties config) throws MalformedURLException {
        host = config.getProperty("HOST");
        if (driver != null) {
            return;
        }
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // defining directory of the path where app is present
        //String appUrl = System.getProperty("user.dir") + config.getProperty("APP_URL");
        File appDir = new File(config.getProperty("APP_URL"));
        File app = new File(appDir, config.getProperty("APP_NAME"));

        // defining appium url
        String appiumUrl = "http://" + config.getProperty("APPIUM_IP") + ":" + config.getProperty("APPIUM_PORT")
                + "/wd/hub";

        switch (config.getProperty("OS")) {

            case "ANDROID":
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getProperty("PLATFORM_VERSION"));
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, config.getProperty("DEVICE_NAME"));
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, config.getProperty("AUTOMATION_NAME"));
                capabilities.setCapability(MobileCapabilityType.UDID, config.getProperty("DEVICE_ID"));
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, config.getProperty("APP_PACKAGE"));
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, config.getProperty("APP_ACTIVITY"));
                capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, config.getProperty("FULL_RESET"));
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, config.getProperty("SESSION_TIMEOUT"));
                capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                driver = new AndroidDriver<MobileElement>(new URL(appiumUrl), capabilities);
                break;

            default:
                System.out.println("OS version is not correct");
                break;
        }
    }

    public void appClose() {
        driverInit().closeApp();
        driverInit().launchApp();
    }

    public void appLaunch() {
        driverInit().launchApp();
    }

    public void appTerminate(String bundelID) {
        driverInit().terminateApp(bundelID);
    }

    public ApplicationState getAppState(String bundelID) {
        ApplicationState state = driver.queryAppState(bundelID);
        return state;
    }
}
