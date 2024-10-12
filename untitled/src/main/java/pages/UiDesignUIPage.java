package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class UiDesignUIPage extends BasePage {
    public UiDesignUIPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Welcome!']")
    private MobileElement welcomeScreenTitle;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Start the app by tap on the icon in the top left corner.']")
    private MobileElement welcomeScreenMessage;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@resource-id='com.boopathy.raja.tutorial:id/home_icon']")
    private MobileElement welcomeScreenHomeIcon;




    public boolean isWelcomeScreenTitleDisplayed() {
        waitTillElementIsVisible(welcomeScreenTitle, 60);
        try {
            return welcomeScreenTitle.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }


    public boolean isAttentionMessageVisible() {
        waitTillElementIsVisible(welcomeScreenMessage, 60);
        try {
            return welcomeScreenMessage.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isContentVisible() {
        return isElementPresent(welcomeScreenHomeIcon);
    }



}
