package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    // Use this method to submit login form with credentials
    public String submitLoginForm(String username, String password){
        UserName.sendKeys(username);
        Password.sendKeys(password);
        SubmitButton.click();
        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }


    // Clear all fields for next test round
    public void emptyAllFields(){
        UserName.clear();
        Password.clear();
    }
}
