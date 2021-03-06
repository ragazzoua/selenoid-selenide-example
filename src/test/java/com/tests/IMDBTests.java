package com.tests;

import com.codeborne.selenide.WebDriverRunner;
import com.pages.HomePage;
import com.pages.MovieDetailPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.containsString;

public class IMDBTests {

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        final DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("enableVNC", true);

        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
        WebDriverRunner.setWebDriver(driver);
    }

    @After
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    public void searchMovieTest() {
        String searchText = "Rick and Morty";
        HomePage homePage = open("http://www.imdb.com/", HomePage.class);
        homePage.searchFor(searchText)
                .goToFirstSearchResult();
        MovieDetailPage movieDetailPage = new MovieDetailPage();
        Assert.assertThat(movieDetailPage.getMovieTitle(),containsString(searchText));
    }
}
