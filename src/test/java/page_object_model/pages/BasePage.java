package page_object_model.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import page_object_model.utilities.ScreenShotUtility;

//Utilities class define functions related to tests while BasePage class defined functions related to pages
public class BasePage {
    protected WebDriver driver = null;

    // Pass the webdriver variable, by creating the constructor of class
    public BasePage(WebDriver driver){
        this.driver = driver;
    }
    public HomePage load_url(String url){
        driver.get(url);
        return PageFactory.initElements(driver, HomePage.class);
    }

    // This scrollpage function is common to any browser
    public void scrollPage(int x, int y){
        new Actions(driver).scrollByAmount(x, y).perform();
    }

    public void captureScreenShot(String fileName){
        ScreenShotUtility screenShotUtility = new ScreenShotUtility();
        screenShotUtility.CaptureScreenshot(driver, fileName);
    }
}

