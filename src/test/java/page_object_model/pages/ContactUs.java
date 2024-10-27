package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactUs extends BasePage{
    // Invoking WebDriver of BasePage class
    public ContactUs(WebDriver driver){
        super(driver);
    }

    // Testing whether Contact detail fields works correctly, in Contact Us form
    // Boundary value analysis - Considering that a contact number must be 10 digits in length (boundary lengths are 9 and 11)

    @FindBy(xpath = "//input[@id='fname']")
    public WebElement FirstName;

    @FindBy(xpath = "//input[@id='phone']")
    public WebElement Phone;

    @FindBy(xpath = "//input[@id='email']")
    public WebElement Email;

    @FindBy(xpath = "//textarea[@id='msg']")
    public WebElement Message;

    @FindBy(xpath = "//input[@id='sub']")
    public WebElement SubmitButton;

    @FindBy(xpath = "//div[@id='content']/div/div")
    public WebElement SystemResponse;

    // Use this method to submit contact details to the form
    public String submitContactUsForm(String[] contactData){
        FirstName.sendKeys(contactData[0]); // passed as parameters
        Phone.sendKeys(contactData[1]); // --
        Email.sendKeys(contactData[2]); // --
        Message.sendKeys(contactData[3]);  // --
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
        Email.clear();
        Message.clear();
    }
}
