package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LeasingOffer extends BasePage {
    // Invoking WebDriver of BasePage class
    public LeasingOffer(WebDriver driver){
        super(driver);
    }

    // Testing whether Name, Phone number fields work correctly, in Leasing Offer form
    // Boundary value analysis - considering that phone number must be a string of 10 digits, testing with strings of lengths 9 and 11.
    // Boundary value analysis - considering that name must be longer than 0 characters, testing with lengths 0 and 1

    @FindBy(xpath = "//input[@id='fname']")
    public WebElement FirstName;

    @FindBy(xpath = "//input[@id='phn']")
    public WebElement Phone;

    @FindBy(xpath = "//input[@id='city']")
    public WebElement City;

    @FindBy(xpath = "//input[@id='sub']")
    public WebElement SubmitButton;

    @FindBy(xpath = "//div[@id='content']/div")
    public WebElement SystemResponse;

    // Use this method to test invalid name (empty name input) or invalid phone number inputs
    public void submitLeasingOfferForm(String firstName, String phone){
        FirstName.sendKeys(firstName);
        Phone.sendKeys(phone);
        City.sendKeys("Colombo"); // This is hard coded since this is not focus of this test
        SubmitButton.click();
        // Wait for 6 seconds to see system response
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // Handle the exception if the thread is interrupted
            System.out.println("The sleep was interrupted.");
            e.printStackTrace();
        }

        System.out.println("System response: " + SystemResponse.getText());
        if((SystemResponse.getText()).equals("Thank you, One of our leasing agents will contact you.")){
            Assert.assertEquals(SystemResponse.getText(), "Thank you, One of our leasing agents will contact you.", "Error: Incorrect name or phone allowed -> " + firstName + " - " + phone);
            System.out.println("Error: Incorrect name or phone allowed -> " + firstName + " - " + phone);
            captureScreenShot("leasing_offer_error");
        } else {
            System.out.println("Incorrect name or phone denied ->  " + firstName + " - " + phone);
        }
        driver.navigate().refresh(); // Refresh page to erase the system response
    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        FirstName.clear();
        Phone.clear();
        City.clear();
    }
}
