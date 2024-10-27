package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserAccount extends BasePage{
    // Constructor invoking driver of BasePage
    public UserAccount(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//a[text()='Logout']")
    public WebElement LogOut;

    // Use this function to log out from user account, after logging in
    // Logging in can be explicit action performed by user, with credentials
    // Or in Riyasewana site, it can be an automatic implicit action too, following a successful user sign up.
    public void clickLogOut(){
        LogOut.click();
    }
}
