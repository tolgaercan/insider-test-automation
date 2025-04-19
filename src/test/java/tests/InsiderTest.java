package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BasePage;

import java.time.Duration;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsiderTest {
    WebDriver driver;
    BasePage basePage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://useinsider.com/");
        basePage = new BasePage(driver);
    }

    @Test
    public void testHomePageIsDisplayed() {
        assertTrue(basePage.isHomePageDisplayed(), "Home page goruntulenemedi.");

    }

    @Test
    public void testCareersPageNavigation() throws InterruptedException {
        basePage.acceptCookiesIfPresent();
        basePage.navigateToCareersPage();
        assertTrue(basePage.verifyCareerPageSections(), "Career sekmesi gorunur degil.");
    }

    @Test
    public void testJobFiltering() throws InterruptedException {
        basePage.acceptCookiesIfPresent();
        basePage.navigateToCareersPage();
        basePage.findYourDreamJobButton();
        basePage.filterJobs("Istanbul, Turkiye", "Quality Assurance");
        assertTrue(basePage.verifyJobListings(), "Job listesinde uygun olmayan ilanlar var");
    }

    @Test
    public void testViewRoleNavigation() throws InterruptedException {
        basePage.acceptCookiesIfPresent();
        basePage.navigateToCareersPage();
        basePage.findYourDreamJobButton();
        basePage.filterJobs("Istanbul, Turkiye", "Quality Assurance");
        basePage.firstJobClick();
        driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
        assertTrue(basePage.applyForThisJobButton(), "Tiklama sonucunda dogrulama yapildi.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

