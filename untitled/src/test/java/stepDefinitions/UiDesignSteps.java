package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import mobile.AppiumHelper;
import org.junit.Assert;
import pages.UiDesignUIPage;

import java.util.Properties;



public class UiDesignSteps extends AppiumHelper {
    Properties config;
    UiDesignUIPage uiDesignPage;

    public UiDesignSteps() {
        config = Hooks.configuration;
        uiDesignPage = new UiDesignUIPage(driverInit());

    }
    @Given("user is on Ui landing page")
    public void userIsOnUiLandingPage() {
       Assert.assertTrue("Welcome!",
                uiDesignPage.isWelcomeScreenTitleDisplayed());
    }

    @And("verify user is on landing page and welcome guideline message is visible")
    public void verifyUserIsOnLandingPageAndWelcomeGuidelineMessageIsVisible() {
        Assert.assertTrue("Welcome guideline message is not visible on landing page",
                uiDesignPage.isAttentionMessageVisible());
    }

    @Then("verify content of landing page")
    public void verifyContentOfLandingPage() {
        Assert.assertTrue("All Content is not visible on landing page",
                uiDesignPage.isContentVisible());
    }

}
