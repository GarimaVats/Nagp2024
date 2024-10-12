package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class AppiumHelper {

    public static AppiumDriver<MobileElement> driverInit() {
        return AppiumManager.instance.driver;
    }
}
