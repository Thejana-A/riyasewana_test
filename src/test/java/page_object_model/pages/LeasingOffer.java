package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LeasingOffer extends BasePage {
    // Invoking WebDriver of BasePage class
    public LeasingOffer(WebDriver driver){
        super(driver);
    }

    // Testing whether Name, Phone number, city fields work correctly, in Leasing Offer form
    // Equivalence partitioning - considering that phone number must be a string of 10 digits, testing with strings of lengths < 10 and 10 <.
    // Pattern matching - considering that name should contain only letters

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

    // Use this method to submit data to Leasing Offer form
    public String submitLeasingOfferForm(String[] leasingOfferData){
        FirstName.sendKeys(leasingOfferData[0]);
        Phone.sendKeys(leasingOfferData[1]);
        City.sendKeys(leasingOfferData[2]); // This is hard coded since this is not focus of this test
        SubmitButton.click();
        // Wait for 6 seconds to see system response
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // Handle the exception if the thread is interrupted
            System.out.println("The sleep was interrupted.");
            e.printStackTrace();
        }
        String systemResponse = SystemResponse.getText();
        return systemResponse;

    }

    // Clear all fields for next test round
    public void emptyAllFields(){
        FirstName.clear();
        Phone.clear();
        City.clear();
    }
}
