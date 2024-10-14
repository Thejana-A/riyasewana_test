package page_object_model.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class ContactUs extends BasePage{
    // Invoking WebDriver of BasePage class
    public ContactUs(WebDriver driver){
        super(driver);
    }

    // Testing whether Contact Number field works correctly, in Contact Us form
    // Equivalence partitioning - Consider that a contact number must be 10 digits in length
    // Data partitions as length of Contact Number are 0-9 (invalid), 10 (valid), and above 10 (invalid)

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

    // Use this method to test invalid phone numbers inputs
    public void submitContactUsForm(String phone){
        FirstName.sendKeys("Amal"); // Hardcoded as this field isn't a focus in this test
        Phone.sendKeys(phone);
        Email.sendKeys("abc@gmail.com");
        Message.sendKeys("This is test message"); // This is hard coded since this is not focus of this test
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
        if((SystemResponse.getText()).equals(" Mail Sent")){
            Assert.assertEquals(SystemResponse.getText(), " Mail Sent", "Error: Incorrect phone was allowed - " + phone);
            System.out.println("Error: Incorrect phone was allowed - " + phone);
            captureScreenShot("contact_number_error");
        } else {
            System.out.println("Incorrect was was denied - " + phone);
        }
        driver.navigate().refresh(); // Refresh page to erase the system response
    }



    // Clear all fields for next test round
    public void emptyAllFields(){
        FirstName.clear();
        Phone.clear();
        Email.clear();
        Message.clear();
    }
}
