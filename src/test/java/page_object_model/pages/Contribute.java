package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Contribute extends BasePage {
    // Invoking WebDriver of BasePage class
    public Contribute(WebDriver driver){
        super(driver);
    }

    // Testing whether Contact Number, Email fields work correctly, in Leasing Offer form
    // Boundary value analysis - considering that phone number must be a string of 10 digits, testing with strings of lengths 9, 10, and 11.
    // Data pattern analysis - considering that email must contain an @ sign in it

    @FindBy(xpath = "//input[@id='first_name']")
    public WebElement FirstName;

    @FindBy(xpath = "//input[@id='phone']")
    public WebElement Phone;

    @FindBy(xpath = "//input[@id='email']")
    public WebElement Email;

    @FindBy(xpath = "//select[@id='amount']")
    public WebElement Amount;

    @FindBy(xpath = "//input[@id='sub']")
    public WebElement SubmitButton;

    // Use this method to submit data to contribute form
    public String submitContributeForm(String[] contributeData){
        FirstName.sendKeys(contributeData[0]); // Dynamically updated by test
        Phone.sendKeys(contributeData[1]);
        Email.sendKeys(contributeData[2]);
        new Select(Amount).selectByVisibleText("Rs. 500"); // This is hard coded since this is always selected by default
        SubmitButton.click();

        String currentUrl = driver.getCurrentUrl();
        return currentUrl;
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        FirstName.clear();
        Phone.clear();
        Email.clear();
        // No need to clear the select option, since it doesn't append anything to existing values in next test round
    }
}
