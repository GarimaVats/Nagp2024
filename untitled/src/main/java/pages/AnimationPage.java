package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class AnimationPage  extends BasePage {
    public AnimationPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Animations']")
    private MobileElement animationsMenu;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Alpha']")
    private MobileElement AlphaOption;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Blink']")
    private MobileElement BlinkOption;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Bounce']")
    private MobileElement BounceOption;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Cross Fade']")
    private MobileElement CrossFadeOption;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Fade In']")
    private MobileElement FadeInOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Arc Menu']")
    private MobileElement ArcSideMenu;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Arc Menu']")
    private MobileElement ArcMenuOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Chart']")
    private MobileElement ChartSideMenu;

    public void AnimationMenuClick() {
        waitCondition(animationsMenu, 60);
        animationsMenu.click();
    }

    public String getAnimationPageText(String labelName) {
        switch (labelName) {
            case "AlphaOption":
                return AlphaOption.getText();
            case "BlinkOption":
                return BlinkOption.getText();
            case "BounceOption":
                return BounceOption.getText();
            case "CrossFadeOption":
                return CrossFadeOption.getText();
            case "FadeInOption":
                return FadeInOption.getText();
            default:
                return "";
        }

}
    public void ArcMenuClick() {
        waitCondition(ArcSideMenu, 60);
        ArcSideMenu.click();
    }

    public boolean isArcContentVisible() {
        return isElementPresent(ArcMenuOption);
    }

    public void ChartClick() {
        waitCondition(ChartSideMenu, 60);
        ChartSideMenu.click();
    }
}
