package page_object_model.pages;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class SignUp extends BasePage{
    // Constructor invoking driver of BasePage
    public SignUp(WebDriver driver){
        super(driver);
    }

    // Testing whether password & re-enter password pair isn't validated correctly
    // Error guessing technique - assuming that functionality may not work, for testing it

    @FindBy(xpath = "//input[@id='uname']")
    public WebElement UserName;

    @FindBy(xpath = "//input[@id='pass']")
    public WebElement Password;

    @FindBy(xpath = "//input[@id='rpass']")
    public WebElement ReEnterPassword;

    @FindBy(xpath = "//input[@id='fname']")
    public WebElement Name;

    @FindBy(xpath = "//input[@id='email']")
    public WebElement Email;

    @FindBy(xpath = "//input[@id='phn']")
    public WebElement Phone;

    @FindBy(xpath = "//select[@id='city']")
    public WebElement City;

    @FindBy(xpath = "//input[@id='sub']")
    public WebElement SubmitButton;


    // Use this method to submit sign up form with parameterized data
    public String submitSignUpForm(String[] formData){
        emptyAllFields(); // Make sure that all fields are empty
        UserName.sendKeys(formData[0]); // Passed dynamically as parameters
        Password.sendKeys(formData[1]); // --
        ReEnterPassword.sendKeys(formData[2]); // --
        Name.sendKeys(formData[3]); // --
        Email.sendKeys(formData[4]); // --
        Phone.sendKeys(formData[5]); // --
        new Select(City).selectByVisibleText(formData[6]); // --
        SubmitButton.click();

        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        UserName.clear();
        Name.clear();
        Email.clear();
        Phone.clear();
        Password.clear();
        ReEnterPassword.clear();
        new Select(City).selectByVisibleText("Select City");
    }
}
