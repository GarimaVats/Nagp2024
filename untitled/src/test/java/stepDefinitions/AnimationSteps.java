package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mobile.AppiumHelper;
import org.junit.Assert;
import pages.AnimationPage;
import pages.HamBurgerMenuUIPage;

import java.util.Properties;

public class AnimationSteps extends AppiumHelper {
    Properties config;
    AnimationPage animationPage;

    public AnimationSteps() {
        config = Hooks.configuration;
        animationPage = new AnimationPage(driverInit());

    }
    @Given("user click on animation of Ui landing page")
    public void userClickOnAnimationOfUiLandingPage() {
        animationPage.AnimationMenuClick();

    }

    @When("verify content of animation page")
    public void verifyContentOfAnimationPage() {
        Assert.assertEquals("Alpha is not matching", "Alpha",
                animationPage.getAnimationPageText("AlphaOption"));
        Assert.assertEquals("Blink section is not matching",
                "Blink",
                animationPage.getAnimationPageText("BlinkOption"));
        Assert.assertEquals("BounceOption is not matching",
                "Bounce",
                animationPage.getAnimationPageText("BounceOption"));
        Assert.assertEquals("Cross Fade section is not matching",
                "Cross Fade",
                animationPage.getAnimationPageText("CrossFadeOption"));
        Assert.assertEquals("FadeInOption section is not matching",
                "Fade In",
                animationPage.getAnimationPageText("FadeInOption"));
}
    @Given("user click on Arc Menu")
    public void userClickOnArcMenu() {
        animationPage.ArcMenuClick();

    }

    @Then("verify content of Arc page")
    public void verifyContentOfArcPage() {
        Assert.assertTrue("All Content is not visible on landing page",
                animationPage.isArcContentVisible());

    }

    @Given("user click on Chart Section")
    public void userClickOnChartSectionu() {
        animationPage.ChartClick();

    }


}
