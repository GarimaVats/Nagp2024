package commonFunction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.util.regex.Matcher;
import logger.Log;
import mobile.AppiumHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MobileCommonFunctions {

    AppiumDriver<MobileElement> driver;

    public MobileCommonFunctions() {
        this.driver = AppiumHelper.driverInit();
    }

    public MobileCommonFunctions(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    /**
     * Method to wait till particular timeout
     *
     * @param ae
     *            Mobile Element Object
     */
    public void waitCondition(MobileElement ae, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(ae));
    }

    /**
     * Method to hide active keyboard.
     *
     */
    public void hideKeyBoard() {
        try {
            if (driver instanceof AndroidDriver<?>) {
                ((AndroidDriver<MobileElement>) driver).isKeyboardShown();
                driver.hideKeyboard();
            }
            else {
                 driver.findElementByXPath("//XCUIElementTypeButton[@name='Done' or @name= 'Return']").click();
            }
        } catch (Exception e) {
            Log.info("No Hiding Keyboard");
        }

    }

    /**
     * Method to hide active keyboard using mobile element touch.
     *
     */

    public void hideKeyBoard(MobileElement ae) {
        int valX = ae.getLocation().getX();
        int valY = ae.getLocation().getY();
        int rightX = valX + ae.getSize().getWidth();
        tapByCordinates(valX -10, valY, 0, 0);
    }

    @SuppressWarnings("rawtypes")
    public void tapByCordinates(int xval, int yval, int shiftX, int shiftY)
    {
        new TouchAction(driver).tap(PointOption.point(xval + shiftX, yval + shiftY)).perform().release();
    }

    /**
     * Method to wait the tread.
     *
     * @param duration
     *            wait time in long
     */
    public void driverWait(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            // Log.info("Thread.wait failed to execute");
        }
    }


    /**
     * Method to wait for visibility of element
     *
     *            Android element to wait for
     */

    public void waitConditionForListOfMobileElements(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath))).stream()
                .map(element -> (MobileElement) element).collect(Collectors.toList());
    }

    public void waitCondition(MobileElement ae) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(ae));
        } catch (TimeoutException e) {
            genericVerticalScroll(ae, 6, 0.5, 0.2);
        }
    }

    @SuppressWarnings("rawtypes")
    public void genericVerticalSwipe(double startyRatio) {
        Dimension dim = driver.manage().window().getSize();
        int xval = dim.getWidth() / 2;
        int starty = (int) (dim.getHeight() * startyRatio);
        int endy = (int) (dim.getHeight() * 0.20);
        new TouchAction(driver).press(ElementOption.point(xval, starty))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                .moveTo(ElementOption.point(xval, endy)).release().perform();
    }
    public String getText(MobileElement ae) {
        return ae.getText();
    }
    @SuppressWarnings("rawtypes")
    public void genericVerticalScroll(MobileElement ele, int loopCount, double startyRatio, double endyRatio) {
        for (int i = 1; i < loopCount; i++) {
            Dimension dim = driver.manage().window().getSize();
            int xval = dim.getWidth() / 2;
            int starty = (int) (dim.getHeight() * startyRatio);
            int endy = (int) (dim.getHeight() * endyRatio);

            new TouchAction(driver).press(ElementOption.point(xval, starty))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .moveTo(ElementOption.point(xval, endy)).release().perform();

            try {
                if (ele.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Method to wait for the element and then click on it
     *
     * @param ae
     *            android element for clicking
     */
    public void click(MobileElement ae) {
        if (driver instanceof IOSDriver<?>) {
            waitTillProgressIconIsDisabled();
        }
        waitCondition(ae);
        waitForElementToEnable(ae);
        ae.click();
    }

    /**
     * Click Without Wait
     */
    public void tap(MobileElement ae) {
        ae.click();
    }

    public String getAttribute(MobileElement ae, String attributeName) {
        return ae.getAttribute(attributeName);
    }

    public void waitTillProgressIconIsDisabled() {
        String xpathProgressIcon;
        if (AppiumHelper.driverInit() instanceof IOSDriver<?>) {
            xpathProgressIcon = "//*[@text='In progress']";
        } else {
            xpathProgressIcon = "//*[@id='progress']";
        }
        for (int counter = 0; counter < 10; counter++) {
            try {
                WebDriverWait wait = new WebDriverWait(this.driver, 30);
                wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath(xpathProgressIcon)));
            } catch (Exception e) {
                break;
            }
        }
    }

    public void waitTillElementIsVisible(MobileElement mobileElement, int timeInSecondsToWait) {
        for (int counter = 0; counter < timeInSecondsToWait; counter++) {
            try {
                WebDriverWait wait = new WebDriverWait(this.driver, 1);
                wait.until(ExpectedConditions.visibilityOf(mobileElement));
            } catch (Exception ignored) {
            }
        }
    }

    public void waitForElementToEnable(MobileElement ae) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(ae));
        } catch (TimeoutException e) {
            genericVerticalScroll(ae, 6, 0.5, 0.2);
        }
        if (!ae.isEnabled()) {
            throw new RuntimeException("Element is not enabled yet.");
        }
    }

    public boolean isElementPresent(MobileElement element) {
        try {
            element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
