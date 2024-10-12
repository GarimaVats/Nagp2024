package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import mobile.AppiumHelper;
import org.junit.Assert;
import pages.HamBurgerMenuUIPage;

import java.util.Properties;

public class HamBurgerMenuSteps extends AppiumHelper {
    Properties config;
    HamBurgerMenuUIPage hamBurgerMenuPage;

    public HamBurgerMenuSteps() {
        config = Hooks.configuration;
        hamBurgerMenuPage = new HamBurgerMenuUIPage(driverInit());

    }
    @Given("verify HamBurger menu of landing page of application")
    public void verifyHamBurgerMenuOfLandingPageOfApplication() {
        Assert.assertTrue("HamBurger Menu is not successful",
                hamBurgerMenuPage.isHamburgerMenuOnlandingPageVisible());
    }

    @And("user click on HamBurger menu of Ui landing page")
    public void userClickOnHamBurgerMenuOfUiLandingPage() {
        hamBurgerMenuPage.hamburgerMenuClick();

    }


    @Then("verify menu content")
    public void verifyMenuContent() {
        Assert.assertEquals("Android UI Design is not matching", "Android UI Design",
                hamBurgerMenuPage.getHamburgerMenuText("HomeSideMenuTitle"));
        Assert.assertEquals("Animations section is not matching",
                "Animations",
                hamBurgerMenuPage.getHamburgerMenuText("animationsSideMenu"));
        Assert.assertEquals("ArcSideMenu is not matching",
               "Arc Menu",
                hamBurgerMenuPage.getHamburgerMenuText("ArcSideMenu"));
        Assert.assertEquals("Chart section is not matching",
                "Chart",
                hamBurgerMenuPage.getHamburgerMenuText("ChartSideMenu"));
        Assert.assertEquals("Drag & Drop section is not matching",
                "Drag & Drop",
                hamBurgerMenuPage.getHamburgerMenuText("DragDropSideMenu"));
        Assert.assertEquals("ListView section is not matching",
                "ListView",
                hamBurgerMenuPage.getHamburgerMenuText("ListViewSideMenu"));
        Assert.assertEquals("Magical TextView section is not matching",
                "Magical TextView",
                hamBurgerMenuPage.getHamburgerMenuText("MagicalTextViewSideMenu"));
        Assert.assertEquals("Map section is not matching",
                "Map",
                hamBurgerMenuPage.getHamburgerMenuText("MapSideMenu"));
        Assert.assertEquals("Paint section is not matching",
                "Paint",
                hamBurgerMenuPage.getHamburgerMenuText("PaintSideMenu"));
        Assert.assertEquals("Picker section is not matching",
                "Picker",
                hamBurgerMenuPage.getHamburgerMenuText("PickerSideMenu"));
    }
}
