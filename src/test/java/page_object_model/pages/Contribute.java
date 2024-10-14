package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

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

    // Use this method to test incorrect phone number or incorrect email inputs
    public void submitContributeForm(String phoneNumber, String email){
        FirstName.sendKeys("Thejana-A"); // Hardcoded since this field is not focussed in this test
        Phone.sendKeys(phoneNumber);
        Email.sendKeys(email);
        new Select(Amount).selectByVisibleText("Rs. 500"); // This is hard coded since this is not focus of this test
        SubmitButton.click();

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("https://www.payhere.lk/pay/checkout")){
            Assert.assertEquals(currentUrl, "https://www.payhere.lk/pay/checkout", "Error in validation of email or phone ");
            System.out.println("Error in validation: Phone - " + phoneNumber + " email - " + email);
            System.out.println("Incorrect phone or email was allowed");
            captureScreenShot("contribute_form_error");
            driver.navigate().back();
        } else {
            System.out.println("Invalid values were denied successfully: Phone - " + phoneNumber + " email - " + email);
        }
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        FirstName.clear();
        Phone.clear();
        Email.clear();
        // No need to clear the select option, since it doesn't append anything to existing values in next test round
    }
}
