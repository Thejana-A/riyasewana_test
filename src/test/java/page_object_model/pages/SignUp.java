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

    @FindBy(xpath = "//div[@id='content']/div/div[2]/ul/li")
    public WebElement SystemResponse;

    // Use this method to test incorrect password, re-enter password pairs only, not for other fields
    // Dynamically provide password, re-enter password, and submit form
    public void submitSignUpForm(String password, String reEnterPassword){
        emptyAllFields(); // Make sure that all fields are empty
        UserName.sendKeys("Thejana-B"); // Hard coded these, as they aren't the focus of this test
        Name.sendKeys("Thejana"); // --
        Email.sendKeys("2020is001@stu.ucsc.cmb.ac.lk"); // --
        Phone.sendKeys("0777712345"); // --
        new Select(City).selectByVisibleText("Colombo"); // Hard coded these, as they aren't the focus of this test

        Password.sendKeys(password); // Passed dynamically as parameters
        ReEnterPassword.sendKeys(reEnterPassword);
        SubmitButton.click();

        System.out.println("System response: " + SystemResponse.getText());

        if((SystemResponse.getText()).isEmpty()){
            System.out.println("Error: Incorrect password pair was allowed ->  " + password + " - " + reEnterPassword + "\n");
            captureScreenShot("signup_error");
        } else {
            System.out.println("Incorrect password pair denied -> " + password + " - " + reEnterPassword + "\n");
        }
        driver.navigate().refresh(); // Refresh page to erase the system response
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        UserName.clear();
        Name.clear();
        Email.clear();
        Phone.clear();
        Password.clear();
        ReEnterPassword.clear();
    }
}
