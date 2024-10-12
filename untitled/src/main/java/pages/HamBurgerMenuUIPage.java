package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class HamBurgerMenuUIPage extends BasePage {
    public HamBurgerMenuUIPage(AppiumDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @AndroidFindBy(xpath = "//android.widget.ImageView[@resource-id='android:id/up']")
    private MobileElement hamburgerMenu;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Android UI Design']")
    private MobileElement homeSideMenuTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Animations']")
    private MobileElement animationsSideMenu;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Arc Menu']")
    private MobileElement ArcSideMenu;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Chart']")
    private MobileElement ChartSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Drag & Drop']")
    private MobileElement DragDropSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='ListView']")
    private MobileElement ListViewSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Magical TextView']")
    private MobileElement MagicalTextViewSideMenu;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Map']")
    private MobileElement MapSideMenu;



    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Paint']")
    private MobileElement PaintSideMenu;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Picker']")
    private MobileElement PickerSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PopUp']")
    private MobileElement PopUpSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Text 2 Speech']")
    private MobileElement Text2SpeechSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='ViewFlow']")
    private MobileElement ViewFlowSideMenu;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Wheel']")
    private MobileElement WheelSideMenu;



    public boolean isHamburgerMenuOnlandingPageVisible() {
        waitCondition(hamburgerMenu, 60);
        return hamburgerMenu.isDisplayed();
    }

    public void hamburgerMenuClick() {
        waitCondition(hamburgerMenu, 60);
        hamburgerMenu.click();
    }

    public String getHamburgerMenuText(String labelName) {
        switch (labelName) {
            case "HomeSideMenuTitle":
                return homeSideMenuTitle.getText();
            case "animationsSideMenu":
                return animationsSideMenu.getText();
            case "ArcSideMenu":
                return ArcSideMenu.getText();
            case "ChartSideMenu":
                return ChartSideMenu.getText();
            case "DragDropSideMenu":
                return DragDropSideMenu.getText();
            case "ListViewSideMenu":
                return ListViewSideMenu.getText();
            case "MagicalTextViewSideMenu":
                return MagicalTextViewSideMenu.getText();
            case "MapSideMenu":
                return MapSideMenu.getText();
            case "PaintSideMenu":
                return PaintSideMenu.getText();
            case "PickerSideMenu":
                return PickerSideMenu.getText();
            default:
                return "";
        }
    }
}
