package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import page_object_model.utilities.ScreenShotUtility;

public class Login extends BasePage {
    // Constructor invoking driver of BasePage
    public Login(WebDriver driver){
        super(driver);
    }

    // Testing login functionality
    // Case-wise testing - Testing different combinations for username + password pairs exhaustively

    @FindBy(xpath = "//input[@id='uname']")
    public WebElement UserName;

    @FindBy(xpath = "//input[@id='pass']")
    public WebElement Password;

    @FindBy(xpath = "//input[@id='sub']")
    public WebElement SubmitButton;

    // Use this method to test incorrect credentials only
    public void submitIncorrectCredentials(String username, String password){
        UserName.sendKeys(username);
        Password.sendKeys(password);
        SubmitButton.click();
        String currentUrl = driver.getCurrentUrl();
        if (!(currentUrl.equals("https://riyasewana.com/login.php"))){
            Assert.assertNotEquals(currentUrl, "https://riyasewana.com/login.php", "Error in credential validation: Incorrect credentials were allowed ");
            System.out.println("Error in credential validation: Incorrect credentials were allowed ");
            driver.navigate().back();
        } else {
            System.out.println("Incorrect credentials were denied successfully: " + username + " - " + password);
        }
    }

    // Use this method to test correct credentials, by passing them as parameters
    public void submitCorrectCredentials(String username, String password){
        UserName.sendKeys(username);
        Password.sendKeys(password);
        SubmitButton.click();
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("https://riyasewana.com/login.php")){
            Assert.assertEquals(currentUrl, "https://riyasewana.com/login.php", "Error in credential validation: Correct credentials were denied");
            System.out.println("Error in credential validation: Correct credentials were denied");
            captureScreenShot("login_error");
        } else {
            System.out.println("Correct credentials were allowed successfully");
            driver.navigate().back();
        }
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        UserName.clear();
        Password.clear();
    }
}
